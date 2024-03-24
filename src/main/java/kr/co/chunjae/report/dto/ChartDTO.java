package kr.co.chunjae.report.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ChartDTO {
    //난이도
    private String criteria;
    //전체 평균
    private int overallAverage;
    //내 평균
    private int myAverage;
}
