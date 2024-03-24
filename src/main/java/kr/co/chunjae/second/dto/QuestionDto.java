package kr.co.chunjae.second.dto;

import lombok.Data;

@Data
public class QuestionDto {
    private Integer id; // 문항 ID
    private Integer sequence; // 문항 순서 (1번부터 시작~)
    private String subjectSection; // 교과 영역
    private String unit; // 관련 단원
    private String averageCorrectRate; // 평균 정답률
    private Integer score; // 문항별 점수
    private String questionType; // IT01, TT09...etc
    private String title; // 문제
    private String content; // 본문
    private String options; // 보기(선지)
    private String answer; // 정답 (JSON)

}
