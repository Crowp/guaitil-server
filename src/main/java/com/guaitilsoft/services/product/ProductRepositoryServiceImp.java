package com.guaitilsoft.services.product;

import com.guaitilsoft.models.Product;
import com.guaitilsoft.models.constant.ReviewState;
import com.guaitilsoft.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("ProductRepositoryServiceBasic")
public class ProductRepositoryServiceImp implements ProductRepositoryService {

    private final ProductRepository productRepository;

    @Autowired
    public ProductRepositoryServiceImp(ProductRepository productRepository) {
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
        return productRepository.findById(id).orElse(null);
    }

    @Override
    public Product save(Product entity) {
        assert entity != null;
        return productRepository.save(entity);
    }

    @Override
    public Product update(Long id, Product entity) {
        assert id != null;
        assert entity != null;

        Product product = this.get(id);
        product.setProductDescription(entity.getProductDescription());
        product.setMultimedia(entity.getMultimedia());
        product.setShowProduct(entity.getShowProduct());
        product.setLocal(product.getLocal());

        return productRepository.save(product);
    }

    @Override
    public void delete(Long id) {
        assert id != null;

        Product product = this.get(id);
        productRepository.delete(product);
    }

    @Override
    public List<Product> getAllProductByLocalId(Long id) {
        assert id != null;
        Iterable<Product> iterable = productRepository.getAllProductByLocalId(id);
        List<Product> products = new ArrayList<>();
        iterable.forEach(products::add);
        return products;
    }

    @Override
    public List<Product> getAllProductByMemberId(Long id) {
        assert id != null;
        Iterable<Product> iterable = productRepository.getAllProductByMemberId(id);
        List<Product> products = new ArrayList<>();
        iterable.forEach(products::add);
        return products;
    }

    @Override
    public List<Product> getAllProductAcceptedByLocalId(Long id) {
        assert id != null;
        Iterable<Product> iterable = productRepository.getAllProductAcceptedByLocalId(ReviewState.ACCEPTED, id, true);
        List<Product> products = new ArrayList<>();
        iterable.forEach(products::add);
        return products;
    }
}
