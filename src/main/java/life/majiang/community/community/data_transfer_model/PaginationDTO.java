package life.majiang.community.community.data_transfer_model;


import life.majiang.community.community.model.Question;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class PaginationDTO {
    //本项目是弱化前端的，一般分页都是后台给前台数据，然后前端JS进行分页布局，这里直接后端来做了，前端只是样式布局的设置
    //分页还可以使用pagehelper来进行
    private List<QuestionDTO> questions;

    private boolean showPrevious;
    private boolean showFirstPage;
    private boolean showNext;
    private boolean showEndPage;

    private Integer page;
    private Integer totalPage;

    private List<Integer> pages = new ArrayList<>();

    public void setPagination(Integer totalPage, Integer page) {

        this.totalPage = totalPage;
        this.page = page;

        pages.add(page);
        for (int i=1; i<=3; i++){
            if (page-i > 0){
                pages.add(0,page-i);
            }

            if (page+i <= totalPage){
                pages.add(page+i);
            }
        }


        //是否展示上一页标识，因为只有首页才没有上一页
        if (page == 1){
            showPrevious = false;
        }else {
            showPrevious = true;
        }
        //是否展示下一页标识，因为只有首页才没有下一页
        if (page == totalPage){
            showNext = false;
        }else {
            showNext = true;
        }

        //是否展示第一页
        if (pages.contains(1)){
            showFirstPage = false;
        }else {
            showFirstPage = true;
        }

        //是否展示最后一页
        if (pages.contains(totalPage)){
            showEndPage = false;
        }else {
            showEndPage = true;
        }
    }
}
