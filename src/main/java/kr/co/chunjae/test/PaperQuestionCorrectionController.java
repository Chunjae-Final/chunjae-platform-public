package kr.co.chunjae.test;

import kr.co.chunjae.correct_rate.service.CorrectRateService;
import kr.co.chunjae.paper.dto.PaperDto;
import kr.co.chunjae.paper.service.PageService;
import kr.co.chunjae.paper_result.service.PaperResultService;
import kr.co.chunjae.question.dto.QuestionDto;
import kr.co.chunjae.question.service.QuestionService;
import kr.co.chunjae.question_result.service.QuestionResultService;
import kr.co.chunjae.user.dto.UserDTO;
import kr.co.chunjae.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Controller
@RequiredArgsConstructor
public class PaperQuestionCorrectionController {

    private final PaperResultService paperResultService;
    private final PageService pageService;
    private final QuestionService questionService;
    private final QuestionResultService questionResultService;
    private final CorrectRateService correctRateService;
    private final UserService userService;

    @GetMapping("/test/paper/siljeon1")
    public String showSiljeon1PaperPage(@RequestParam(name = "paper_id") Integer paperId, Model model, Authentication authentication) {
        Map<String, Object> paperWithQuestionList = pageService.getPaperWithQuestionListByPaperId(paperId);
        PaperDto paper = (PaperDto) paperWithQuestionList.get("paper");
        List<QuestionDto> questionList = (List<QuestionDto>) paperWithQuestionList.get("questionList");

//        String studentPhone = "010-5804-0095";
        String studentPhone = "";
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.getPrincipal() instanceof String) {
            studentPhone = (String) auth.getPrincipal();
        }
        log.info("studentPhone :" + studentPhone);

            model.addAttribute("paper", paper);
            model.addAttribute("questionList", questionList);
            model.addAttribute("studentPhone", studentPhone);


        return "test/siljeon1/siljeon1";
    }

    @PostMapping(value = "/test/paper/generate-new-paper-result-id")
    @ResponseBody
    public ResponseEntity<?> generateNewPaperResultId(@RequestBody GenerateNewPaperResultIdDto requestDto) {
        log.info("GenerateNewPaperResultIdDto = {}", requestDto);
        paperResultService.generateNewPaperResultId(requestDto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(requestDto.getGeneratedResultId());
    }

    @PostMapping(value = "/test/paper/question/send-and-check-student-answer")
    @ResponseBody
    public ResponseEntity<?> checkStudentAnswer(@RequestBody CheckStudentAnswerDto requestDto) {
        log.info("CheckStudentAnswerDto = {}", requestDto);

        // 채점결과: 학생이 푼 문제가 정답이 맞는지 아닌지 여부 (Y or N)
        String yesOrNo = questionService.compareStudentChoiceAnswerWithCorrectAnswer(requestDto);

        // 먼저 paper_result_id와 question_id를 사용하여 tb_question_result 에 저장되어 있는 사용자가 이전에 선택한 답을 삭제
        questionResultService.deletePreviousStudentChoiceAnswer(requestDto);

        // 그 다음, 채점결과를 새롭게 tb_question_result에 저장
        questionResultService.insertResultOfCheckStudentAnswer(yesOrNo, requestDto);

        // 그 다음, 현재 시간이랑, tb_paper_result 에 저장되어 있는 last_access_timestamp와 현재 시간이랑 비교한다
        LastAccessTimestampAndSpendTimeDto lastAccessTimestampAndSpendTime =
                paperResultService.getLastAccessTimestampAndSpendTime(requestDto.getPaperResultId());
        LocalDateTime currentTimestamp = LocalDateTime.now(ZoneId.of("Asia/Seoul"));

        Duration duration = Duration.between(lastAccessTimestampAndSpendTime.getLastAccessTimestamp(),
                currentTimestamp);

//        log.info("소비 시간: " + duration.getSeconds());

        int updatedSpendTime = lastAccessTimestampAndSpendTime.getSpendTime() + (int) duration.getSeconds();

        // tb_paper_result 의 spend_time을 업데이트 시켜준다
        paperResultService.updateLastAccessTimestampAndSpendTime(requestDto.getPaperResultId(), updatedSpendTime);

        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("status", "success");
        responseMap.put("paperResultId", requestDto.getPaperResultId());
        responseMap.put("questionId", requestDto.getQuestionId());
        responseMap.put("studentChoice", requestDto.getStudentChoice());
        responseMap.put("spendTime", updatedSpendTime);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(responseMap);
    }

    @PostMapping(value = "/test/paper/check-student-finished-paper")
    @ResponseBody
    public ResponseEntity<?> checkStudentFinishedPaper(@RequestBody CheckStudentFinishedPaperDto requestDto) {
        log.info("CheckStudentFinishedPaperDto = {}", requestDto);
        Map<String, Object> resultMap = paperResultService.checkStudentFinishedPaper(requestDto);

        if (resultMap.get("result").equals("EXISTS")) {
            /*
             * 만약 해당 시험지를 푼 기록이 있다면 (진행 중이라면), 시험지를 맨 처음 응시했던 그 시점 (start_timestamp)과
             * 현재 시간을 비교하여 1시간 (3600초)가 넘었다면, 그냥 시험 종료시킴
             * 즉, 시험지를 풀다가 중간에 꺼버렸어도 무조건 시간은 흐름
             * */
            Integer paperResultId = (Integer) resultMap.get("paperResultId");
            LocalDateTime startTimestamp =
                    paperResultService.getStartTimestampByPaperResultId(paperResultId);
            LocalDateTime currentTimestamp = LocalDateTime.now(ZoneId.of("Asia/Seoul"));
            long elapsedTime = Duration.between(startTimestamp, currentTimestamp).getSeconds();
            if (elapsedTime > 3_600) {
                // 시험을 최초로 시작한지 한 시간이 지나버렸다면, 강제로 시험 종료
                resultMap.put("finished", "true");
            } else {
                // 그게 아니라면, last_access_time을 지금 시간으로 변경시켜주고, spend_time을 elapsedTime으로 update 시켜줌
                paperResultService.updateLastAccessTimestampAndSpendTime(paperResultId, (int) elapsedTime);
                resultMap.put("finished", "false");
                resultMap.put("spendTime", elapsedTime);
            }
        }

        return ResponseEntity
                .ok(resultMap);
    }

    @PostMapping(value = "/test/paper/student-click-final-submit-button")
    @ResponseBody
    public ResponseEntity<?> paperFinalSubmit(@RequestBody PaperFinalSubmitDto requestDto) {
        /*
         * 순서:
         * 1. 학생이 푼 특정 시험지 중, tb_paper_result 에 해당 paper_id 가 있다면, 이전 기록을 삭제해야 하기 때문에
         *    지금 전송받은 paper_result_id 와 match 되지 않는 paper_result_id를 가져오는데 해당 paper_result_id는 지금 제출한
         *    paper_id와 연관되어 있는 것들...
         * */
        List<Integer> paperResultIdListForDelete = paperResultService.getPaperResultIdListForDelete(requestDto);

        // 받아온 paperResultIdListForDelete를 활용하여, tb_question_result 테이블에서 paperResultIdListForDelete 에 담겨있는
        // paper_result_id 에 연관된 행들을 삭제
        if (paperResultIdListForDelete.size() > 0) {
            questionResultService.deleteQuestionResultByPaperResultId(paperResultIdListForDelete);

            // 그리고, tb_paper_result 에서 paperResultIdListForDelete에 담겨있는 id 들을 삭제
            paperResultService.deletePaperResultByIdList(paperResultIdListForDelete);
        }

        // 이제, 프론트에서 받아온 paper_result_id 를 활용하여 tb_paper_result 테이블에 end_timestamp 찍어주고
        // finished를 Y 로 변경해준다
        paperResultService.writeEndTimestampAndChangeFinishedToY(requestDto.getPaperResultId());

        // 학생이 문제를 아예 풀지도 않고 최종제출버튼을 클릭했으면, tb_question_result 에 저장되지도 않으니, 아예 틀렸다고 DB에 저장해줘야함
        questionResultService.fillUpNotSolvedQuestions(requestDto.getStudentPhone(), requestDto.getPaperId(),
                requestDto.getPaperResultId());

        // 마지막으로, 문항별 정답률 테이블의 문 푼 학생수 +1 해주고, 만약 특정 문항을 맞았다면 +1
        correctRateService.updateStudentCountAndCorrectCount(requestDto.getPaperResultId());

        return ResponseEntity.ok("Success");
    }
}
