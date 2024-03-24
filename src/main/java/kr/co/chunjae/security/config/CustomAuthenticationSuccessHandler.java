package kr.co.chunjae.security.config;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class CustomAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

        private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

        @Override
        protected void handle(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
            // 인증 성공 후 redirect 타겟 URL 결정
            String targetUrl = determineTargetUrl(request, response, authentication);

            if (response.isCommitted()) {
                logger.debug("Response has already been committed. Unable to redirect to " + targetUrl);
                return;
            }

            redirectStrategy.sendRedirect(request, response, targetUrl);
        }

    @Override
    protected String determineTargetUrl(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        // 로그인 폼에서 전달받은 redirect 파라미터 값을 가져옴
        String redirectUrl = request.getParameter("redirect");

        // redirectUrl이 유효한지 검증합
        if (redirectUrl != null && !redirectUrl.isEmpty()) {
            // URL이 유효한 경우, 그 URL로 리다이렉트
            return redirectUrl;
        } else {
            // redirect 파라미터가 없는 경우, 이전 요청 URL을 사용
            String prevRequestedUrl = (String) request.getSession().getAttribute("prevRequestedUrl");
            if (prevRequestedUrl != null && !prevRequestedUrl.isEmpty()) {
                return prevRequestedUrl;
            } else {
                // 이전 요청 URL도 없는 경우, 기본 로그인 성공 URL로 리다이렉트
                return super.determineTargetUrl(request, response, authentication);
            }
        }

    }


}
