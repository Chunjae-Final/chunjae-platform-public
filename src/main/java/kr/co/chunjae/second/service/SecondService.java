package kr.co.chunjae.second.service;


import kr.co.chunjae.second.dto.*;
import kr.co.chunjae.second.mapper.SecondMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@Slf4j
@RestController
@RequiredArgsConstructor
public class SecondService {

    private final SecondMapper secondMapper;

    public void saveSecondResult(SecondResultDto secondResultDto) {
        String realAnswer = secondMapper.selectRealAnswer(secondResultDto.getQuestionId());//진짜답
        if(realAnswer.equals(secondResultDto.getStudentChoice())){//사용자입력답비교
            secondResultDto.setCorrect("Y");//맞으면y
        }else{
            secondResultDto.setCorrect("N");//틀리면n
        }

        int exist = secondMapper.selectSecondResult(secondResultDto);//사용자가 푼답이 존재하는지체크
        if(exist == 0) {//존재안하면
            secondMapper.saveSecondResult(secondResultDto);//저장
        }else{//존재하면 지우고 저장
            secondMapper.deleteSecondResult(secondResultDto.getQuestionId());
            secondMapper.saveSecondResult(secondResultDto);
        }

        log.info(String.valueOf(secondResultDto.getQuestionId()));

    }

    public StartExamDto addNewExam(StartExamDto startExamDto) {
        Integer testProgress = secondMapper.checkTestProgress(startExamDto);//finished 체크하기
        if(testProgress == 0) {//전에본 테스트가 finished일떄
            secondMapper.addPaperResultId(startExamDto);//paperResultId가져오기
        }else{
            Integer paperResultId = secondMapper.selectPaperResultId(startExamDto);//finished가 아닐때
            startExamDto.setPaperResultId(paperResultId);//paperResultId 세팅
        }

        return startExamDto;
    }

    public void finishExam(FinishExamDto finishExamDto) {
        Integer previousExamId = secondMapper.selectPreviousExam(finishExamDto);//전시험지를 가져옴
        if (previousExamId != null) {
            secondMapper.deletePreExamAnswer(previousExamId);//전시험지 답을 지움
            secondMapper.deletePreExam(previousExamId);//전시험지를 지움
        }

        HashSet<Integer> questionIdList = secondMapper.getQuestionId(finishExamDto);//이 시험지의 questionId가져오기
        HashSet<Integer> userAnswerIdList = secondMapper.getUserAnswerId(finishExamDto);//questionAnswerId 가져오기

        questionIdList.removeAll(userAnswerIdList);//빼서 테이블에 답이입력안되있는 번호 구하기

        List<Integer> setNullList = new ArrayList<>(questionIdList);
        if(!setNullList.isEmpty()) {//setNullList가 비어있지 않을때 tb_quesion_result에 null값으로 생성
            secondMapper.insertNull(finishExamDto, setNullList);
        }
        secondMapper.finishExam(finishExamDto);//FinishY로 업데이트


        HashSet<Integer> existQuestionIdList = secondMapper.getQuestionId(finishExamDto);
        for(Integer questionId:existQuestionIdList){//questionid를 가져옴
            Integer checkQuestionId = secondMapper.checkQuestionIdExist(questionId);
            if(checkQuestionId == 0){//만약에 correct_rate에 id가 생성안되있을때
                secondMapper.insertQuestionId(questionId);//id생성
            }
        }

        HashSet<Integer> correctQuestionIdList = secondMapper.userCorrectResult(finishExamDto);//맞은문제번호가져옴
        existQuestionIdList.removeAll(correctQuestionIdList);//전체 id에서 맞은번호빼서 틀린번호구함

        List<Integer> incorrectQuestionIdList = new ArrayList<>(existQuestionIdList);//틀린번호

        if(!correctQuestionIdList.isEmpty()) {//맞은번호가 비어있지 않을때
            secondMapper.userCorrectRateUpdate(correctQuestionIdList);
        }
        if(!incorrectQuestionIdList.isEmpty()) {//틀린번호가 비어잇지 않을때
            secondMapper.userinCorrectRateUpdate(incorrectQuestionIdList);
        }


        }



    public List<UserAnswerDto> userChosenAnswer(UserAnswerDto userAnswerDto) {
        return secondMapper.userChosenAnswer(userAnswerDto);
    }

    public LocalDateTime getTimeFromServer(GetTimeDto getTimeDto) {
        return secondMapper.getTimeFromServer(getTimeDto);//시간을 서버에서가져오기
    }
}
