package life.majiang.community.community.interceptor;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.theme.ThemeChangeInterceptor;

@Configuration
//@EnableWebMvc
public class WebConfig implements WebMvcConfigurer {
//    如果网页发现css样式没有了，去掉@EnableWebMvc这个注解，因为它拦截下来了
    //也可以重写一个addResourceHandlers（ResourceHandlerRegistry registry）这个方法
    @Autowired
    private SessionInterceptor sessionInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry){
        //这里不加.excludePathPatterns()因为我想要所有的页面都加上拦截器
//        registry.addInterceptor(new SessionInterceptor()).addPathPatterns("/**");
        registry.addInterceptor(sessionInterceptor).addPathPatterns("/**");
    }


}
