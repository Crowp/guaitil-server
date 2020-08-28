package com.guaitilsoft.web.models;

import com.guaitilsoft.models.Address;
import com.guaitilsoft.models.Local;
import com.guaitilsoft.models.Multimedia;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class GalleryView {
    private Long id;

    private String name;

    private String description;

    private List<Multimedia> multimedia;
}
