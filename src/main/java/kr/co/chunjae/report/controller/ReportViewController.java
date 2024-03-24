package kr.co.chunjae.report.controller;

import kr.co.chunjae.user.dto.UserDTO;
import kr.co.chunjae.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/report")
public class ReportViewController {

    private final UserService userService;
    @GetMapping
    public String report(Model model, Authentication authentication){
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
        model.addAttribute("studentPhone", studentPhone);
        return "report/report";
    }
}
