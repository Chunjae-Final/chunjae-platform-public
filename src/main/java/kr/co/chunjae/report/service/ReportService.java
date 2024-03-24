package kr.co.chunjae.report.service;

import kr.co.chunjae.report.dto.*;
import kr.co.chunjae.report.mapper.ReportMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ReportService {
    private final ReportMapper reportMapper;

    public List<CheckDTO> record(String id) {
        return reportMapper.record(id);
    }

    public ExamInfoDTO examInfo(ExamReqDTO examReqDTO) {
        return reportMapper.examInfo(examReqDTO);
    }

    public SummaryDTO summary(ExamReqDTO examReqDTO) {
        return reportMapper.summary(examReqDTO);
    }

    public List<CorrectionDTO> correction(ExamReqDTO examReqDTO) {
        return reportMapper.correction(examReqDTO);
    }

    public List<ChartDTO> difficultyAchievementRate(ExamReqDTO examReqDTO) {
        return reportMapper.difficultyAchievementRate(examReqDTO);
    }

    public List<ChartDTO> sectionAchievementRate(ExamReqDTO examReqDTO) {
        return reportMapper.sectionAchievementRate(examReqDTO);
    }
}
