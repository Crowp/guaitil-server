package com.guaitilsoft.services.sale;

import com.guaitilsoft.models.Sale;
import com.guaitilsoft.web.models.sale.SaleRequest;
import com.guaitilsoft.web.models.sale.SaleResponse;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.util.List;

@Service
public class SaleServiceImp implements SaleService {

    private final SaleRepositoryService saleRepositoryService;
    private final ModelMapper modelMapper;

    @Autowired
    public SaleServiceImp(SaleRepositoryService saleRepositoryService,
                          ModelMapper modelMapper) {
        this.saleRepositoryService = saleRepositoryService;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<SaleResponse> list() {
        return this.parseToSaleResponseList(saleRepositoryService.list());
    }

    @Override
    public SaleResponse get(Long id) {
        return this.parseToSaleResponse(saleRepositoryService.get(id));
    }

    @Override
    public SaleResponse save(SaleRequest entity) {
        Sale sale = this.parseToSale(entity);
        return onSaveSale(sale);
    }

    private SaleResponse onSaveSale(Sale saleToStore){
        Sale sale = this.saleRepositoryService.save(saleToStore);
        return parseToSaleResponse(sale);
    }

    @Override
    public SaleResponse update(Long id, SaleRequest entity) {
        return parseToSaleResponse(saleRepositoryService.update(id, this.parseToSale(entity)));
    }

    @Override
    public void delete(Long id) {
        saleRepositoryService.delete(id);
    }

    @Override
    public List<SaleResponse> getAllSaleByMemberId(Long id) {
        return parseToSaleResponseList(saleRepositoryService.getAllSaleByMemberId(id));
    }

    @Override
    public List<Sale> saleList() {
        return saleRepositoryService.list();
    }

    private List<SaleResponse> parseToSaleResponseList(List<Sale> sales){
        Type lisType = new TypeToken<List<SaleResponse>>(){}.getType();
        return this.modelMapper.map(sales, lisType);
    }
    private SaleResponse parseToSaleResponse(Sale sale){
        return this.modelMapper.map(sale, SaleResponse.class);
    }

    private Sale parseToSale(SaleRequest saleRequest){
        return this.modelMapper.map(saleRequest, Sale.class);
    }
}
