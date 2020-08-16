package com.guaitilsoft.web.models.Local;

import com.guaitilsoft.models.Address;
import com.guaitilsoft.models.Multimedia;
import com.guaitilsoft.models.constant.LocalType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import javax.persistence.CascadeType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
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

    private String personId;

    private List<Multimedia> multimedia;
}
