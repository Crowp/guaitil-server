package com.guaitilsoft.web.models.product;

import com.guaitilsoft.models.Multimedia;
import com.guaitilsoft.models.ProductPrice;
import com.guaitilsoft.models.constant.ProductType;
import com.guaitilsoft.web.models.local.LoadLocal;
import com.guaitilsoft.web.models.multimedia.MultimediaResponse;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class ProductView {
    private Long id;

    private String name;

    private String description;

    private Boolean status;

    private ProductType productType;

    private Date createdAt;

    private Date updatedAt;

    private LoadLocal local;

    private ProductPrice productPrice;

    private List<MultimediaResponse> multimedia;
}
