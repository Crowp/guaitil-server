package com.guaitilsoft.services;


import com.guaitilsoft.models.Product;

import java.util.List;

public interface ProductService {
    List<Product> list();

    Product get(Long id);

    void save(Product entity);

    void update(Long id, Product entity);

    void delete(Long id);

    Product deleteMultimediaById(Long id, Long idMultimedia);

    List<Product> getAllProductByLocalId(Long id);

    List<Product> getAllProductByMemberId(Long id);

    List<Product> getAllProductAcceptedByLocalId( Long id);
}
