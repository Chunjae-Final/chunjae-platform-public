package kr.co.chunjae.test;

import kr.co.chunjae.paper.service.PageService;
import kr.co.chunjae.paper_result.service.PaperResultService;
import kr.co.chunjae.question.dto.QuestionDto;
import kr.co.chunjae.question.service.QuestionService;
import kr.co.chunjae.question_result.service.QuestionResultService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Slf4j
@Controller
@RequiredArgsConstructor
public class PaperReportAnswerController {

    private final PageService pageService;
    private final PaperResultService paperResultService;
    private final QuestionService questionService;
    private final QuestionResultService questionResultService;

    @GetMapping(value = "/paper/report/student-choice")
    public String showStudentChoiceAnswerPage(@RequestParam(name = "paper_result_id", required = false) Integer paperResultId,
                                              @RequestParam(name = "question_id", required = false) Integer questionId,
                                              Model model) {

        // 문항 정보 갖고오기
        QuestionDto question = questionService.getSpecificQuestionByQuestionId(questionId);

        // 사용자가 선택한 정답 갖고오기
        String studentChoice = questionResultService.getStudentChoiceByPaperResultIdAndQuestionId(paperResultId,
                questionId);

        model.addAttribute("question", question);
        model.addAttribute("studentChoice", studentChoice);
        return "test/studentChoice";
    }

    @GetMapping(value = "/paper/report/real-answer")
    public String showRealAnswerPage(@RequestParam(name = "question_id", required = false) Integer questionId,
                                     Model model) {

        // 문항 정보 갖고오기
        QuestionDto question = questionService.getSpecificQuestionByQuestionId(questionId);
        log.info("question.answer = {}", question.getAnswer());

        model.addAttribute("question", question);
        return "test/realAnswer";
    }
}
