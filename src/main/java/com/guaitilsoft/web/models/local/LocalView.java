package com.guaitilsoft.web.models.local;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.guaitilsoft.models.Address;
import com.guaitilsoft.models.Member;
import com.guaitilsoft.models.Multimedia;
import com.guaitilsoft.models.Product;
import com.guaitilsoft.models.constant.LocalType;
import com.guaitilsoft.web.models.member.LoadMember;
import com.guaitilsoft.web.models.multimedia.MultimediaResponse;
import com.guaitilsoft.web.models.product.LoadProduct;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
public class LocalView {

    private Long id;

    private String name;

    private String description;

    private String telephone;

    private LocalType localType;

    private Date createdAt;

    private Date updatedAt;

    private Address address;

    private LoadMember member;

    private List<MultimediaResponse> multimedia;

    private List<LoadProduct> products;
}
