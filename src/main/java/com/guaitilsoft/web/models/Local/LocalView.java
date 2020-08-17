package com.guaitilsoft.web.models.Local;

import com.guaitilsoft.models.Address;
import com.guaitilsoft.models.Member;
import com.guaitilsoft.models.Multimedia;
import com.guaitilsoft.models.constant.LocalType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class LocalView {

    private Long id;

    private String name;

    private String description;

    private String telephone;

    private LocalType localType;

    private Address address;

    private List<Multimedia> multimedia;
}
