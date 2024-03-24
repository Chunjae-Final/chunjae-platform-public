package kr.co.chunjae.test;

import lombok.Data;

@Data
public class GenerateNewPaperResultIdDto {
    private String studentPhone;
    private Integer paperId;
    private Integer generatedResultId;
}
