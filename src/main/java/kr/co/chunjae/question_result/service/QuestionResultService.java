package kr.co.chunjae.question_result.service;

import kr.co.chunjae.question.service.QuestionService;
import kr.co.chunjae.question_result.mapper.QuestionResultMapper;
import kr.co.chunjae.test.CheckStudentAnswerDto;
import kr.co.chunjae.test.StudentChoiceDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class QuestionResultService {
    // 신수진


    // 변재혁
    private final QuestionResultMapper questionResultMapper;
    private final QuestionService questionService;

    public void deletePreviousStudentChoiceAnswer(CheckStudentAnswerDto requestDto) {
        Map<String, Object> map = new HashMap<>();
        map.put("paperResultId", requestDto.getPaperResultId());
        map.put("questionId", requestDto.getQuestionId());
        Integer deleteCount = questionResultMapper.deletePreviousStudentChoiceAnswer(map);
        log.info("시험응시결과ID: " + requestDto.getPaperResultId() + ", 문항ID: " + requestDto.getQuestionId()
        + "가 tb_question_result에 존재했는지 여부 = " + deleteCount);
    }

    public void insertResultOfCheckStudentAnswer(String yesOrNo, CheckStudentAnswerDto requestDto) {
        Map<String, Object> insertMap = new HashMap<>();
        insertMap.put("studentPhone", requestDto.getStudentPhone());
        insertMap.put("paperResultId", requestDto.getPaperResultId());
        insertMap.put("questionId", requestDto.getQuestionId());
        insertMap.put("studentChoice", requestDto.getStudentChoice());
        insertMap.put("correct", yesOrNo);

        questionResultMapper.insertResultOfCheckStudentAnswer(insertMap);
    }

    public List<StudentChoiceDto> getAllStudentChoicesByPaperResultId(Map<String, Object> queryMap) {
        return questionResultMapper.getAllStudentChoicesByPaperResultId(queryMap);
    }

    public void deleteQuestionResultByPaperResultId(List<Integer> paperResultIdListForDelete) {
        questionResultMapper.deleteQuestionResultByPaperResultId(paperResultIdListForDelete);
    }

    public void fillUpNotSolvedQuestions(String studentPhone, Integer paperId, Integer paperResultId) {
        // paperId로 tb_question 테이블에서 paper_id 가 paperId 인 것을 전부 다 가져옴
        List<Integer> allQuestionIdListByPaperId = questionService.getAllQuestionIdListByPaperId(paperId);
        Set<Integer> allQuestionIdSetByPaperId = new HashSet<>(allQuestionIdListByPaperId);

        // paperResultId로 tb_question_result 테이블에서 사용자가 푼 문제들 번호를 갖고옴
        List<Integer> allSolvedQuestionIdListByPaperResultId =
                questionResultMapper.getAllSolvedQuestionIdListByPaperResultId(paperResultId);
        Set<Integer> allSolvedQuestionIdSetByPaperResultId = new HashSet<>(allSolvedQuestionIdListByPaperResultId);

        // 차집합 (학생이 아예 풀지도 않은 문제들)
        allQuestionIdSetByPaperId.removeAll(allSolvedQuestionIdSetByPaperResultId);
        if (allQuestionIdSetByPaperId.size() > 0) {
            Map<String, Object> queryMap = new HashMap<>();
            queryMap.put("studentPhone", studentPhone);
            queryMap.put("paperResultId", paperResultId);
            queryMap.put("questionIdList", new ArrayList<>(allQuestionIdSetByPaperId));
            questionResultMapper.fillUpNotSolvedQuestions(queryMap);
        }
    }

    public List<Map<String, Object>> getQuestionIdAndCorrectListByPaperResultId(Integer paperResultId) {
        return questionResultMapper.getQuestionIdAndCorrectListByPaperResultId(paperResultId);
    }

    public String getStudentChoiceByPaperResultIdAndQuestionId(Integer paperResultId, Integer questionId) {
        return questionResultMapper.getStudentChoiceByPaperResultIdAndQuestionId(paperResultId, questionId);
    }

    // 이무현


    // 최경락


    // 최재혁

}
