package com.guaitilsoft.services.sale;

import com.guaitilsoft.models.Sale;
import com.guaitilsoft.repositories.SaleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("SaleRepositoryServiceBasic")
public class SaleRepositoryServiceImp implements SaleRepositoryService {

    private final SaleRepository saleRepository;

    @Autowired
    public SaleRepositoryServiceImp(SaleRepository saleRepository) {
        this.saleRepository = saleRepository;
    }

    @Override
    public List<Sale> list() {
        Iterable<Sale> iterable = saleRepository.findAll();
        List<Sale> sales = new ArrayList<>();
        iterable.forEach(sales::add);
        return sales;
    }

    @Override
    public Sale get(Long id) {
        assert id != null;
        return this.saleRepository.findById(id).orElse(null);
    }

    @Override
    public Sale save(Sale entity) {
        assert entity != null;
        return this.saleRepository.save(entity);
    }

    @Override
    public Sale update(Long id, Sale entity) {
        assert id != null;
        assert entity != null;

        Sale sale = this.get(id);
        sale.setSaleDate(entity.getSaleDate());
        sale.setProductDescription(entity.getProductDescription());
        sale.setAmountSold(entity.getAmountSold());

        return saleRepository.save(sale);
    }

    @Override
    public void delete(Long id) {
        assert id != null;
        Sale sale = this.get(id);
        this.saleRepository.delete(sale);
    }

    @Override
    public List<Sale> getAllSaleByMemberId(Long id) {
        assert id != null;
        Iterable<Sale> iterable = saleRepository.getAllSaleByMemberId(id);
        List<Sale> sales = new ArrayList<>();
        iterable.forEach(sales::add);
        return sales;
    }
}
