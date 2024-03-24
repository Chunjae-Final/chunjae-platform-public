package kr.co.chunjae.test;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class LastAccessTimestampAndSpendTimeDto {
    private LocalDateTime lastAccessTimestamp;
    private Integer spendTime;
}
