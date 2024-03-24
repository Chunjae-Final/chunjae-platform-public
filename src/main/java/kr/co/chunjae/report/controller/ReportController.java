package kr.co.chunjae.report.controller;

import kr.co.chunjae.report.dto.*;
import kr.co.chunjae.report.service.ReportService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/report")
public class ReportController {
    private final ReportService reportService;

    @GetMapping("/record")
    public ResponseEntity<?> record(@RequestParam("id") String id) {
        List<CheckDTO> checkDTOList = reportService.record(id);
        if (checkDTOList.isEmpty()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("no");
        }else {
            return ResponseEntity.status(HttpStatus.OK).body(checkDTOList);
        }
    }

    @GetMapping("/examInfo")
    public ResponseEntity<?> examInfo(ExamReqDTO examReqDTO){
        ExamInfoDTO examInfoDTO = reportService.examInfo(examReqDTO);
        return ResponseEntity.status(HttpStatus.OK).body(examInfoDTO);
    }

    @GetMapping("/summary")
    public ResponseEntity<?> summary(ExamReqDTO examReqDTO){
        SummaryDTO summaryDTO = reportService.summary(examReqDTO);
        return ResponseEntity.status(HttpStatus.OK).body(summaryDTO);
    }

    @GetMapping("/correction")
    public ResponseEntity<?> correction(ExamReqDTO examReqDTO){
        List<CorrectionDTO> correctionDTOList = reportService.correction(examReqDTO);
        return ResponseEntity.status(HttpStatus.OK).body(correctionDTOList);
    }

    @GetMapping("/difficulty")
    public ResponseEntity<?> difficultyAchievementRate(ExamReqDTO examReqDTO){
        List<ChartDTO> chartDTOList = reportService.difficultyAchievementRate(examReqDTO);
        return ResponseEntity.status(HttpStatus.OK).body(chartDTOList);
    }
    @GetMapping("/section")
    public ResponseEntity<?> sectionAchievementRate(ExamReqDTO examReqDTO){
        List<ChartDTO> chartDTOList = reportService.sectionAchievementRate(examReqDTO);
        return ResponseEntity.status(HttpStatus.OK).body(chartDTOList);
    }
}
