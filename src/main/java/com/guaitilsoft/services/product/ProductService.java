package com.guaitilsoft.services.product;

import com.guaitilsoft.web.models.product.ProductRequest;
import com.guaitilsoft.web.models.product.ProductResponse;

import java.util.List;

public interface ProductService {
    List<ProductResponse> list();

    ProductResponse get(Long id);

    ProductResponse getByProductDescriptionId(Long id);

    ProductResponse save(ProductRequest entity);

    ProductResponse update(Long id, ProductRequest entity);

    void delete(Long id);

    List<ProductResponse> getAllProductByLocalId(Long id);

    List<ProductResponse> getAllProductByMemberId(Long id);

    List<ProductResponse> getAllProductAcceptedByLocalId( Long id);

    ProductResponse deleteMultimediaById(Long id, Long idMultimedia);
}