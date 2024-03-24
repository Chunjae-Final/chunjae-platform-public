package kr.co.chunjae.report.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class SummaryDTO {
    //평가 문항 수
    private int questionCount;
    //정답 문항 수
    private int correctCount;
    //점수
    private int score;
}
