package kr.co.chunjae.question_result.mapper;

import kr.co.chunjae.first.dto.paperFinishedDTO;
import kr.co.chunjae.first.dto.saveCorrectRateDTO;
import kr.co.chunjae.first.dto.saveStudentAnswerDTO;
import kr.co.chunjae.first.dto.studentChoiceAnswerDTO;
import kr.co.chunjae.test.StudentChoiceDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface QuestionResultMapper {
    // 신수진
    void saveStudentAnswer(saveStudentAnswerDTO saveStudentAnswerDTO);

    void studentAnswerCorrect(Map<String, Object> correctMap);

    String questionAnswer(int questionId);

    int existStudentAnswer(saveStudentAnswerDTO saveStudentAnswerDTO);

    void deleteStudentAnswer(saveStudentAnswerDTO saveStudentAnswerDTO);

    List<studentChoiceAnswerDTO> studentChoiceAnswerList(
            Map<String, Object> studentChoiceMap);

    List<Integer> findQuestionResult(Integer paperResultId);

    void insertUnansweredQuestion(Map<String, Object> unansweredMap);
    List<saveCorrectRateDTO> findSolvedQuestion(
            paperFinishedDTO paperFinishedDTO);
    List<Integer> correctRateQuestion();
    void addQuestionId(Integer questionId);
    void saveCorrectRateByCorrectQuestion(@Param("correctQuestion") List<Integer> correctQuestion);
    void saveCorrectRateByIncorrectQuestion(@Param("incorrectQuestion") List<Integer> incorrectQuestion);


    // 변재혁
    Integer deletePreviousStudentChoiceAnswer(Map<String, Object> map);
    Integer insertResultOfCheckStudentAnswer(Map<String, Object> map);
    List<StudentChoiceDto> getAllStudentChoicesByPaperResultId(Map<String, Object> map);
    Integer deleteQuestionResultByPaperResultId(List<Integer> paperResultIdListForDelete);
    List<Integer> getAllSolvedQuestionIdListByPaperResultId(Integer paperResultId);
    Integer fillUpNotSolvedQuestions(Map<String, Object> map);
    List<Map<String, Object>> getQuestionIdAndCorrectListByPaperResultId(Integer paperResultId);
    String getStudentChoiceByPaperResultIdAndQuestionId(@Param("paperResultId") Integer paperResultId,
                                                        @Param("questionId") Integer questionId);

    // 이무현


    // 최경락


    // 최재혁


}
