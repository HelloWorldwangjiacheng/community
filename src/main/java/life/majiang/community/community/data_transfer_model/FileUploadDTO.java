package life.majiang.community.community.data_transfer_model;

import lombok.Data;

@Data
public class FileUploadDTO {
    private int success;
    private String message;
    private String url;
}
