package kr.co.chunjae.second.controller;


import kr.co.chunjae.second.dto.*;
import kr.co.chunjae.second.service.SecondService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/second")
public class SecondController {

    private final SecondService secondService;

    @PostMapping("/item/user-item-result")
    @ResponseBody
    public ResponseEntity<?> saveSecondResult(@RequestBody SecondResultDto secondResultDto) {
        secondService.saveSecondResult(secondResultDto);
        log.info(String.valueOf(secondResultDto));
        return ResponseEntity.status(HttpStatus.OK).body("");
    }

    @PostMapping("/item/add-new-exam")
    @ResponseBody
    public ResponseEntity<?> addNewExam(@RequestBody StartExamDto startExamDto){
        StartExamDto examDto = secondService.addNewExam(startExamDto);
        Integer paperResultId = examDto.getPaperResultId();
        log.info(String.valueOf(paperResultId));
        return ResponseEntity.ok().body(paperResultId);
    }

    @PutMapping("/exam/complete-exam")
    @ResponseBody
    public ResponseEntity<?> finishExam(@RequestBody FinishExamDto finishExamDto){
        secondService.finishExam(finishExamDto);
        return ResponseEntity.status(HttpStatus.OK).body("");

    }

    @PostMapping("/item/user-answer")
    @ResponseBody
    public ResponseEntity<?> userChosenAnswer(@RequestBody UserAnswerDto userAnswerDto){
        List<UserAnswerDto> userChosenDto = secondService.userChosenAnswer(userAnswerDto);
        log.info(String.valueOf(userChosenDto));
        return ResponseEntity.ok().body(userChosenDto);
    }


    @PostMapping("/item/get-time")
    @ResponseBody
    public ResponseEntity<?> getTimeFromServer(@RequestBody GetTimeDto getTimeDto){
        LocalDateTime startTime = secondService.getTimeFromServer(getTimeDto);
        return ResponseEntity.ok().body(startTime);
    }

}
