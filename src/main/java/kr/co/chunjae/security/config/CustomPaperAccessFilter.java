package kr.co.chunjae.security.config;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Component
public class CustomPaperAccessFilter extends GenericFilterBean {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request; // 요청을 HttpServletRequest로 반환
        String requestURI = httpRequest.getRequestURI(); // 요청 URL 가져옴

        // 만약 요청 주소가 "/paper/paper-view"인 경우
        if (requestURI.equals("/paper/paper-view") || requestURI.equals("/paper/sound-check") || requestURI.equals("/paper/announcement") || requestURI.equals("/paper/report/real-answer") || requestURI.equals("/paper/report/student-choice") )  {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication == null || !authentication.isAuthenticated() || "anonymousUser".equals(authentication.getPrincipal())) {
                // 로그인되지 않은 경우, 로그인 페이지로 리다이렉트하고 현재 URL을 파라미터로 전달
                String loginUrl = "/login?redirect=" + httpRequest.getRequestURI() +
                        (httpRequest.getQueryString() != null ? "?" + httpRequest.getQueryString() : "");
                HttpServletResponse httpResponse = (HttpServletResponse) response;
                httpResponse.sendRedirect(loginUrl);
                return;
            }
        }
        // 만약 요청 주소가 "/paper/report/real-answer"인 경우
//        if (requestURI.equals("/paper/report/real-answer")) {
//            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//            if (authentication == null || !authentication.isAuthenticated() || "anonymousUser".equals(authentication.getPrincipal())) {
//                // 로그인되지 않은 경우, 로그인 페이지로 리다이렉트하고 현재 URL을 파라미터로 전달
//                String loginUrl = "/login?redirect=" + httpRequest.getRequestURI() +
//                        (httpRequest.getQueryString() != null ? "?" + httpRequest.getQueryString() : "");
//                HttpServletResponse httpResponse = (HttpServletResponse) response;
//                httpResponse.sendRedirect(loginUrl);
//                return;
//            }
//        }

        String paperType = httpRequest.getParameter("paper_type"); // 파라미터 값을 가져옴
        List<String> restrictedTypes = Arrays.asList("2", "3", "4"); // 제한되는 파라미터 값의 목록

        // `paper_type`이 제한된 값 중 하나일 경우, 로그인 여부 확인
        if (restrictedTypes.contains(paperType)) {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            // 인증된 경우 계속 진행(로그인 상태)
            if (authentication != null && authentication.isAuthenticated() && !"anonymousUser".equals(authentication.getPrincipal())) {
                chain.doFilter(request, response);
            }
            else {
                // 로그인 페이지로 리다이렉트, 원래 요청 URL을 파라미터로 포함
                HttpServletResponse httpResponse = (HttpServletResponse) response;
                String loginUrl = "/login?redirect=" + httpRequest.getRequestURI() +
                        (httpRequest.getQueryString() != null ? "?" + httpRequest.getQueryString() : "");
                httpResponse.sendRedirect(loginUrl);
            }
        } else {
            // `paper_type`이 1이거나, 유효하지 않은 값이거나, 파라미터가 없는 경우, 필터 체인을 계속 진행
            chain.doFilter(request, response);
        }
    }
}

