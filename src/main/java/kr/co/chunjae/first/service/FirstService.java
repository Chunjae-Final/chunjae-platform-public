package kr.co.chunjae.first.service;

import kr.co.chunjae.first.dto.*;
import kr.co.chunjae.paper.dto.PaperDto;
import kr.co.chunjae.paper.mapper.PaperMapper;
import kr.co.chunjae.paper_result.mapper.PaperResultMapper;
import kr.co.chunjae.question.dto.QuestionDto;
import kr.co.chunjae.question.mapper.QuestionMapper;
import kr.co.chunjae.question.service.QuestionService;
import kr.co.chunjae.question_result.mapper.QuestionResultMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.LocalDateTime;
import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class FirstService {
    private final QuestionResultMapper questionResultMapper;
    private final PaperMapper paperMapper;
    private final QuestionMapper questionMapper;
    private final QuestionService questionService;
    private final PaperResultMapper paperResultMapper;

    public Map<String, Object> getPaperTypeByPaperId(Integer paperId) {
        return paperMapper.getPaperTypeByPaperId(paperId);
    }

    public Map<String, Object> getPaperWithQuestionListByPaperId(Integer paperId) {
        PaperDto paper = paperMapper.getPaperByPaperId(paperId);
        List<QuestionDto> questionList = questionService.getQuestionListByPaperId(paperId);

        Map<String, Object> result = new HashMap<>();
        result.put("paper", paper);
        result.put("questionList", questionList);
        return result;
    }


    // 응시한 시험지에 대한 (결과) 데이터를 생성한다.
    // 이때, 이미 응시한 시험지에 대해서는 재응시를 진행하도록 처리한다.
    public int newPaperResult(newPaperResultDTO newPaperResultDTO) {
        // 응시한 시험지 결과 ID를 반환해 저장한다.
        // newPaperResultDTO의 newPaperResultId를 이용하여 값을 가져올 수 있다.
        paperResultMapper.newPaperResult(newPaperResultDTO);

        // 응시한 시험지 결과 ID를 반환하여 문항별 결과 데이터 생성에 이용한다.
        return newPaperResultDTO.getNewPaperResultId();
    }


    // 사용자가 선택한 답(문항별)을 DB에 저장한다.
    // 선택한 답(문항별)에 대한 채점 결과를 DB에 저장한다.
    public void saveStudentAnswer(saveStudentAnswerDTO saveStudentAnswerDTO) {
        // 문제를 푼 이력이 있는 경우
        if(existStudentAnswer(saveStudentAnswerDTO) >= 1) {
            // 기존 이력을 삭제한다.
            deleteStudentAnswer(saveStudentAnswerDTO);

            // 사용자가 선택한 답(문항별)을 저장한다.
            questionResultMapper.saveStudentAnswer(saveStudentAnswerDTO);

            // 해당 문항을 채점하고 결과를 저장한다.
            correctStudentAnswer(saveStudentAnswerDTO);

            // 시험지 최종 액세스 시간을 업데이트한다.
            paperResultMapper.updateLastAccessTime(saveStudentAnswerDTO);
        } else { // 문제를 푼 이력이 없는 경우
            // 사용자가 선택한 답(문항별)을 저장한다.
            questionResultMapper.saveStudentAnswer(saveStudentAnswerDTO);

            // 해당 문항을 채점하고 결과를 저장한다.
            correctStudentAnswer(saveStudentAnswerDTO);

            // 시험지 최종 액세스 시간을 업데이트한다.
            paperResultMapper.updateLastAccessTime(saveStudentAnswerDTO);
        }
    }


    // 각 문항을 채점하고 결과를 저장한다.
    public void correctStudentAnswer(saveStudentAnswerDTO saveStudentAnswerDTO) {
        // 각 문항별 채점하고 결과를 저장한다.
        // 문항별 채점 결과 : Y or N

        // 1. 답변 선택이 완료된 문항의 정답을 가져온다.
        String questionAnswer = questionResultMapper.questionAnswer(
                saveStudentAnswerDTO.getQuestionId());

        Map<String, Object> correctMap = new HashMap<String, Object>();

        correctMap.put("answer", questionAnswer);
        correctMap.put("saveStudentAnswerDTO", saveStudentAnswerDTO);
        correctMap.put("studentChoice", saveStudentAnswerDTO.getStudentChoice());

        // 2. 각 문항을 채점하고 결과를 저장한다.
        // 가져온 정답과 사용자의 정답을 비교하여 'Y' or 'N'을 저장한다.
        questionResultMapper.studentAnswerCorrect(correctMap);
    }


    // 문항을 푼 이력이 있는지 확인한다.
    public int existStudentAnswer(saveStudentAnswerDTO saveStudentAnswerDTO) {
        return questionResultMapper.existStudentAnswer(saveStudentAnswerDTO);
    }


    // 기존에 저장된 이력을 삭제한다.
    public void deleteStudentAnswer(saveStudentAnswerDTO saveStudentAnswerDTO) {
        questionResultMapper.deleteStudentAnswer(saveStudentAnswerDTO);
    }


    // 응시할 시험지에 대한 데이터 유무를 판단한다.
    public Map<String, Object> existPaperResult(paperResultDTO paperResultDTO) {
        // 응시할 시험지 ID, 사용자 휴대폰 번호, 최종 제출 유무를 기준으로 판단한다.
        // 데이터가 존재한다면 '시험지 결과' 테이블에서 가장 최근에 응시한 id 값을 저장한다.
        // 데이터가 존재하지 않는다면 -1을 저장한다.
        int existResult = paperResultMapper.existPaperResult(paperResultDTO);

        Map<String, Object> existPaperResultMap = new HashMap<String, Object>();

        if (existResult != -1) {   // 응시할 시험지에 대한 데이터가 존재하는 경우
            existPaperResultMap.put("existResult", "exist");
        } else {                  // 응시할 시험지에 대한 데이터가 존재하지 않는 경우
            existPaperResultMap.put("existResult", "not exist");
            return existPaperResultMap;
        }

        // 응시할 시험지에 대한 데이터가 존재하는 경우, 문항별 결과 테이블에서
        // 기존에 사용자가 선택한 답을 모두 불러온다.
        Map<String, Object> studentChoiceMap = new HashMap<String, Object>();

        studentChoiceMap.put("paperResultId", existResult);
        studentChoiceMap.put("studentPhone", paperResultDTO.getStudentPhone());

        List<studentChoiceAnswerDTO> studentChoiceAnswerList =
                questionResultMapper.studentChoiceAnswerList(studentChoiceMap);

        existPaperResultMap.put("paperResultId", existResult);
        existPaperResultMap.put(
                "studentChoiceAnswerList", studentChoiceAnswerList);

        return existPaperResultMap;
    }


    // 시험지를 최종 제출 처리한다.
    public void paperFinished(paperFinishedDTO paperFinishedDTO) {
        // 응시 종료 시간, 최종 제출 여부를 반영한다.
        // 최초 최종 제출 혹은 재응시에 따른 분기 처리를 진행한다.

        Integer finishedPaperCount = paperResultMapper.finishedPaperCount(
                paperFinishedDTO);

        // 해당 시험지가 이미 최종 제출된 상태라면(재응시의 경우),
        // 기존 기록을 삭제한 후 재저장한다.
        if (finishedPaperCount >= 1) {
            paperResultMapper.deleteFinishedPaper(paperFinishedDTO);
            paperResultMapper.paperFinished(paperFinishedDTO);
        } else {
            // 초기 최종 제출 상태일 시, 관련 데이터를 곧바로 저장한다.
            paperResultMapper.paperFinished(paperFinishedDTO);
        }

        // 사용자가 확인하지 않은 문제의 경우, 답으로 빈 값을 채워준다.
        // 1) 응시 중인 시험지 ID에 해당하는 문항 ID를 뽑는다.
        List<Integer> questionList = questionMapper.findPaperQuestion(
                paperFinishedDTO.getPaperId());

        // 2) 응시 중인 시험지 결과 중 사용자가 답한 문항 ID를 뽑는다.
        List<Integer> questionResultList =
                questionResultMapper.findQuestionResult(
                        paperFinishedDTO.getPaperResultId());

        List<Integer> addStudentAnswerList = new ArrayList<>();

        // 3) 두 리스트를 비교하며 사용자가 답하지 않은 문항 ID를 찾는다.
        for (Integer questionId : questionList) {
            if (!questionResultList.contains(questionId)) {
                addStudentAnswerList.add(questionId);
            }
        }

        // 답이 공백인 문제가 없다면
        if (addStudentAnswerList.isEmpty()) {
            log.info("모든 문항을 해결하였습니다. 최종 제출 처리가 완료됩니다.");
            // 통계를 위해 문항에 대한 데이터를 저장한다.
            saveCorrectRate(paperFinishedDTO);

        } else { // 답이 공백인 문제가 있다면
            Map<String, Object> unansweredMap = new HashMap<String, Object>();

            unansweredMap.put("unansweredList", addStudentAnswerList);
            unansweredMap.put("paperId", paperFinishedDTO.getPaperId());
            unansweredMap.put("paperResultId", paperFinishedDTO.getPaperResultId());
            unansweredMap.put("studentPhone", paperFinishedDTO.getStudentPhone());


            // 4) 사용자가 답하지 않은 문항에 대하여 빈 값을 채워주고, 'N'으로 채점한다.
            questionResultMapper.insertUnansweredQuestion(unansweredMap);

            // 통계를 위해 문항에 대한 데이터를 저장한다.
            saveCorrectRate(paperFinishedDTO);
        }
    }


    // 통계를 위해 각 문항에 대한 데이터를 분기 처리하여 카운트한다.
    public void saveCorrectRate(paperFinishedDTO paperFinishedDTO) {
        // 사용자가 해결한 문제 데이터를 불러온다.
        List<saveCorrectRateDTO> findSolvedQuestion =
                questionResultMapper.findSolvedQuestion(paperFinishedDTO);

        // 통계 테이블에 존재하는 문항 리스트를 불러온다.
        List<Integer> correctRateQuestion =
                questionResultMapper.correctRateQuestion();

        // 문항 번호가 등록되지 않은 경우 통계 테이블에 등록한다.
        for (saveCorrectRateDTO dto : findSolvedQuestion) {
            // 사용자가 해결한 문항
            Integer questionId = dto.getQuestionId();

            // 사용자가 해결한 문항이 통계 테이블에 존재하지 않는다면
            if (!correctRateQuestion.contains(questionId)) {
                // 통계 테이블에 해당 문항 ID를 추가한다.
                questionResultMapper.addQuestionId(questionId);
            }
        }

        List<Integer> correctQuestion = new ArrayList<>();
        List<Integer> incorrectQuestion = new ArrayList<>();

        // 채점 결과에 따라 리스트에 문항 번호를 넣는다.
        for (int i = 0; i < findSolvedQuestion.size(); i++) {
            // 맞은 문제일 경우
            if (findSolvedQuestion.get(i).getCorrect().equals("Y")) {
                correctQuestion.add(findSolvedQuestion.get(i).getQuestionId());
            } else {
                // 틀린 문제일 경우
                incorrectQuestion.add(findSolvedQuestion.get(i).getQuestionId());
            }
        }


        if (!correctQuestion.isEmpty()) {
            // 맞은 문제에 대한 통계 데이터를 축적한다.
            questionResultMapper.saveCorrectRateByCorrectQuestion(
                    correctQuestion);
        }

        if (!incorrectQuestion.isEmpty()) {
            // 틀린 문제에 대한 통계 데이터를 축적한다.
            questionResultMapper.saveCorrectRateByIncorrectQuestion(
                    incorrectQuestion);
        }
    }
}
