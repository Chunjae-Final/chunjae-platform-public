package kr.co.chunjae.report.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ExamReqDTO {
    //사용자 id : 핸드폰 번호
    private String id;
    //시험 id
    private int paperId;
    //시험 결과 id
    private int paperResultId;
}
