package life.majiang.community.community.controller;


import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collections;
import java.util.Map;


//@Controller("/error")
@Controller
@RequestMapping("${server.error.path:${error.path:/error}}")
public class CustomizeErrorController implements ErrorController {

    @Override
    public String getErrorPath() {
        return "error";
    }

    //errorHtml这个方法是从BasicErrorController.java这个spring系统封装类中调出来的，在第85行
    //重写这个方法
    @RequestMapping(produces = MediaType.TEXT_HTML_VALUE)
    public ModelAndView errorHtml(HttpServletRequest request,
//                                  HttpServletResponse response,
                                  Model model
                                  ) {
        HttpStatus status = getStatus(request);

        if (status.is4xxClientError()){
            model.addAttribute("message","你的这个请求有问题，要不换个姿势？");
        }

        if (status.is5xxServerError()){
            model.addAttribute("message","服务器已经冒烟了，攻城狮正在修复中......");
        }

//        Map<String, Object> model = Collections
//                .unmodifiableMap(getErrorAttributes(request, isIncludeStackTrace(request, MediaType.TEXT_HTML)));
//        response.setStatus(status.value());
//        ModelAndView modelAndView = resolveErrorView(request, response, status, model);
//        return (modelAndView != null) ? modelAndView : new ModelAndView("error", model);
        return new ModelAndView("error");
    }

    //getStatus这个方法是从AbstractErrorController这个封装类中调出来的，在第82行
    //为上面已经重写的方法提供依赖方法
    private HttpStatus getStatus(HttpServletRequest request) {
        Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
        if (statusCode == null) {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
        try {
            return HttpStatus.valueOf(statusCode);
        }
        catch (Exception ex) {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
    }
}
