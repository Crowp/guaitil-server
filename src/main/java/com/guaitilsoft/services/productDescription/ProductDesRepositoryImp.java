package com.guaitilsoft.services.productDescription;

import com.guaitilsoft.models.ProductDescription;
import com.guaitilsoft.repositories.ProductDescriptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Service
public class ProductDesRepositoryImp implements ProductDesRepositoryService {

    private final ProductDescriptionRepository productDescriptionRepository;

    @Autowired
    public ProductDesRepositoryImp(ProductDescriptionRepository productDescriptionRepository) {
        this.productDescriptionRepository = productDescriptionRepository;
    }

    @Override
    public List<ProductDescription> list() {
        Iterable<ProductDescription> iterable = productDescriptionRepository.findAll();
        List<ProductDescription> productDescriptions = new ArrayList<>();
        iterable.forEach(productDescriptions::add);
        return productDescriptions;
    }

    @Override
    public ProductDescription get(Long id) {
        assert id != null;

        ProductDescription productDescription = productDescriptionRepository.findById(id).orElse(null);
        if (productDescription != null){
            return productDescription;
        }
        throw new EntityNotFoundException("No se encontró un product description con el id: " + id);
    }

    @Override
    public List<ProductDescription> getLocalsDescriptionNoRelationships() {
        return productDescriptionRepository.getProductsDescriptionNoRelationships();
    }

    @Override
    public void delete(Long id) {
        assert id != null;
        ProductDescription productDescription = this.get(id);
        productDescriptionRepository.delete(productDescription);
    }
}
