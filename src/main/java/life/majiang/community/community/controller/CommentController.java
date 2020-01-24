package life.majiang.community.community.controller;

import life.majiang.community.community.data_transfer_model.CommentCreateDTO;
import life.majiang.community.community.data_transfer_model.CommentDTO;
import life.majiang.community.community.data_transfer_model.ResultDTO;
import life.majiang.community.community.enums.CommentTypeEnum;
import life.majiang.community.community.exception.CustomizeErrorCode;
import life.majiang.community.community.model.Comment;
import life.majiang.community.community.model.User;
import life.majiang.community.community.service.CommentService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class CommentController {

//    @Autowired
//    private CommentMapper commentMapper;

    @Autowired
    private CommentService commentService;

    //用requestBody接收json格式的数据
    @ResponseBody
    @RequestMapping(value = "/comment", method = RequestMethod.POST)
    public Object post(@RequestBody CommentCreateDTO commentCreateDTO,
                       HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            //向内封装，封装到CustomizeErrorCode这个枚举类类中
            return ResultDTO.errorOf(CustomizeErrorCode.NO_LOGIN);
        }
//        commentCreateDTO == null || commentCreateDTO.getContent() == null || commentCreateDTO.getContent() == ""
        //在org.apache.commons：commons-lang3下面的StringUntils方法类的使用效果等同于commentCreateDTO.getContent() == null || commentCreateDTO.getContent() == ""
        if (commentCreateDTO == null || StringUtils.isBlank(commentCreateDTO.getContent())) {
            return ResultDTO.errorOf(CustomizeErrorCode.COMMENT_IS_EMPTY);
        }
        Comment comment = new Comment();
        comment.setParentId(commentCreateDTO.getParentId());
        comment.setContent(commentCreateDTO.getContent());
        comment.setType(commentCreateDTO.getType());
        comment.setGmtModified(System.currentTimeMillis());
        comment.setGmtCreate(System.currentTimeMillis());
        comment.setCommentator(user.getId());
        comment.setLikeCount(0);
//        commentMapper.insert(comment);
        commentService.insert(comment);

        Map<Object, Object> objectObjectHashMap = new HashMap<>();
        objectObjectHashMap.put("message", "成功");
        return ResultDTO.okOf();
    }

    @ResponseBody
    @RequestMapping(value = "/comment/{id}", method = RequestMethod.GET)
    public ResultDTO<List<CommentDTO>> comments(@PathVariable(name = "id") Long id) {

        List<CommentDTO> commentDTOS = commentService.listByTargetId(id, CommentTypeEnum.COMMENT);

        return ResultDTO.okOf(commentDTOS);
    }

}
