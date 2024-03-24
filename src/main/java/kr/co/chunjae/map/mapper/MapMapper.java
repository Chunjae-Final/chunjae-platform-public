package kr.co.chunjae.map.mapper;

import kr.co.chunjae.map.dto.SchoolDto;
import kr.co.chunjae.map.dto.SurroundSchoolListRequestDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MapMapper {
    List<SchoolDto> getSurroundSchoolList(SurroundSchoolListRequestDto requestDto);

    SchoolDto getSchoolInformationById(String schoolId);
}
