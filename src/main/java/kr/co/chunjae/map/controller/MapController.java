package kr.co.chunjae.map.controller;

import kr.co.chunjae.map.dto.SchoolDto;
import kr.co.chunjae.map.dto.SurroundSchoolListRequestDto;
import kr.co.chunjae.map.dto.SurroundSchoolListResponseDto;
import kr.co.chunjae.map.service.MapService;
import kr.co.chunjae.paper.service.PageService;
import kr.co.chunjae.user.dto.UserDTO;
import kr.co.chunjae.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Slf4j
@Controller
@RequestMapping(value = "/map")
@RequiredArgsConstructor
public class MapController {

    private final MapService mapService;
    private final PageService pageService;
    private final UserService userService;

    @GetMapping(value = "/surround-school")
    public String mapTestV2(Model model, Authentication authentication) {
        if (authentication != null && authentication.isAuthenticated()) { //
            String phone = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

            UserDTO userDTO = userService.getUserByPhone(phone);
            userDTO.setPassword(null); // password는 보이지 않도록 null로 설정
            model.addAttribute("user", userDTO);
        }
        return "map/surroundSchool";
    }

    @PostMapping(value = "/get-surround-school-list")
    @ResponseBody
    public ResponseEntity<?> getSurroundSchoolList(@RequestBody SurroundSchoolListRequestDto requestDto) {
        requestDto.setSurroundMeter(requestDto.getSurroundMeter() + 1);
        log.info("SurroundSchoolListRequestDto = {}", requestDto);

        List<SchoolDto> schoolList = mapService.getSurroundSchoolList(requestDto);
        log.info("내 주면 중학교 개수: {}", schoolList.size());

        SurroundSchoolListResponseDto responseDto = new SurroundSchoolListResponseDto();
        responseDto.setSurroundSchoolList(schoolList);
        responseDto.setSchoolCount(schoolList.size());
        return ResponseEntity.ok(responseDto);
    }

    @GetMapping(value = "/school-test-list")
    public String showSchoolTestListPage(@RequestParam(name = "school_id", required = false) String schoolId,
                                         Model model) {
        SchoolDto school = mapService.getSchoolInformationById(schoolId);
        model.addAttribute("school", school);
        return "map/schoolTestList";
    }

    @GetMapping(value = "/paper")
    public String showSchoolPaperPage(@RequestParam(name = "paper_id") Integer paperId,
                                      Model model) {
        Map<String, Object> paperWithQuestionListByPaperId = pageService.getPaperWithQuestionListByPaperId(paperId);

        return null;
    }
}
