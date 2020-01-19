package life.majiang.community.community.controller;

import life.majiang.community.community.data_transfer_model.AccessTokenDTO;
import life.majiang.community.community.data_transfer_model.GithubUser;
import life.majiang.community.community.mapper.UserMapper;
import life.majiang.community.community.model.User;
import life.majiang.community.community.provider.GithubProvider;
import life.majiang.community.community.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

@Controller
public class AuthorizeController {

    @Autowired
    private GithubProvider githubProvider;

    @Value("${github.client.id}")
    private String clientId;

    @Value("${github.client.secret}")
    private String clientSecret;

    @Value("${github.redirect.uri}")
    private String redirectUri;

//    @Autowired
//    private UserMapper userMapper;

    @Autowired
    private UserService userService;

    @GetMapping("/callback")
    public String callback(@RequestParam(name="code") String code,
                           @RequestParam(name = "state") String state,
                           HttpServletRequest request,
                           HttpServletResponse response){
        AccessTokenDTO accessTokenDTO1 = new AccessTokenDTO();
        accessTokenDTO1.setClient_id(clientId);
        accessTokenDTO1.setClient_secret(clientSecret);
        accessTokenDTO1.setCode(code);
        accessTokenDTO1.setRedirect_uri(redirectUri);
        accessTokenDTO1.setState(state);
        String accessToken = githubProvider.getAccessToken(accessTokenDTO1);
        GithubUser githubUser = githubProvider.getUser(accessToken);
//        System.out.println(githubUser.getName());
        if(githubUser != null && githubUser.getId() != null){
            User user = new User();
            String token = UUID.randomUUID().toString();
            user.setToken(token);
            user.setName(githubUser.getName());
            user.setAccountId(String.valueOf(githubUser.getId()));//将id强制转换为String类型
            user.setAvatarUrl(githubUser.getAvatar_url());

            userService.createOrUpdate(user);

//            userMapper.insert(user);
            Cookie cookie = new Cookie("token",token);
            response.addCookie(cookie);
            // 登录成功，写cookie和session

            request.getSession().setAttribute("user",githubUser);
            return "redirect:/";
        }else{
            //登录失败，重新登录
            return "redirect:/";
        }
    }


    @GetMapping("/logout")
    public String logout(HttpServletRequest request,
                         HttpServletResponse response)
    {
        request.getSession().removeAttribute("user");
        Cookie cookie = new Cookie("token",null);
        cookie.setMaxAge(0);
        response.addCookie(cookie);
        return "redirect:/";
    }
}
