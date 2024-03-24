package kr.co.chunjae.map.dto;

import lombok.Data;

@Data
public class SurroundSchoolListRequestDto {
    private Integer surroundMeter;
    private Double currentLatitude;
    private Double currentLongitude;
}
