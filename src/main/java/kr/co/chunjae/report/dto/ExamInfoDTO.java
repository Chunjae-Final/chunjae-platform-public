package kr.co.chunjae.report.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

@Getter
@Setter
@ToString
public class ExamInfoDTO {
    private String name;
    private String grade;
    private LocalDate startTimestamp;
}
