package kr.co.chunjae.paper.mapper;

import kr.co.chunjae.paper.dto.PaperDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface PaperMapper {
    // 신수진


    // 변재혁
    Map<String, Object> getPaperTypeByPaperId(Integer paperId);

    PaperDto getPaperByPaperId(Integer paperId);
    List<Integer> getPaperIdListByPaperTypeId(Integer paperType);


    // 이무현


    // 최경락


    // 최재혁

}
