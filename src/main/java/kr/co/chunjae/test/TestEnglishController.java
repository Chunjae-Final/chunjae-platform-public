package kr.co.chunjae.test;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
public class TestEnglishController {

    private final QuestionMapperTest questionMapperTest;

    @GetMapping(value = "/test-english")
    public String testEnglish() {
        return "test/english";
    }

    @GetMapping(value = "/testAjax")
    public String testAjax() {
        return "test/testAjax";
    }

    @GetMapping(value = "/load-english")
    public String loadEnglish(Model model) {
        model.addAttribute("questionList", questionMapperTest.questionList(1));
        return "test/loadEnglish";
    }

    @GetMapping(value = "/siljeon-paper-list-1")
    public String siljeonPaperList1() {
        return "test/siljeonPaperList1";
    }

    @GetMapping(value = "/user/paper/sound-check")
    public String soundCheck() {
        return "test/soundCheck";
    }

    @GetMapping(value = "/test-korean")
    public String testKorean() {
        return "test/korean";
    }

    @GetMapping(value = "/test-math")
    public String testMath() {
        return "test/math";
    }

    @GetMapping(value = "/test-korean2")
    public String testKorean2() {
        return "test/korean2";
    }

    @GetMapping(value = "/test-english2")
    public String testEnglish2() {
        return "test/english2";
    }

    @GetMapping(value = "/test-math2")
    public String testMath2() {
        return "test/math2";
    }

    @GetMapping(value = "/test-cbt")
    public String testCbt() {
        return "test/cbt";
    }

//    @GetMapping(value = "/test/paper/{id}")
//    public String showPaper(@PathVariable(name = "id") Integer id, Model model) {
//        List<QuestionDtoTest> questionList = null;
//        String jspFileNameWithPath = null;
//        if (id.equals(1)) {
//            questionList = questionMapperTest.questionList(1);
//            jspFileNameWithPath = "test/siljeon1/korean";
//        } else if (id.equals(2)) {
//            questionList = questionMapperTest.questionList(2);
//            jspFileNameWithPath = "test/siljeon1/english";
//        } else if (id.equals(3)) {
//            questionList = questionMapperTest.questionList(3);
//            jspFileNameWithPath = "test/siljeon1/math";
//        } else if (id.equals(4)) {
//            questionList = questionMapperTest.questionList(4);
//            jspFileNameWithPath = "test/siljeon2/korean2";
//        } else if (id.equals(5)) {
//            questionList = questionMapperTest.questionList(5);
//            jspFileNameWithPath = "test/siljeon2/english2";
//        } else if (id.equals(6)) {
//            questionList = questionMapperTest.questionList(6);
//            jspFileNameWithPath = "test/siljeon2/math2";
//        } else if (id.equals(7)) {
//            questionList = questionMapperTest.questionList(1);
//            jspFileNameWithPath = "test/cbt/cbt";
//        } else {
//            jspFileNameWithPath = null;
//        }
//        model.addAttribute("questionList", questionList);
//        return jspFileNameWithPath;
//    }
}
