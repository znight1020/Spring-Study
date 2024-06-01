package hello.exception.servlet;

import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.IOException;

@Slf4j
@Controller
public class ServletExController {
    @GetMapping("/error-ex")
    public void errorEx(){
        throw new RuntimeException("예외 발생");
    }

    @GetMapping("/error-404")
    public void error404(HttpServletResponse response) throws IOException {
        log.info("ServletController 호출");
        response.sendError(404, "404 오류!");
    }


    // 500에러는 정의되어 있으니 500.html 를 찾아간다.
    @GetMapping("/error-500")
    public void error500(HttpServletResponse response) throws IOException {
        response.sendError(500, "500 오류!");
    }

    // 415에러는 정의되어 있지않아 4xx.html 를 찾아간다.
    @GetMapping("/error-4xx")
    public void error4xx(HttpServletResponse response) throws IOException {
        response.sendError(415, "415 오류!");
    }

    // 501에러는 정의되어 있지않아 5xx.html 를 찾아간다.
    @GetMapping("/error-5xx")
    public void error5xx(HttpServletResponse response) throws IOException {
        response.sendError(501, "501 오류!");
    }

}
