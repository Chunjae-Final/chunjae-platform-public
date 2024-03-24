package kr.co.chunjae.second.dto;

import lombok.Data;

@Data
public class FinishExamDto {
    private Integer id;
    private Integer paperId;
    private Integer questionId;
    private String studentPhone;
    private Integer paperResultId;
}
