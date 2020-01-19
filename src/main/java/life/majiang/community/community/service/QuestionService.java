package life.majiang.community.community.service;

import life.majiang.community.community.data_transfer_model.PaginationDTO;
import life.majiang.community.community.data_transfer_model.QuestionDTO;
import life.majiang.community.community.mapper.QuestionMapper;
import life.majiang.community.community.mapper.UserMapper;
import life.majiang.community.community.model.Question;
import life.majiang.community.community.model.User;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionService {
    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private UserMapper userMapper;

    //上面这个方法是给所有问题分页展示用的
    public PaginationDTO list(Integer page, Integer size) {
        Integer totalPage;
        PaginationDTO paginationDTO = new PaginationDTO();
        //totalCount是数据库question表中的记录的数量
        Integer totalCount = questionMapper.count();

        if (totalCount % size == 0){
            totalPage = totalCount/size;
        } else {
            totalPage = totalCount/size +1;
        }

        if (page<1){ page=1; }
        if (page>totalPage){ page = totalPage; }
        paginationDTO.setPagination(totalPage,page);
//        size*(i-1)
        Integer offset = size*(page-1);

        List<Question> questions = questionMapper.list(offset,size);
        List<QuestionDTO> questionDTOList = new ArrayList<>();

        for (Question question : questions){
            User user = userMapper.findById(question.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question,questionDTO);//将question上的所有属性复制到questionDTO上去
            questionDTO.setUser(user);
            questionDTOList.add(questionDTO);
        }
        paginationDTO.setQuestions(questionDTOList);
        return paginationDTO;
    }
    //下面这个方法是给我的问题列表分页用的
    public PaginationDTO list(Integer userId, Integer page, Integer size) {
        Integer totalPage;
        PaginationDTO paginationDTO = new PaginationDTO();
        //totalCount是数据库question表中的记录的数量
        Integer totalCount = questionMapper.countByUserId(userId);

        if (totalCount%size == 0){
            totalPage = totalCount/size;
        } else {
            totalPage = totalCount/size +1;
        }

        if (page<1){ page=1; }
        if (page>totalPage){ page = totalPage; }
        paginationDTO.setPagination(totalPage,page);
//        size*(i-1)
        Integer offset = size*(page-1);
        if (offset<1) offset=1;

        List<Question> questions = questionMapper.listByUserId(userId,offset,size);
        List<QuestionDTO> questionDTOList = new ArrayList<>();

        for (Question question : questions){
            User user = userMapper.findById(question.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question,questionDTO);//将question上的所有属性复制到questionDTO上去
            questionDTO.setUser(user);
            questionDTOList.add(questionDTO);
        }
        paginationDTO.setQuestions(questionDTOList);
        return paginationDTO;
    }

    public QuestionDTO getById(Integer id) {
        Question question = questionMapper.getById(id);
        QuestionDTO questionDTO = new QuestionDTO();
        BeanUtils.copyProperties(question,questionDTO);

        User user = userMapper.findById(question.getCreator());
        questionDTO.setUser(user);

        return questionDTO;
    }
}
