package kr.co.chunjae.user.controller;

import kr.co.chunjae.user.dto.UserDTO;
import kr.co.chunjae.user.dto.UserRequestDTO;
import kr.co.chunjae.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;

@Slf4j
@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;


    /**
        메인페이지
        로그인된 사용자(인증된 사용자) 정보를 보여줌
     */
    @GetMapping(value = "/")
    public String main(Model model, Authentication authentication) { // 인증된 사용자의 정보를 보여줌
        // token에 저장되어 있는 user의 phone 값
        // 인증 객체가 null이 아니고, 인증된 상태라면 실행
        if (authentication != null && authentication.isAuthenticated()) {
            // 인증 정보에서 phone을 가져옴
            String phone = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

            UserDTO userDTO = userService.getUserByPhone(phone);
            userDTO.setPassword(null); // password는 보이지 않도록 null로 설정
            model.addAttribute("user", userDTO); // model에 user 정보 추가
        }

        return "mainpage/mainPage"; // 메인화면
    }

    /**
        로그인 페이지

     */
    @GetMapping(value = "/login")
    public String loginPage(@RequestParam(value = "redirect", required = false) String redirect) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            // 인증 객체가 AnonymousAuthenticationToken의 인스턴스라면 로그인되지 않은 상태
            if (authentication instanceof AnonymousAuthenticationToken) {
                // 로그인 되지 않은 상태라면 로그인 페이지로
                return "user/loginPage";
            } else {
                // 로그인 된 상태면, redirect 파라미터가 있는지 확인
                if (redirect != null && !redirect.isEmpty()) {
                    // 유효한 redirect 주소가 제공된 경우, 해당 주소로 redirect
                    return "redirect:" + redirect;
                } else {
                    // redirect 파라미터가 없는 경우, 메인 페이지로 redirect
                    return "redirect:/";
                }
            }
        } catch (Exception ex) {
            throw new RuntimeException("로그인 페이지를 로드하는 도중 오류가 발생했습니다.");
        }
    }

    /**
        회원가입 페이지

     */
    @GetMapping(value = "/signup")
    public String signUpPage(@ModelAttribute UserRequestDTO userRequestDTO) {
        try {
            log.info("userRequestDTO: " + userRequestDTO);

            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            // 인증 객체가 AnonymousAuthenticationToken의 인스턴스라면 회원가입 하지 않은 상태
            if (authentication instanceof AnonymousAuthenticationToken)
                return "user/signupPage"; // 회원가입 하지 않은 상태라면 회원가입 페이지로
            return "redirect:/"; // 회원가입이 된 상태면 mainPage로
        } catch (Exception ex) {
            throw new RuntimeException("회원가입 페이지를 로드하는 도중 오류가 발생했습니다.");
        }
    }

    /**
        회원가입 처리
     */
    @PostMapping(value = "/signup")
    public String signUp(@Valid @ModelAttribute UserRequestDTO userRequestDTO, BindingResult bindingResult) {
        try {
            log.info("userRequestDTO: " + userRequestDTO);
            // 유효성 검사에서 걸리면 다시 회원가입 페이지로 이동
            if (bindingResult.hasErrors()) {
                log.info("Validation errors: {}", bindingResult.getAllErrors());
                bindingResult.addError(new ObjectError("userRequestDTO", "회원가입 양식에 맞게 모든 값을 입력해주세요."));
                return "/user/signupPage";
            }
            userService.signUp(userRequestDTO);
            // 회원가입에 성공하면 로그인 페이지로 redirect
            return "redirect:/login";
        } catch (Exception ex) {
            throw new RuntimeException("회원가입 처리 도중 오류가 발생했습니다.");
        }
    }

    /**
        전화번호 중복검사
     */
    @GetMapping("/check-phone")
    public ResponseEntity<?> checkPhone(@RequestParam("phone") String phone) {
        // 전화번호 존재 여부 확인
        boolean exists = userService.existsByPhone(phone);
        if (exists) {
            // 사용 중이라면 USER 응답 반환
            return ResponseEntity.ok("USED");
        } else {
            // 사용 가능하면 OK 응답 반환
            return ResponseEntity.ok("OK");
        }
    }

//    @GetMapping(value = "/update")
//    public String editPage(Model model) { // 회원정보 수정 페이지
//        String phone = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        UserDTO userDTO = userService.getUserByPhone(phone);
//        model.addAttribute("user", userDTO);
//        return "user/editPage";
//    }
//
//    @PostMapping(value = "/update")
//    public String edit(UserDTO userDTO) { // 회원정보 수정
//        String phone = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        userDTO.setPhone(phone);
//        userService.edit(userDTO);
//
//        return "redirect:/";
//
//    }
//
//    @PostMapping(value = "/delete") // 회원탈퇴
//    public String withdraw(HttpServletRequest request, HttpSession session, HttpServletResponse response) {
//        String phone = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        if (phone != null) {
//            userService.deleteUser(phone);
//        }
//
//        // 현재 사용자의 세션을 무효화
//        session.invalidate();
//
//        // 쿠키를 삭제하여 세션을 완전히 종료
//        Cookie[] cookies = request.getCookies();
//        if (cookies != null) {
//            for (Cookie cookie : cookies) {
//                cookie.setMaxAge(0);
//                response.addCookie(cookie);
//            }
//        }
//
//        // SecurityContextHolder에 남아있는 token 삭제
//        SecurityContextHolder.clearContext();
//
//        return "redirect:/";
//    }


}



