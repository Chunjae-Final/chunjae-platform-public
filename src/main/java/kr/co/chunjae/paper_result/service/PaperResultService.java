package kr.co.chunjae.paper_result.service;

import kr.co.chunjae.paper_result.mapper.PaperResultMapper;
import kr.co.chunjae.question_result.service.QuestionResultService;
import kr.co.chunjae.test.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class PaperResultService {
    // 신수진


    // 변재혁
    private final PaperResultMapper paperResultMapper;
    private final QuestionResultService questionResultService;

    public void generateNewPaperResultId(GenerateNewPaperResultIdDto requestDto) {
        paperResultMapper.generateNewPaperResultId(requestDto);
    }

    public Map<String, Object> checkStudentFinishedPaper(CheckStudentFinishedPaperDto requestDto) {
        Map<String, Object> resultMap = new HashMap<>();
        Integer paperResultId = paperResultMapper.checkStudentFinishedPaper(requestDto);
        if (paperResultId.equals(-1)) {
            resultMap.put("result", "NOT EXISTS");
            return resultMap;
        } else {
            resultMap.put("result", "EXISTS");
        }
        // 만약 현재 특정 시험지를 풀고 있고 아직 최종 제출 버튼을 클릭하지 않은 상태라면,
        // 시험응시결과ID를 사용해서 학생이 선택했었던 초이스들 전부 다 조회
        Map<String, Object> queryMap = new HashMap<>();
        queryMap.put("studentPhone", requestDto.getStudentPhone());
        queryMap.put("paperResultId", paperResultId);
        List<StudentChoiceDto> studentChoiceList = questionResultService.getAllStudentChoicesByPaperResultId(queryMap);

        resultMap.put("paperResultId", paperResultId);
        resultMap.put("studentChoiceList", studentChoiceList);
        return resultMap;
    }

    public List<Integer> getPaperResultIdListForDelete(PaperFinalSubmitDto requestDto) {
        Map<String, Object> queryMap = new HashMap<>();
        queryMap.put("studentPhone", requestDto.getStudentPhone());
        queryMap.put("paperId", requestDto.getPaperId());
        queryMap.put("paperResultId", requestDto.getPaperResultId());

        return paperResultMapper.getPaperResultIdListForDelete(queryMap);
    }

    public Integer writeEndTimestampAndChangeFinishedToY(Integer paperResultId) {
        return paperResultMapper.writeEndTimestampAndChangeFinishedToY(paperResultId);
    }

    public Integer deletePaperResultByIdList(List<Integer> paperResultIdListForDelete) {
        return paperResultMapper.deletePaperResultByIdList(paperResultIdListForDelete);
    }

    public List<PaperStatusDto> queryForSpecifyStatusValuesInPaperListPage(String studentPhone,
                                                                           List<Integer> paperIdListByPaperTypeId) {
        Map<String, Object> queryMap = new HashMap<>();
        queryMap.put("studentPhone", studentPhone);
        queryMap.put("paperIdList", paperIdListByPaperTypeId);
        return paperResultMapper.queryForSpecifyStatusValuesInPaperListPage(queryMap);
    }

    public LastAccessTimestampAndSpendTimeDto getLastAccessTimestampAndSpendTime(Integer paperResultId) {
        return paperResultMapper.getLastAccessTimestampAndSpendTime(paperResultId);
    }

    public Integer updateLastAccessTimestampAndSpendTime(Integer paperResultId, Integer updatedSpendTime) {
        return paperResultMapper.updateLastAccessTimestampAndSpendTime(paperResultId, updatedSpendTime);
    }

    public LocalDateTime getStartTimestampByPaperResultId(Integer paperResultId) {
        return paperResultMapper.getStartTimestampByPaperResultId(paperResultId);
    }

    public Integer getPaperIdByPaperResultId(Integer paperResultId) {
        return paperResultMapper.getPaperIdByPaperResultId(paperResultId);
    }

    // 이무현


    // 최경락


    // 최재혁

}
