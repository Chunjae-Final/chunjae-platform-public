package kr.co.chunjae.paper_result.mapper;

import kr.co.chunjae.first.dto.newPaperResultDTO;
import kr.co.chunjae.first.dto.paperFinishedDTO;
import kr.co.chunjae.first.dto.paperResultDTO;
import kr.co.chunjae.first.dto.saveStudentAnswerDTO;
import kr.co.chunjae.test.CheckStudentFinishedPaperDto;
import kr.co.chunjae.test.GenerateNewPaperResultIdDto;
import kr.co.chunjae.test.LastAccessTimestampAndSpendTimeDto;
import kr.co.chunjae.test.PaperStatusDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Map;

import java.util.Map;

@Mapper
public interface PaperResultMapper {
    // 신수진
    int newPaperResult(newPaperResultDTO newPaperResultDTO);
    int existPaperResult(paperResultDTO paperResultDTO);
    void paperFinished(paperFinishedDTO paperFinishedDTO);
    int finishedPaperCount(paperFinishedDTO paperFinishedDTO);
    void deleteFinishedPaper(paperFinishedDTO paperFinishedDTO);
//    List<Date> loadPaperTimestamp(paperResultDTO paperResultDTO);
    void updateLastAccessTime(saveStudentAnswerDTO saveStudentAnswerDTO);



    // 변재혁
    void generateNewPaperResultId(GenerateNewPaperResultIdDto requestDto);
    Integer checkStudentFinishedPaper(CheckStudentFinishedPaperDto requestDto);
    List<Integer> getPaperResultIdListForDelete(Map<String, Object> map);
    Integer writeEndTimestampAndChangeFinishedToY(Integer paperResultId);
    Integer deletePaperResultByIdList(List<Integer> paperResultIdListForDelete);
    List<PaperStatusDto> queryForSpecifyStatusValuesInPaperListPage(Map<String, Object> map);
    LastAccessTimestampAndSpendTimeDto getLastAccessTimestampAndSpendTime(Integer paperResultId);
    Integer updateLastAccessTimestampAndSpendTime(@Param("paperResultId") Integer paperResultId,
                                                  @Param("updatedSpendTime") Integer updatedSpendTime);
    LocalDateTime getStartTimestampByPaperResultId(Integer paperResultId);
    Integer getPaperIdByPaperResultId(Integer paperResultId);



    // 이무현


    // 최경락


    // 최재혁


}
