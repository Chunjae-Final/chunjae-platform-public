package kr.co.chunjae.second.dto;

import lombok.Data;


import java.time.LocalDateTime;

@Data
public class GetTimeDto {
    private Integer paperResultId;
    private String studentPhone;
    private LocalDateTime startTimestamp;

}
