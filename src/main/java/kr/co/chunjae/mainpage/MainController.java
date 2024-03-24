package kr.co.chunjae.mainpage;

import kr.co.chunjae.user.dto.UserDTO;
import kr.co.chunjae.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class MainController {
    private final UserService userService;

    @GetMapping(value = "/main")
    public String main(Model model, Authentication authentication) { // 인증된 사용자의 정보를 보여줌
        // token에 저장되어 있는 user의 phone 값
        if (authentication != null && authentication.isAuthenticated()) { //
            String phone = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

            UserDTO userDTO = userService.getUserByPhone(phone);
            userDTO.setPassword(null); // password는 보이지 않도록 null로 설정
            model.addAttribute("user", userDTO);
        }

        return "mainpage/mainPage"; // 메인화면
    }
}
