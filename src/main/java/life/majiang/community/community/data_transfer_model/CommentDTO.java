package life.majiang.community.community.data_transfer_model;

import lombok.Data;

@Data
public class CommentDTO {
    private Long parentId;
    private String content;
    private Integer type;

}
