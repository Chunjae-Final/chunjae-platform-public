package kr.co.chunjae.second.dto;


import lombok.Data;

@Data

public class UserAnswerDto {
    private Integer questionId;
    private Integer paperResultId;
    private String studentPhone;
    private String studentChoice;

}
