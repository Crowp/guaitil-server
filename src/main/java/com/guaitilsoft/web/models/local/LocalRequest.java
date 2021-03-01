package com.guaitilsoft.web.models.local;

import com.guaitilsoft.models.Address;
import com.guaitilsoft.models.constant.LocalType;
import com.guaitilsoft.web.models.member.MemberId;
import com.guaitilsoft.web.models.multimedia.MultimediaResponse;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class LocalRequest {

    private Long id;

    private String localName;

    private String description;

    private String localTelephone;

    private LocalType localType;

    private Address address;

    private Boolean state;

    private MemberId member;

    private List<MultimediaResponse> multimedia;
}
