package kr.co.chunjae.report.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class CorrectionDTO {
    private int questionId;
    private int paperResultId;
    private String sectionName;
    private String unitName;
    private String correct;
    private int correctRate;
}
