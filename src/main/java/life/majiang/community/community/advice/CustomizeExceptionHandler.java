package life.majiang.community.community.advice;

import life.majiang.community.community.exception.CustomizeException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class CustomizeExceptionHandler {
    @ExceptionHandler(Exception.class)
//    因为我们不希望它返回json所以把responseBody去掉
//    @ResponseBody
    ModelAndView handle(HttpServletRequest request, Throwable ex, Model model){
        HttpStatus status = getStatus(request);

        if (ex instanceof CustomizeException){
            //如果我们知道这个异常时怎么回事，就直接抛出我们的自定义的异常
            model.addAttribute("message",ex.getMessage());
        }else{
            //如果不知道那么就是说这种模糊不清的话
            model.addAttribute("message","服务冒烟了，你要不稍后再试试");
        }
        return new ModelAndView("error");
    }

    private HttpStatus getStatus(HttpServletRequest request) {
        Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
        if (statusCode == null){
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return HttpStatus.valueOf(statusCode);
    }
}
