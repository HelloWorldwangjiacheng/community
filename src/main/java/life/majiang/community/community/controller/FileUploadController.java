package life.majiang.community.community.controller;

import life.majiang.community.community.data_transfer_model.FileUploadDTO;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class FileUploadController {
    @RequestMapping("/file/upload")
    @ResponseBody
    public FileUploadDTO upload(){
        FileUploadDTO fileUploadDTO = new FileUploadDTO();
        fileUploadDTO.setSuccess(1);
        fileUploadDTO.setUrl("/images/gala.jpg");
//        fileUploadDTO.setMessage();
        return fileUploadDTO;
    }
}
