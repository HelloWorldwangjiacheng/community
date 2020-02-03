package life.majiang.community.community.controller;

import life.majiang.community.community.data_transfer_model.PaginationDTO;
import life.majiang.community.community.data_transfer_model.QuestionDTO;
import life.majiang.community.community.mapper.QuestionMapper;
import life.majiang.community.community.mapper.UserMapper;
import life.majiang.community.community.model.Question;
import life.majiang.community.community.model.User;
import life.majiang.community.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class IndexController {

//    @Autowired
//    private UserMapper userMapper;

    @Autowired
    private QuestionService questionService;

    @GetMapping("/")
    public String index(
                        Model model,
                        @RequestParam(name = "page",defaultValue = "1") Integer page,
                        @RequestParam(name = "size",defaultValue = "2") Integer size,
                        @RequestParam(name = "search",required = false) String search
                        )
    {
        //重构之后将对cookie的校验放到了拦截器interceptor.SessionInterceptor中去，这里将不用校验了
        //  index方法中的 HttpServletRequest request,参数就不用了
        PaginationDTO pagination = questionService.list(search,page,size);
        model.addAttribute("pagination", pagination);
        model.addAttribute("search",search);
        return "index";
    }
}
