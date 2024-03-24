package kr.co.chunjae.test;

import lombok.Data;

@Data
public class CheckStudentAnswerDto {
    private String studentPhone;
    private Integer paperResultId;
    private Integer questionId;
    private String studentChoice;
}
