package kr.co.chunjae.first.dto;

import lombok.Data;

@Data
public class saveStudentAnswerDTO {
    private String studentPhone;
    private int paperResultId;
    private int questionId;
    private String studentChoice;
}
