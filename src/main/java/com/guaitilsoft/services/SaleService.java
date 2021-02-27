package com.guaitilsoft.services;

import com.guaitilsoft.models.Sale;

import java.util.List;

public interface SaleService {
    List<Sale> list();

    Sale get(Long id);

    void save(Sale entity);

    void update(Long id, Sale entity);

    void delete(Long id);

    List<Sale> getAllSaleByMemberId(Long id);

}
