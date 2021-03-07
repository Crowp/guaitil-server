package com.guaitilsoft.services.sale;

import com.guaitilsoft.models.Sale;
import com.guaitilsoft.web.models.sale.SaleRequest;
import com.guaitilsoft.web.models.sale.SaleResponse;

import java.util.List;

public interface SaleService {
    List<SaleResponse> list();

    SaleResponse get(Long id);

    SaleResponse save(SaleRequest entity);

    SaleResponse update(Long id, SaleRequest entity);

    void delete(Long id);

    List<SaleResponse> getAllSaleByMemberId(Long id);

    List<Sale> saleList();
}
