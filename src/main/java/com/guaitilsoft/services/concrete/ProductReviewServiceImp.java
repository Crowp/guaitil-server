package com.guaitilsoft.services.concrete;

import com.guaitilsoft.models.ProductReview;
import com.guaitilsoft.repositories.ProductReviewRepository;
import com.guaitilsoft.services.ProductReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
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
    public List<ProductReview> listByIdMember(Long memberId) {
        assert memberId != null;
        Iterable<ProductReview> iterable = productReviewRepository.selectProductReviewByMemberId(memberId);
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
    public ProductReview getByProductId(Long productId) {
        assert productId != null;

        ProductReview productReview = productReviewRepository
                .selectProductReviewByProductId(productId)
                .orElse(null);
        if(productReview != null){
            return productReview;
        }
        throw new EntityNotFoundException("No se encontro un la revision de producto con el id: ");
    }

    @Override
    public void save(ProductReview entity)  {
        assert entity != null;
        entity.setUpdatedAt(new Date());
        entity.setCreatedAt(new Date());
        productReviewRepository.save(entity);
    }

    @Override
    public ProductReview update(Long id, ProductReview entity) {
        assert id != null;
        assert entity != null;

        ProductReview productReview = this.get(id);
        productReview.setReviewDate(new Date());
        productReview.setState(entity.getState());
        productReview.setComment(entity.getComment());
        productReview.setUpdatedAt(new Date());
        productReviewRepository.save(entity);
        return productReview;
    }

    @Override
    public void delete(Long id) {
        assert id != null;

        ProductReview productReview = this.get(id);
        productReviewRepository.delete(productReview);
    }

    @Override
    public void deleteProductReviewByProductId(Long productId) {
          Optional<ProductReview> productReviewOptional = productReviewRepository.selectProductReviewByProductId(productId);
          productReviewOptional.ifPresent(productReview -> this.delete(productReview.getId()));
    }
}
