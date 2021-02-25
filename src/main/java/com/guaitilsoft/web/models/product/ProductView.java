package com.guaitilsoft.web.models.product;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.guaitilsoft.models.ProductPrice;
import com.guaitilsoft.models.constant.ProductType;
import com.guaitilsoft.web.models.local.GetLocal;
import com.guaitilsoft.web.models.multimedia.MultimediaResponse;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import java.time.LocalDateTime;
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

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updatedAt;

    private GetLocal local;

    private ProductPrice productPrice;

    private List<MultimediaResponse> multimedia;
}
