package com.guaitilsoft.services.product;

import com.guaitilsoft.models.Product;

import java.util.List;

public interface ProductRepositoryService {
    List<Product> list();

    Product get(Long id);

    Product getByProductDescriptionId(Long id);

    Product save(Product entity);

    Product update(Long id, Product entity);

    Product updateProductByAdminUser(Long id, Product entity);

    void delete(Long id);

    List<Product> getAllProductByLocalId(Long id);

    List<Product> getAllProductByMemberId(Long id);

    List<Product> getAllProductAcceptedByLocalId( Long id);
}
