package kr.co.chunjae.map.dto;

import lombok.Data;
import org.locationtech.jts.geom.Point;

@Data
public class SchoolDto {
    private String id;
    private String schoolName;
    private String schoolType;
    private String address;
    private String latitude;
    private String longitude;
//    private Point latitudeLongitude;
}
