package life.majiang.community.community.cache;


import life.majiang.community.community.data_transfer_model.TagDTO;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TagCache {
    public static List<TagDTO> get(){
        ArrayList<TagDTO> tagDTOS = new ArrayList<>();
        TagDTO program = new TagDTO();
        program.setCategoryName("开发语言");
        program.setTags(Arrays.asList("Java","PHP","Javascript","C/C++","Python","HTML","CSS","Go","C#","Ruby","VB"));
        tagDTOS.add(program);

        TagDTO framework = new TagDTO();
        framework.setCategoryName("平台框架");
        framework.setTags(Arrays.asList("Spring","Spring Boot","Spring Cloud","Vue","Angular","Node","Django","Tornado","Android","ios","React"));
        tagDTOS.add(framework);

        TagDTO server = new TagDTO();
        server.setCategoryName("服务器");
        server.setTags(Arrays.asList("linux","nginx","docker","apache","ubuntu","centOs","tomcat","unix","windows-server","hadoop","缓存"));
        tagDTOS.add(server);

        TagDTO dataBase = new TagDTO();
        dataBase.setCategoryName("数据库");
        dataBase.setTags(Arrays.asList("MySQL","SQL Server","Oracle","NoSQL","MongoDB","postgreSQL","SQLite","Redis"));
        tagDTOS.add(dataBase);

        TagDTO tool = new TagDTO();
        tool.setCategoryName("开发工具");
        tool.setTags(Arrays.asList("Eclipse","IDEA","WebStorm","VSCode","Visual Stdio","Android Stdio","DEV-C++","Git","Sublime-Text"));
        tagDTOS.add(tool);
        return tagDTOS;
    }

    public static String filterInvaild(String tags){
        String[] split = StringUtils.split(tags, ",");
        List<TagDTO> tagDTOS = get();
//        二维数组两层的数据循环出来
        List<String> tagList = tagDTOS.stream().flatMap(tag -> tag.getTags().stream()).collect(Collectors.toList());
        String invaild = Arrays.stream(split).filter(t -> !tagList.contains(t)).collect(Collectors.joining(","));
        return invaild;
    }

}
