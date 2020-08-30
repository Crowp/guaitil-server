package com.guaitilsoft.web.models.gallery;

import com.guaitilsoft.models.Multimedia;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class GalleryView {
    private Long id;

    private String name;

    private String description;

    private Date createdAt;

    private Date updatedAt;

    private List<Multimedia> multimedia;
}
