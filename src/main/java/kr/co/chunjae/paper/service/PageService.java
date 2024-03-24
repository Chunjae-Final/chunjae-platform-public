package kr.co.chunjae.paper.service;

import kr.co.chunjae.paper.dto.PaperDto;
import kr.co.chunjae.paper.mapper.PaperMapper;
import kr.co.chunjae.question.dto.QuestionDto;
import kr.co.chunjae.question.service.QuestionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class PageService {
    // 신수진


    // 변재혁
    private final PaperMapper paperMapper;
    private final QuestionService questionService;

    public Map<String, Object> getPaperTypeByPaperId(Integer paperId) {
        return paperMapper.getPaperTypeByPaperId(paperId);
    }

    public Map<String, Object> getPaperWithQuestionListByPaperId(Integer paperId) {
        PaperDto paper = paperMapper.getPaperByPaperId(paperId);
        List<QuestionDto> questionList = questionService.getQuestionListByPaperId(paperId);

        Map<String, Object> result = new HashMap<>();
        result.put("paper", paper);
        result.put("questionList", questionList);
        return result;
    }

    public List<Integer> getPaperIdListByPaperTypeId(Integer paperType) {
        return paperMapper.getPaperIdListByPaperTypeId(paperType);
    }

    // 이무현


    // 최경락


    // 최재혁

}
