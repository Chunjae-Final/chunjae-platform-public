package kr.co.chunjae.question.mapper;

import kr.co.chunjae.question.dto.QuestionDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface QuestionMapper {
    // 신수진
    List<Integer> findPaperQuestion(int paperId);

    // 변재혁
    List<QuestionDto> getQuestionListByPaperId(Integer paperId);
    String getQuestionAnswerByQuestionId(Integer questionId);
    List<Integer> getAllQuestionIdListByPaperId(Integer paperId);
    QuestionDto getSpecificQuestionByQuestionId(Integer questionId);




    // 이무현


    // 최경락


    // 최재혁

}
