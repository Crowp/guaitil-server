package com.guaitilsoft.web.models.local;

import com.guaitilsoft.models.Address;
import com.guaitilsoft.models.constant.LocalType;
import com.guaitilsoft.web.models.multimedia.MultimediaResponse;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class LoadLocal {

    private Long id;

    private String name;

    private String description;

    private String telephone;

    private LocalType localType;

    private Address address;

    private List<MultimediaResponse> multimedia;
}
