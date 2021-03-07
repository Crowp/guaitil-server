package com.guaitilsoft.services.concrete;

import com.guaitilsoft.models.ProductDescription;
import com.guaitilsoft.repositories.ProductDescriptionRepository;
import com.guaitilsoft.services.ProductDescriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Service
public class ProductDescriptionServiceImp implements ProductDescriptionService {

    private final ProductDescriptionRepository productDescriptionRepository;

    @Autowired
    public ProductDescriptionServiceImp(ProductDescriptionRepository productDescriptionRepository) {
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
}
