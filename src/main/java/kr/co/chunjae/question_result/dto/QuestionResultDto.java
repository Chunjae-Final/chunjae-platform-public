package kr.co.chunjae.question_result.dto;

import lombok.Data;

@Data
public class QuestionResultDto {
    private Integer id; // PK, 문항별 응시 결과 ID
    private String studentPhone; // FK, 학생 핸드폰
    private Integer paperResultId; // FK, 시험응시결과ID
    private Integer questionId; // FK, 문항 ID
    private String studentChoice; // 사용자가 선택한 정답 (문자열 JSON)
    private String correct; // 정답여부 (Y or N)
}
