package com.guaitilsoft.web.models.product;

import com.guaitilsoft.models.Multimedia;
import com.guaitilsoft.models.ProductPrice;
import com.guaitilsoft.models.constant.ProductType;
import com.guaitilsoft.web.models.local.GetLocal;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
public class LoadProduct {
    private Long id;

    private String name;

    private String description;

    private Boolean status;

    private ProductType productType;

    private Date createdAt;

    private Date updatedAt;

    private ProductPrice productPrice;

    private GetLocal local;

    private List<Multimedia> multimedia;
}
