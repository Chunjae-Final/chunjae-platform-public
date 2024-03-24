package kr.co.chunjae.first.controller;

import kr.co.chunjae.first.dto.newPaperResultDTO;
import kr.co.chunjae.first.dto.paperFinishedDTO;
import kr.co.chunjae.first.dto.paperResultDTO;
import kr.co.chunjae.first.dto.saveStudentAnswerDTO;
import kr.co.chunjae.first.service.FirstService;
import kr.co.chunjae.paper.dto.PaperDto;
import kr.co.chunjae.paper.service.PageService;
import kr.co.chunjae.question.dto.QuestionDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("first")
public class FirstController {
    private final PageService pageService;
    private final FirstService firstService;

    @GetMapping(value = "/paper-list")
    public String showPaperListPage(@RequestParam("paper_type") Integer paperType, Model model) {
        // paperType의 값은 CBT일 경우 1 or 실전시험 I은 2 or 실전시험 II은 3 or Report는 4
        // JSP 에서 탭 메뉴의 색상을 파란색으로 칠해주기 위해 paperType 값이 필요
        model.addAttribute("paperType", paperType);
        return "first/paperTabMenuList";
    }

    @GetMapping(value = "/sound-check")
    public String showSoundCheckPage(@RequestParam("paper_id") Integer paperId, Model model) {
        // 시험지의 종류가 CBT, 실전시험 I, 실전시험 II인지 확인
        Map<String, Object> resultMap = pageService.getPaperTypeByPaperId(paperId);
        // id 값은 CBT일 경우 1, 실전시험 I는 2, 실전시험 II는 3
        model.addAttribute("paperTypeId", (Integer) resultMap.get("id"));
        // name은 CBT, 실전시험 I, 실전시험 II 문자열 그대로
        model.addAttribute("paperTypeName", (String) resultMap.get("name"));
        return "first/soundCheck";
    }

    @GetMapping(value = "/announcement")
    public String showAnnouncementPage(@RequestParam("paper_id") Integer paperId, Model model) {
        // 시험지의 종류가 CBT, 실전시험 I, 실전시험 II인지 확인
        Map<String, Object> resultMap = pageService.getPaperTypeByPaperId(paperId);
        // id 값은 CBT일 경우 1, 실전시험 I는 2, 실전시험 II는 3
        model.addAttribute("paperTypeId", (Integer) resultMap.get("id"));
        // name은 CBT, 실전시험 I, 실전시험 II 문자열 그대로
        model.addAttribute("paperTypeName", (String) resultMap.get("name"));
        return "first/announcement";
    }

    @GetMapping(value = "/paper-view")
    public String showPaperViewPage(@RequestParam("paper_id") Integer paperId, Model model) {
        Map<String, Object> paperWithQuestionList = pageService.getPaperWithQuestionListByPaperId(paperId);
        PaperDto paper = (PaperDto) paperWithQuestionList.get("paper");
        List<QuestionDto> questionList = (List<QuestionDto>) paperWithQuestionList.get("questionList");

        String studentPhone = "010-9294-4623";

        model.addAttribute("paper", paper);
        model.addAttribute("questionList", questionList);
        model.addAttribute("studentPhone", studentPhone);
        model.addAttribute("paperId", paperId);

        return "first/siljeon1";
    }

    // 응시할 시험지에 대한 데이터가 존재하는지 판단한다.
    @PostMapping("/existPaperResult")
    @ResponseBody
    public Map<String, Object> existPaperResult(
            @RequestBody paperResultDTO paperResultDTO) {
        Map<String, Object> resultMap = firstService.existPaperResult(paperResultDTO);

        return resultMap;
    }

    // 응시한 시험지에 대한 (결과) 데이터를 생성한다.
    @PostMapping("/newPaperResult")
    @ResponseBody
    public Map<String, Integer> newPaperResult(
            @RequestBody newPaperResultDTO newPaperResultDTO) {
        int id = firstService.newPaperResult(newPaperResultDTO);

        Map<String, Integer> paperResultId = new HashMap<String, Integer>();

        paperResultId.put("paperResultId", id);

        return paperResultId;
    }


    // 사용자가 선택한 답을 저장 및 채점한다.
    @PostMapping("/saveStudentAnswer")
    @ResponseBody
    public void saveStudentAnswer(
            @RequestBody saveStudentAnswerDTO saveStudentAnswerDTO) {
        firstService.saveStudentAnswer(saveStudentAnswerDTO);
    }


    // 시험지를 최종 제출 처리한다.
    @PostMapping("/paperFinished")
    @ResponseBody
    public void paperFinished(
            @RequestBody paperFinishedDTO paperFinishedDTO) {
        firstService.paperFinished(paperFinishedDTO);
    }
}
