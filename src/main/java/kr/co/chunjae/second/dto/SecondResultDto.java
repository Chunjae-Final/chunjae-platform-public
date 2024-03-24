package kr.co.chunjae.second.dto;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;


@Data
public class SecondResultDto {
    private Integer id;
    private String studentPhone;
    private Integer paperId;
    private Integer paperResultId;
    private Integer questionId;
    private String studentChoice;
    private LocalDateTime startTimestamp;
    private LocalDate endTimestamp;
    private LocalDate spendTime;
    private String finished;
    private String correct;
    private String answer;


}
