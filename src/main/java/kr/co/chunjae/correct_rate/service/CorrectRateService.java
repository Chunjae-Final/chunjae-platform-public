package kr.co.chunjae.correct_rate.service;

import kr.co.chunjae.correct_rate.mapper.CorrectRateMapper;
import kr.co.chunjae.question_result.service.QuestionResultService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class CorrectRateService {
    // 신수진


    // 변재혁
    private final CorrectRateMapper correctRateMapper;
    private final QuestionResultService questionResultService;

    public void updateStudentCountAndCorrectCount(Integer paperResultId) {
        // tb_question_result 테이블에서, paper_result_id를 활용하여 사용자가 푼 문항들 전부 다 갖고옴
        List<Map<String, Object>> questionIdAndCorrectListByPaperResultId =
                questionResultService.getQuestionIdAndCorrectListByPaperResultId(paperResultId);

        List<Integer> correctQuestionIdList = new ArrayList<>();
        List<Integer> incorrectQuestionIdList = new ArrayList<>();
        List<Integer> allQuestionIdList = new ArrayList<>();

        for (Map<String, Object> map : questionIdAndCorrectListByPaperResultId) {
            Integer questionId = (Integer) map.get("question_id");
            String correct = (String) map.get("correct"); // Y or N
            allQuestionIdList.add(questionId);
            if (correct.equals("Y")) {
                correctQuestionIdList.add(questionId);
            } else {
                incorrectQuestionIdList.add(questionId);
            }
        }

        // 먼저, allQuestionIdList에 있는 모든 questionId들에 대해,
        // correct_rate 테이블에 id로 먼저 존재하는지부터 확인
        checkIfQuestionIdExistsInCorrectRateTable(allQuestionIdList);

        // 학생이 맞춘 questionId들은 student_count + 1, correct_count + 1
        if (correctQuestionIdList.size() >= 1) {
            correctRateMapper.updateStudentCountAndCorrectCountByCorrectQuestionIdList(correctQuestionIdList);
        }
        if (incorrectQuestionIdList.size() >= 1) {
            // 학생이 틀린 문항들은 student_count만 + 1
            correctRateMapper.updateStudentCountByIncorrectQuestionIdList(incorrectQuestionIdList);
        }
    }

    public void checkIfQuestionIdExistsInCorrectRateTable(List<Integer> allQuestionIdList) {
        // SELECT COUNT(*) FROM tb_correct_rate WHERE id = #{questionId} 로 일단 먼저 존재하는지 부터 확인
        // 만약 COUNT(*) 했을 때 값이 0이 나온다면, 일단 해당 questionId에 대해 insert 해주는 작업부터 필요
        for (Integer questionId : allQuestionIdList) {
            Integer count = correctRateMapper.checkIfQuestionIdExists(questionId);
            if (count.equals(0)) {
                correctRateMapper.insertNewQuestionId(questionId);
            }
        }
    }

    // 이무현


    // 최경락


    // 최재혁


}
