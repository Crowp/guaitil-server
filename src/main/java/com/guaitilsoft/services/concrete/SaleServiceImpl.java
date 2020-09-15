package com.guaitilsoft.services.concrete;

import com.guaitilsoft.models.Sale;
import com.guaitilsoft.repositories.SaleRepository;
import com.guaitilsoft.services.SaleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Service
public class SaleServiceImpl implements SaleService {

    private SaleRepository saleRepository;

    @Autowired
    public SaleServiceImpl(SaleRepository saleRepository){
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

        Sale sale = saleRepository.findById(id).orElse(null);
        if(sale != null){
            return sale;
        }
        throw new EntityNotFoundException("No se encontro la venta con el id: " + id);
    }

    @Override
    public void save(Sale entity) {
        assert entity != null;
        saleRepository.save(entity);
    }

    @Override
    public void update(Long id, Sale entity) {
        assert id != null;
        assert entity != null;

        Sale sale = this.get(id);
        sale.setSaleDate(entity.getSaleDate());
        sale.setProduct(entity.getProduct());
        sale.setAmountSold(entity.getAmountSold());
        sale.setUpdatedAt(entity.getUpdatedAt());

        saleRepository.save(entity);
    }

    @Override
    public void delete(Long id) {
        assert id != null;

        Sale sale = this.get(id);
        saleRepository.delete(sale);
    }
}