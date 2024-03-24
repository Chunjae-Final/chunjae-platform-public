package kr.co.chunjae.paper_result.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PaperResultDto { // 시험 응시 결과
    private Integer id; // PK, 응시 결과 ID
    private String studentPhone; // FK, 학생핸드폰번호
    private Integer paperId; // FK, 시험지 ID
    private LocalDateTime startTimestamp; // 응시 시작시간
    private LocalDateTime endTimestamp; // 응시 종료시간
    private Integer spendTime; // 응시 소요 시간 (seconds)
    private String finished; // 최종 제출 여부 (Y or N)
}
