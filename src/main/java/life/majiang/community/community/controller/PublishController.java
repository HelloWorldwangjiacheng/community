package life.majiang.community.community.controller;

import life.majiang.community.community.mapper.QuestionMapper;
import life.majiang.community.community.mapper.UserMapper;
import life.majiang.community.community.model.Question;
import life.majiang.community.community.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@Controller
public class PublishController {

    @Autowired
    private QuestionMapper questionMapper;

//    @Autowired
//    private UserMapper userMapper;

    @GetMapping("/publish")
    public String Publish(){
        return "publish";
    }

    @PostMapping("/publish")
    public String doPublish(
            @RequestParam("title") String title,
            @RequestParam("description") String description,
            @RequestParam("tag") String tag,
            HttpServletRequest request,
            Model model){

        model.addAttribute("title",title);
        model.addAttribute("description",description);
        model.addAttribute("tag",tag);

        //因为本项目是专注于后端的，所以把这些校验放到后端，其实正常的项目开发中应该是放到前端js来校验的，再保险一点后端也要验证因为某些手段可以绕过前端验证
        if (title == null || title == "" ){
            model.addAttribute("error","标题不能为空");
            return "publish";
        }

        if (description == null || description == ""){
            model.addAttribute("error","问题补充不能为空");
            return "publish";
        }

        if (tag == null || tag == ""){
            model.addAttribute("error","标签不能为空");
            return "publish";
        }

//        User user = null;
//        Cookie[] cookies = request.getCookies();
//        if (cookies != null && cookies.length != 0){
//            for (Cookie cookie : cookies){
//                if (cookie.getName().equals("token")){
//                    String token = cookie.getValue();
//                    user = userMapper.findByToken(token);
//                    if (user != null){
//                        request.getSession().setAttribute("user",user);
//                    }
//                    break;
//                }
//            }
//        }

        User user = (User) request.getSession().getAttribute("user");

        if (user == null){
            model.addAttribute("error","用户未登录");
            return "publish";
        }

        Question question = new Question();
        question.setTitle(title);
        question.setDescription(description);
        question.setTag(tag);
        question.setCreator(user.getId());
        question.setGmtCreate(System.currentTimeMillis());
        question.setGmtModified(question.getGmtCreate());


        questionMapper.create(question);
        return "redirect:/";
    }
}
