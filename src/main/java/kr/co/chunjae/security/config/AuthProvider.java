package kr.co.chunjae.security.config;

import kr.co.chunjae.user.dto.UserDTO;
import kr.co.chunjae.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class AuthProvider implements AuthenticationProvider {

    private final UserService userService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String phone = (String) authentication.getPrincipal(); // 로그인 창에 입력한 phone
        String password = (String) authentication.getCredentials(); // 로그인 창에 입력한 password

        PasswordEncoder passwordEncoder = userService.passwordEncoder(); // 비밀번호 인코더 가져옴
        UsernamePasswordAuthenticationToken token;
        UserDTO userDTO = userService.getUserByPhone(phone); // 전화번호로 사용자 정보 조회

        if(userDTO != null && passwordEncoder.matches(password, userDTO.getPassword())){// 일치하는 user 정보가 있는지 확인
            List<GrantedAuthority> roles = new ArrayList<>();
            roles.add(new SimpleGrantedAuthority("User")); // User권한 부여

            // 인증된 user 정보를 담아 SecurityContextholer에 저장되는 Token 생성
            token = new UsernamePasswordAuthenticationToken(userDTO.getPhone(), null, roles);

            return token; //토큰 반환
        }
        // Exception을 던지지 않고 다른 값을 반환하면 authenticate() 메서드는 정상적으로 실행
        // 인증되지 않았다면 Exception을 throw
        throw new BadCredentialsException("아이디 또는 비밀번호가 맞지않습니다.");
    }

    @Override
    public boolean supports(Class<?> authentication) {
        // 모든 종류의 인증을 지원하도록 true 반환
        return true;
    }


}
