package com.guaitilsoft.services.sale;

import com.guaitilsoft.exceptions.ApiRequestException;
import com.guaitilsoft.models.Member;
import com.guaitilsoft.models.Sale;
import com.guaitilsoft.services.member.MemberRepositoryService;
import com.guaitilsoft.services.productDescription.ProductDesRepositoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service("SaleRepositoryServiceValidation")
public class SaleValidationRepositoryServiceImp implements SaleRepositoryService{

    private final SaleRepositoryService saleRepositoryService;
    private final ProductDesRepositoryService productDescriptionService;
    private final MemberRepositoryService memberRepositoryService;

    @Autowired
    public SaleValidationRepositoryServiceImp(@Qualifier("SaleRepositoryServiceBasic") SaleRepositoryService saleRepositoryService,
                                              ProductDesRepositoryService productDescriptionService,
                                              @Qualifier("MemberRepositoryServiceValidation") MemberRepositoryService memberRepositoryService) {
        this.saleRepositoryService = saleRepositoryService;
        this.productDescriptionService = productDescriptionService;
        this.memberRepositoryService = memberRepositoryService;
    }

    @Override
    public List<Sale> list() {
        return saleRepositoryService.list();
    }

    @Override
    public Sale get(Long id) {
        Sale sale = saleRepositoryService.get(id);
        if (sale != null){
            return sale;
        }
        throw new EntityNotFoundException("No se encontró la venta con el id: " + id);
    }

    @Override
    public Sale save(Sale entity) {
        getProductDescription(entity);
        return saleRepositoryService.save(entity);
    }

    @Override
    public Sale update(Long id, Sale entity) {
        if(!id.equals(entity.getId())){
            throw new ApiRequestException("El id de la venta: " + entity.getId() + " es diferente al id del parametro: " + id);
        }
        getProductDescription(entity);
        return saleRepositoryService.update(id, entity);
    }

    @Override
    public void delete(Long id) {
        saleRepositoryService.delete(id);
    }

    @Override
    public List<Sale> getAllSaleByMemberId(Long id) {
        Member member = memberRepositoryService.get(id);
        return saleRepositoryService.getAllSaleByMemberId(member.getId());
    }

    private void getProductDescription(Sale sale){
        sale.setProductDescription(this.productDescriptionService.get(sale.getProductDescription().getId()));
    }
}
