package kr.co.chunjae.report.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class CheckDTO {
    private Integer paperId;
    private Integer subjectId;
    private String subjectName;
    private Integer paperRound;
    private Integer paperResultId;
}
