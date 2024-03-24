package kr.co.chunjae.test;

import lombok.Data;

@Data
public class QuestionDtoTest {
    private Integer id; // PK, 문항 ID
    private Integer paperId; // FK, 시험지 ID
    private Integer sectionId; // FK, 교과영역 ID
    private Integer unitId; // FK, 관련단원 ID
    private Integer sequence; // 문항 순서 (1번부터 시작)
    private Integer score; // 문항별 점수
    private String questionType; // 문항 타입 (IT01, TT07 etc...)
    private String title; // 문제 제목
    private String content; // 본문
    private String choices; // 보기 (선지)
    private String otherChoices; // 기타보기 (드래그 앤 드랍용 선지 등)
    private String answer; // 정답 (JSON)
    private String explanation; // 해설
}
