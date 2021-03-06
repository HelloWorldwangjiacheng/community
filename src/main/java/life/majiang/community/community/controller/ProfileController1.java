package life.majiang.community.community.controller;

import life.majiang.community.community.data_transfer_model.PaginationDTO;
import life.majiang.community.community.mapper.UserMapper;
import life.majiang.community.community.model.Notification;
import life.majiang.community.community.model.User;
import life.majiang.community.community.service.NotificationService;
import life.majiang.community.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@Controller
public class ProfileController1 {
    //为什么起名ProfileController1 因为ProfileController和某配置文件重名了

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private QuestionService questionService;

    @GetMapping("/profile/{action}")
    public String profile(@PathVariable(name = "action") String action,
                          Model model,
                          HttpServletRequest request,
                          @RequestParam(name = "page",defaultValue = "1") Integer page,
                          @RequestParam(name = "size",defaultValue = "5") Integer size
                          )
    {
//        User user = null;
//        Cookie[] cookies = request.getCookies();
//        if (cookies != null && cookies.length != 0){
//            for(Cookie cookie : cookies ) {
//                if (cookie.getName().equals("token")){
//                    String token = cookie.getValue();
//                    user = userMapper.findByToken(token);
//                    if(user != null){
//                        request.getSession().setAttribute("user",user);
//                    }
//                    break;
//                }
//            }
//        }
        User user = (User) request.getSession().getAttribute("user");
        if (user == null){
            return "redirect:/";
        }
        if ("questions".equals(action)){
            model.addAttribute("section","questions");
            model.addAttribute("sectionName","我的提问");
            PaginationDTO paginationDTO = questionService.list(user.getId(), page, size);
            model.addAttribute("pagination",paginationDTO);
        }else if ("replies".equals(action)){
            PaginationDTO paginationDTO = notificationService.list(user.getId(), page, size);
//            Long unreadCount = notificationService.unreadCount(user.getId());
            model.addAttribute("pagination",paginationDTO);
            model.addAttribute("section","replies");
//            model.addAttribute("unreadCount",unreadCount);
            model.addAttribute("sectionName","最新回复");
        }


        return "profile";
    }

}
