package com.guaitilsoft.services;

import com.guaitilsoft.models.ProductDescription;

import java.util.List;

public interface ProductDescriptionService {
    List<ProductDescription> list();
    ProductDescription get(Long id);
}
