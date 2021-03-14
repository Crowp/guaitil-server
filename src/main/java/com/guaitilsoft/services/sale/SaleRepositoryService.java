package com.guaitilsoft.services.sale;

import com.guaitilsoft.models.Sale;

import java.util.List;

public interface SaleRepositoryService {
    List<Sale> list();

    Sale get(Long id);

    Sale save(Sale entity);

    Sale update(Long id, Sale entity);

    void delete(Long id);

    List<Sale> getAllSaleByMemberId(Long id);
}
