package com.guaitilsoft.web.models.gallery;

import com.guaitilsoft.web.models.multimedia.MultimediaResponse;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class GalleryResponse {
    private Long id;
    private List<MultimediaResponse> multimedia;
}
