package com.guaitilsoft.web.models.multimedia;

import com.guaitilsoft.models.constant.MultimediaType;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MultimediaResponse {

    private Long id;

    private String fileName;

    private MultimediaType type;

    private String format;

    private String url;
}
