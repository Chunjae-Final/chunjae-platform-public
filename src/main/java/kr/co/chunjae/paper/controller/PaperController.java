package kr.co.chunjae.paper.controller;

import kr.co.chunjae.paper.dto.PaperDto;
import kr.co.chunjae.paper.service.PageService;
import kr.co.chunjae.paper_result.service.PaperResultService;
import kr.co.chunjae.question.dto.QuestionDto;
import kr.co.chunjae.user.dto.UserDTO;
import kr.co.chunjae.user.service.UserService;
import kr.co.chunjae.test.PaperStatusDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping(value = "/paper")
public class PaperController {
    // 신수진


    // 변재혁
    private final PageService pageService;
    private final PaperResultService paperResultService;
    private final UserService userService;

    @GetMapping(value = "/paper-list")
    public String showPaperListPage(@RequestParam("paper_type") Integer paperType, Model model, Authentication authentication) {

        if (authentication != null && authentication.isAuthenticated()) { //
            String phone = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

            UserDTO userDTO = userService.getUserByPhone(phone);
            userDTO.setPassword(null); // password는 보이지 않도록 null로 설정
            model.addAttribute("user", userDTO);
        }

        String studentPhone = "";
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.getPrincipal() instanceof String) {
            studentPhone = (String) auth.getPrincipal();
        }
        log.info("studentPhone :" + studentPhone);


        // paperType의 값은 CBT일 경우 1 or 실전시험 I은 2 or 실전시험 II은 3 or Report는 4
        // JSP 에서 탭 메뉴의 색상을 파란색으로 칠해주기 위해 paperType 값이 필요
        model.addAttribute("paperType", paperType);

        if (paperType != 1) { // CBT 일 경우, 애초에 아래 로직을 할 필요 조차 없음
            // paper list 페이지에서, 탭 당 'CBT', '실전시험 I', '실전시험 II'로 나눠져 있으니,
            // paperType을 활용해서 해당 타입에 해당하는 시험지들의 PK(id)를 전부 갖고 옴
            List<Integer> paperIdListByPaperTypeId = pageService.getPaperIdListByPaperTypeId(paperType);

            // paper list 페이지에서, '응시하기' 인지, '재응시'인지, '이어하기'인지 구분해주기 위해 쿼리
            List<PaperStatusDto> paperStatusList =
                    paperResultService.queryForSpecifyStatusValuesInPaperListPage(studentPhone, paperIdListByPaperTypeId);

            Map<String, String> paperStatusMap = new HashMap<>();


            // Y -> 최종제출 완료, N -> 아직 최종제출 미완료, NEVER STARTED YET -> 아직 한 번도 푼 적 없음
            for (PaperStatusDto dto : paperStatusList) {
                int paperId = dto.getPaperId();
                String paperStatus = dto.getStatus(); // Y  or N or NEVER STARTED YET
                paperStatusMap.put(paperId + "", paperStatus);
            }

            model.addAttribute("paperStatusMap", paperStatusMap);
        }
        return "paper/list/paperTabMenuList";
    }

    @GetMapping(value = "/cbt")
    public String showCbtPage(Model model) {
        Map<String, Object> paperWithQuestionList = pageService.getPaperWithQuestionListByPaperId(7);
        PaperDto paper = (PaperDto) paperWithQuestionList.get("paper");
        List<QuestionDto> questionList = (List<QuestionDto>) paperWithQuestionList.get("questionList");

        model.addAttribute("paper", paper);
        model.addAttribute("questionList", questionList);

        return "test/cbt/cbt";
    }

    @GetMapping(value = "/sound-check")
    public String showSoundCheckPage(@RequestParam("paper_id") Integer paperId, Model model, Authentication authentication) {

        if (authentication != null && authentication.isAuthenticated()) { //
            String phone = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

            UserDTO userDTO = userService.getUserByPhone(phone);
            userDTO.setPassword(null); // password는 보이지 않도록 null로 설정
            model.addAttribute("user", userDTO);
        }

        // 시험지의 종류가 CBT, 실전시험 I, 실전시험 II인지 확인
        Map<String, Object> resultMap = pageService.getPaperTypeByPaperId(paperId);
        // id 값은 CBT일 경우 1, 실전시험 I는 2, 실전시험 II는 3
        model.addAttribute("paperTypeId", (Integer) resultMap.get("id"));
        // name은 CBT, 실전시험 I, 실전시험 II 문자열 그대로
        model.addAttribute("paperTypeName", (String) resultMap.get("name"));
        return "paper/soundCheck/soundCheck";
    }

    @GetMapping(value = "/announcement")
    public String showAnnouncementPage(@RequestParam("paper_id") Integer paperId, Model model, Authentication authentication) {

        if (authentication != null && authentication.isAuthenticated()) { //
            String phone = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

            UserDTO userDTO = userService.getUserByPhone(phone);
            userDTO.setPassword(null); // password는 보이지 않도록 null로 설정
            model.addAttribute("user", userDTO);
        }
        // 시험지의 종류가 CBT, 실전시험 I, 실전시험 II인지 확인
        Map<String, Object> resultMap = pageService.getPaperTypeByPaperId(paperId);
        // id 값은 CBT일 경우 1, 실전시험 I는 2, 실전시험 II는 3
        model.addAttribute("paperTypeId", (Integer) resultMap.get("id"));
        // name은 CBT, 실전시험 I, 실전시험 II 문자열 그대로
        model.addAttribute("paperTypeName", (String) resultMap.get("name"));
        return "paper/announcement/announcement";
    }

    @GetMapping(value = "/paper-view")
    public String showPaperViewPage(@RequestParam("paper_id") Integer paperId, Model model) {
        String studentPhone = "";
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.getPrincipal() instanceof String) {
            studentPhone = (String) auth.getPrincipal();
        }
        log.info("studentPhone :" + studentPhone);

        Map<String, Object> paperWithQuestionList = pageService.getPaperWithQuestionListByPaperId(paperId);
        PaperDto paper = (PaperDto) paperWithQuestionList.get("paper");
        List<QuestionDto> questionList = (List<QuestionDto>) paperWithQuestionList.get("questionList");

        model.addAttribute("paper", paper);
        model.addAttribute("questionList", questionList);
        model.addAttribute("studentPhone", studentPhone);

        Integer paperType = paper.getPaperTypeId();

        if (paperType.equals(1)) {
            // CBT
            return "test/cbt/cbt";
        } else if (paperType.equals(2)) {
            // 실전시험 I
            return "test/siljeon1/siljeon1";
        } else if (paperType.equals(3)) {
            // 실전시험 II
            return "paper/siljeon2/siljeon2";
        } else {
            return null;
        }
    }

    // 이무현


    // 최경락


    // 최재혁


}

