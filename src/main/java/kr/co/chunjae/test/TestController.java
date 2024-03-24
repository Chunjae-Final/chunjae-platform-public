package kr.co.chunjae.test;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class TestController {

    private final TestMapper testMapper;

    @GetMapping(value = "/test-select")
    public String testSelect() {
        List<TestDto> testDtos = testMapper.testSelect();
        testDtos.stream().forEach(System.out::println);
        return "Success";
    }

    @PostMapping(value = "/jqueryAjax")
    public ResponseEntity<?> jqueryAjax(@RequestBody AnswerDtoTest answerDtoTest) {
        System.out.println("answerDto.getStudentAnswer() = " + answerDtoTest.getStudentAnswer());
        return null;
    }
}
