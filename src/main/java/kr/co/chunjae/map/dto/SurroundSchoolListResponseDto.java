package kr.co.chunjae.map.dto;

import lombok.Data;

import java.util.List;

@Data
public class SurroundSchoolListResponseDto {
    private List<SchoolDto> surroundSchoolList;
    private Integer schoolCount;
}
