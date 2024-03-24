package kr.co.chunjae.map.service;

import kr.co.chunjae.map.dto.SchoolDto;
import kr.co.chunjae.map.dto.SurroundSchoolListRequestDto;
import kr.co.chunjae.map.mapper.MapMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class MapService {

    private final MapMapper mapMapper;

    public List<SchoolDto> getSurroundSchoolList(SurroundSchoolListRequestDto requestDto) {
        return mapMapper.getSurroundSchoolList(requestDto);
    }

    public SchoolDto getSchoolInformationById(String schoolId) {
        return mapMapper.getSchoolInformationById(schoolId);
    }
}
