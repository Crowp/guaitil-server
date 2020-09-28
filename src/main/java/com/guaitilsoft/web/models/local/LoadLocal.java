package com.guaitilsoft.web.models.local;

import com.guaitilsoft.models.Address;
import com.guaitilsoft.models.Multimedia;
import com.guaitilsoft.models.constant.LocalType;
import com.guaitilsoft.web.models.product.GetProduct;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
public class LoadLocal {

    private Long id;

    private String name;

    private String description;

    private String telephone;

    private LocalType localType;

    private Date createdAt;

    private Date updatedAt;

    private Address address;

    private List<Multimedia> multimedia;

    private List<GetProduct> products;
}
