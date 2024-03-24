package kr.co.chunjae.report.mapper;

import kr.co.chunjae.report.dto.*;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ReportMapper {
    List<CheckDTO> record(String id);

    ExamInfoDTO examInfo(ExamReqDTO examReqDTO);

    SummaryDTO summary(ExamReqDTO examReqDTO);

    List<CorrectionDTO> correction(ExamReqDTO examReqDTO);

    List<ChartDTO> difficultyAchievementRate(ExamReqDTO examReqDTO);

    List<ChartDTO> sectionAchievementRate(ExamReqDTO examReqDTO);
}
