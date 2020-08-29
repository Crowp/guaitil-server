package com.guaitilsoft.services.concrete;

import com.guaitilsoft.models.Product;

import com.guaitilsoft.repositories.ProductRepository;
import com.guaitilsoft.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Service
public class ProductServiceImp implements ProductService {

    private final ProductRepository productRepository;

    @Autowired
    public ProductServiceImp(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }


    @Override
    public List<Product> list() {
        Iterable<Product> iterable = productRepository.findAll();
        List<Product> products = new ArrayList<>();
        iterable.forEach(products::add);
        return products;
    }

    @Override
    public Product get(Long id) {
        assert id != null;

        Product product = productRepository.findById(id).orElse(null);
        if(product != null){
            return product;
        }
        throw new EntityNotFoundException("No se encontro un producto con el id: " + id);
    }

    @Override
    public void save(Product entity)   {
        assert entity != null;

        productRepository.save(entity);
    }

    @Override
    public void update(Long id, Product entity) {
        assert id != null;
        assert entity != null;

        Product product = this.get(id);
        product.setName(entity.getName());
        product.setDescription(entity.getDescription());
        product.setStatus(entity.getStatus());
        product.setProductType(entity.getProductType());
        product.setLocal(entity.getLocal());
        product.setMultimedia(entity.getMultimedia());
        product.setProductPrice(entity.getProductPrice());

        productRepository.save(entity);
    }

    @Override
    public void delete(Long id) {
        assert id != null;

        Product product = this.get(id);
        productRepository.delete(product);
    }
}
