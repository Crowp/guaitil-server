package com.guaitilsoft.services.concrete;

import com.guaitilsoft.models.ProductReview;
import com.guaitilsoft.repositories.ProductReviewRepository;
import com.guaitilsoft.services.ProductReviewService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class ProductReviewServiceImp implements ProductReviewService {
    private ProductReviewRepository productReviewRepository;

    @Autowired
    public ProductReviewServiceImp(ProductReviewRepository productReviewRepository) {
        this.productReviewRepository = productReviewRepository;
    }

    @Override
    public List<ProductReview> list() {
        Iterable<ProductReview> iterable = productReviewRepository.findAll();
        List<ProductReview> productReviews = new ArrayList<>();
        iterable.forEach(productReviews::add);
        return productReviews;
    }

    @Override
    public ProductReview get(Long id) {
        assert id != null;

        ProductReview productReview = productReviewRepository.findById(id).orElse(null);
        if(productReview != null){
            return productReview;
        }
        throw new EntityNotFoundException("No se encontro un la revision de producto con el id: ");
    }

    @Override
    public void save(ProductReview entity)  {
        productReviewRepository.save(entity);
    }

    @Override
    public void update(Long id, ProductReview entity) {
        ProductReview productReview = this.get(id);

        productReview.setReviewDate(entity.getReviewDate());
        productReview.setState(entity.getState());
        productReview.setProduct(entity.getProduct());

        productReviewRepository.save(entity);
    }

    @Override
    public void delete(Long id) {
        assert id != null;

        ProductReview productReview = this.get(id);
        productReviewRepository.delete(productReview);
    }
}
