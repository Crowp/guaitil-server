package com.guaitilsoft.services.productDescription;

import com.guaitilsoft.models.ProductDescription;

import java.util.List;

public interface ProductDesRepositoryService {
    List<ProductDescription> list();
    ProductDescription get(Long id);
    List<ProductDescription> getLocalsDescriptionNoRelationships();
    void delete(Long id);
}
