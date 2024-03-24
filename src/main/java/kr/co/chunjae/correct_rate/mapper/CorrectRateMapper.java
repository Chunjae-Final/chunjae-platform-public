package kr.co.chunjae.correct_rate.mapper;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CorrectRateMapper {
    Integer checkIfQuestionIdExists(Integer questionId);
    Integer insertNewQuestionId(Integer questionId);
    Integer updateStudentCountAndCorrectCountByCorrectQuestionIdList(List<Integer> correctQuestionIdList);
    Integer updateStudentCountByIncorrectQuestionIdList(List<Integer> incorrectQuestionIdList);
}
