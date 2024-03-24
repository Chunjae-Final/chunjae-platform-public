package kr.co.chunjae.test;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface QuestionMapperTest {
    List<QuestionDtoTest> questionList(Integer paperId);
}
