package kr.co.chunjae.mainpage;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.WebRequest;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@Controller
@Slf4j
@RequiredArgsConstructor
public class CustomErrorController implements ErrorController  {

    private final ErrorAttributes errorAttributes;

    @RequestMapping("/error")
    public String handleError(HttpServletRequest request, Model model, WebRequest webRequest) {
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        if (status != null) {
            int statusCode = Integer.parseInt(status.toString());
            if (statusCode == HttpStatus.NOT_FOUND.value()) {
                return "error/error404";
            } else if (statusCode == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
                return "error/error500";
            }
        }
        return "error/error"; // 기본 에러 페이지
    }

    // "/update" 경로로의 요청이 발생할 때 404 에러 페이지로 연결
    @RequestMapping("/update")
    public String handleUpdateError() {
        return "redirect:error/error404";
    }

    // "/delete" 경로로의 요청이 발생할 때 404 에러 페이지로 연결
    @RequestMapping("/delete")
    public String handleDeleteError() {
        return "redirect:error/error404";
    }

}