package life.majiang.community.community.data_transfer_model;

import lombok.Data;
import org.springframework.stereotype.Component;

@Data
public class GithubUser {
    private String name;
    private Long id;
    private String bio;
    private String avatar_url;
}
