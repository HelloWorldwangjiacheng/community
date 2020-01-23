package life.majiang.community.community.advice;

import com.alibaba.fastjson.JSON;
import life.majiang.community.community.data_transfer_model.ResultDTO;
import life.majiang.community.community.exception.CustomizeErrorCode;
import life.majiang.community.community.exception.CustomizeException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@ControllerAdvice
public class CustomizeExceptionHandler {
    @ExceptionHandler(Exception.class)
//    规范化统一处理错误异常
//    ModelAndView因为我们不希望它返回json所以把responseBody去掉
//    现在我们想要返回一个json时用responseBody
//    但是@ResponseBody会报错，所以我们手写一个
     ModelAndView handle(HttpServletRequest request, Throwable ex, Model model, HttpServletResponse response){

        String contentType = request.getContentType();

        if ("application/json".equals(contentType)){
            //返回JSON
            ResultDTO resultDTO = null;
            if (ex instanceof CustomizeException){

                resultDTO = ResultDTO.errorOf((CustomizeException) ex);
            }else {
                resultDTO = ResultDTO.errorOf(CustomizeErrorCode.SYS_ERROR);
            }
            try {
                response.setCharacterEncoding("UTF-8");
                response.setContentType("application/json");
                PrintWriter printWriter = response.getWriter();
                printWriter.write(JSON.toJSONString(resultDTO));
                printWriter.close();
            } catch (IOException ioEx) {
                ioEx.printStackTrace();
            }
            return null;
        }else{
            //错误页面跳转
            if (ex instanceof CustomizeException){
                //如果我们知道这个异常时怎么回事，就直接抛出我们的自定义的异常
                model.addAttribute("message",ex.getMessage());
            }else{
                //如果不知道那么就是说这种模糊不清的话
                model.addAttribute("message",CustomizeErrorCode.SYS_ERROR.getMessage());
            }
            return new ModelAndView("error");
        }


    }

}
