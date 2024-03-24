package kr.co.chunjae.second.mapper;

import kr.co.chunjae.second.dto.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@Mapper
public interface SecondMapper {


    void saveSecondResult(SecondResultDto secondResultDto);

    int selectSecondResult(SecondResultDto secondResultDto);

    void deleteSecondResult(Integer questionId);

    String selectRealAnswer(Integer questionId);

    Integer addPaperResultId(StartExamDto startExamDto);

    Integer checkTestProgress(StartExamDto startExamDto);

    Integer selectPaperResultId(StartExamDto startExamDto);

    void finishExam(FinishExamDto finishExamDto);
    Integer selectPreviousExam(FinishExamDto finishExamDto);

    List<UserAnswerDto> userChosenAnswer(UserAnswerDto userAnswerDto);


    void deletePreExamAnswer(Integer previousExamId);

    void deletePreExam(Integer previousExamId);

    HashSet<Integer> getQuestionId(FinishExamDto finishExamDto);

    HashSet<Integer> getUserAnswerId(FinishExamDto finishExamDto);

    void insertNull(@Param("finishExamDto") FinishExamDto finishExamDto,
                    @Param("setNullList") List<Integer> setNullList);

    LocalDateTime getTimeFromServer(GetTimeDto getTimeDto);

    Integer checkQuestionIdExist(Integer questionId);

    void insertQuestionId(Integer questionId);

    HashSet<Integer> userCorrectResult(FinishExamDto finishExamDto);

    void userCorrectRateUpdate(@Param("correctQuestionIdList") HashSet<Integer> correctQuestionIdList);

    void userinCorrectRateUpdate(@Param("incorrectQuestionIdList") List<Integer> incorrectQuestionIdList);
}
