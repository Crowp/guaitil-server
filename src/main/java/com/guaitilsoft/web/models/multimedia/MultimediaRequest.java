package com.guaitilsoft.web.models.multimedia;

import com.guaitilsoft.models.constant.MultimediaType;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

@Data
public class MultimediaRequest {
    private MultipartFile file;

    private String prefix;

    private String suffix;

    private MultimediaType type;

    public String getFileName(){
        return file.getOriginalFilename();
    }

    public InputStream getInputStream() throws IOException {
        return file.getInputStream();
    }

    public String getContentType(){
        return file.getContentType();
    }
}
