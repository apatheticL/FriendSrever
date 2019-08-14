package com.hunglephuong.fiendlyserver;

import com.hunglephuong.fiendlyserver.model.response.BaseResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;

@RestController
public class FireBaseController {
    @Autowired
    private FirebaseManager firebaseManager;
    @PostMapping(value = "/postImage")
    public Object postImage(
            @RequestParam(value = "image")MultipartFile file
            ) throws IOException {
        String path= firebaseManager.uploadFile(file);
        return BaseResponse.createResponse(path);
    }

    @GetMapping(value = "/getImage", produces = MediaType.IMAGE_JPEG_VALUE)
    public byte[] getImage(
            @RequestParam(value = "fileName") String fileName
    ){
        return firebaseManager.getImage(fileName);
    }
}
