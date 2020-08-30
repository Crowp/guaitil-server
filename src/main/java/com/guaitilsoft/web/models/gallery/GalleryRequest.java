package com.guaitilsoft.web.models.gallery;

import com.guaitilsoft.models.Multimedia;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class GalleryRequest {
    private List<Multimedia> multimedia;
}
