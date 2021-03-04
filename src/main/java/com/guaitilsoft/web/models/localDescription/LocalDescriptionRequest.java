package com.guaitilsoft.web.models.localDescription;

import com.guaitilsoft.models.Address;
import com.guaitilsoft.models.constant.LocalType;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class LocalDescriptionRequest {

    private Long id;

    private String localName;

    private String description;

    private String localTelephone;

    private LocalType localType;

    private Address address;
}
