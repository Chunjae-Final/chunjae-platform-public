package kr.co.chunjae.question.service;

import kr.co.chunjae.question.dto.QuestionDto;
import kr.co.chunjae.question.mapper.QuestionMapper;
import kr.co.chunjae.question_result.service.QuestionResultService;
import kr.co.chunjae.test.CheckStudentAnswerDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class QuestionService {
    // 신수진


    // 변재혁
    private final QuestionMapper questionMapper;

    public List<QuestionDto> getQuestionListByPaperId(Integer paperId) {
        List<QuestionDto> questionList = questionMapper.getQuestionListByPaperId(paperId);
        return questionList;
    }

    public String getQuestionAnswerByQuestionId(Integer questionId) {
        String answer = questionMapper.getQuestionAnswerByQuestionId(questionId);
        return answer;
    }

    public String compareStudentChoiceAnswerWithCorrectAnswer(CheckStudentAnswerDto requestDto) {
        String studentChoiceAnswer = requestDto.getStudentChoice();
        String correctAnswer = getQuestionAnswerByQuestionId(requestDto.getQuestionId());
        String yesOrNo;
        if (studentChoiceAnswer.equals(correctAnswer)) {
            yesOrNo = "Y";
        } else {
            yesOrNo = "N";
        }
        return yesOrNo;
    }

    public List<Integer> getAllQuestionIdListByPaperId(Integer paperId) {
        return questionMapper.getAllQuestionIdListByPaperId(paperId);
    }

    public QuestionDto getSpecificQuestionByQuestionId(Integer questionId) {
        return questionMapper.getSpecificQuestionByQuestionId(questionId);
    }

    // 이무현


    // 최경락


    // 최재혁

}
