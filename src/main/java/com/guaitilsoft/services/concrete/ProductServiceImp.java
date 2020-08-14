package com.guaitilsoft.services.concrete;

import com.guaitilsoft.models.Product;

import com.guaitilsoft.repositories.ProductRepository;
import com.guaitilsoft.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class ProductServiceImp implements ProductService {

    private ProductRepository productRepository;

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
        Product product = productRepository.findById(id).orElse(null);
        if(product == null){
            throw new EntityNotFoundException();
        }
        return product;
    }

    @Override
    public void save(Product entity)   {
        productRepository.save(entity);
    }

    @Override
    public void update(Long id, Product entity) {
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
        Product product = this.get(id);
        if(product != null){
            productRepository.delete(product);
        }
    }
}
