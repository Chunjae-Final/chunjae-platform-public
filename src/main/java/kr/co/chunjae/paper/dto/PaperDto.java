package kr.co.chunjae.paper.dto;

import lombok.Data;

@Data
public class PaperDto {
    private Integer id; // PK, 시험지 ID
    private Integer subjectId; // FK, 과목ID (국어-1, 영어-2, 수학-3, 혼합(CBT용)-4)
    private Integer paperTypeId; // FK, 시험지 타입 (CBT-1, 실전시험 I-2, 실전시험 II-3)
    private String name; // 시험지명
    private Integer grade; // 시험지 대상 학년
    private Integer timeLimit; // 시험 제한 시간 (seconds)
    private Integer questionCount; // 문항 수

    // 신수진

    // 변재혁

    // 이무현

    // 최경락

    // 최재혁

}
