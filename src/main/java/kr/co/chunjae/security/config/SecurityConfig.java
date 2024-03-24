package kr.co.chunjae.security.config;

import kr.co.chunjae.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final UserService userService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        // 권한에 따라 허용되는 url 설정
        http
                .authorizeRequests()
                .antMatchers("/", "/main", "/login", "/signup", "/check-phone", "/error/**").permitAll() // /login, /signup 페이지는 permitAll
                .antMatchers("/common/**").permitAll()
                .antMatchers("/paper/**").permitAll()
               // .antMatchers("/paper/report/**").authenticated()
                .anyRequest().authenticated()  // 다른 페이지는 인증된 사용자만 허용

                .and()
                .addFilterBefore(new CustomPaperAccessFilter(), UsernamePasswordAuthenticationFilter.class);// 커스텀 필터 추가
        // 로그인 설정
        http
                .formLogin()
                .loginPage("/login") // Get 요청 -> loginForm
                .successHandler(customAuthenticationSuccessHandler())
                .loginProcessingUrl("/auth") // Post 요청 -> loginForm에 입력한 데이터를 처리
                .usernameParameter("phone")// 로그인에 필요한 id값을 phone으로 설정(default 값은 username)
                .passwordParameter("password") //로그인에 필요한 password 값을 password(default)로 설정
                .failureUrl("/login?error") // 로그인 실패 시 리다이렉트 될 URL 지정
                .defaultSuccessUrl("/"); // 로그인 성공시 /main 으로 redirect
        // 로그아웃 설정
        http
                .logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/") // 로그아웃에 성공하면 /main으로 redirect
                .invalidateHttpSession(true) // 로그아웃시 세션 무효화
                .deleteCookies("JSESSIONID"); // 로그아웃시 쿠키 삭제

        http
                .exceptionHandling()
                .accessDeniedHandler(new AccessDeniedHandler() {
                    @Override
                    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
                        // 로그인 페이지로 리다이렉트하기 전에 현재 요청 URL 저장
                        String baseRedirectUrl = "/login?redirect=";
                        String requestUri = request.getRequestURI();
                        String queryString = request.getQueryString();
                        String redirectUrl = baseRedirectUrl + requestUri + (queryString != null ? "?" + queryString : "");
                        response.sendRedirect(redirectUrl);
                    }
                })
                .and()
                .addFilterBefore(new CustomPaperAccessFilter(), UsernamePasswordAuthenticationFilter.class);
        http
                .csrf().disable()
                .cors().disable();


        return http.build();

    }

    @Bean
    public PasswordEncoder passwordEncoder() {

        //비밀번호 암호화
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationSuccessHandler customAuthenticationSuccessHandler() {
        return new AuthenticationSuccessHandler() {
            @Override
            public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
                RequestCache requestCache = new HttpSessionRequestCache();
                SavedRequest savedRequest = requestCache.getRequest(request, response);
                if (savedRequest == null) {
                    response.sendRedirect(request.getContextPath() + "/");
                } else {
                    String targetUrl = savedRequest.getRedirectUrl();
                    response.sendRedirect(targetUrl);
                }
            }
        };
    }
}
