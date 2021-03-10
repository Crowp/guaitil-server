package com.guaitilsoft.services.productReview;

import com.guaitilsoft.models.ProductReview;
import com.guaitilsoft.repositories.ProductReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service("ProductReviewRepositoryServiceBasic")
public class ProductReviewRepositoryServiceImp implements ProductReviewRepositoryService {

    private final ProductReviewRepository productReviewRepository;

    @Autowired
    public ProductReviewRepositoryServiceImp(ProductReviewRepository productReviewRepository) {
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
        return productReviewRepository.findById(id).orElse(null);
    }

    @Override
    public ProductReview getByProductId(Long productId) {
        assert productId != null;

        return productReviewRepository
                .selectProductReviewByProductId(productId)
                .orElse(null);
    }

    @Override
    public ProductReview save(ProductReview entity)  {
        assert entity != null;
        return productReviewRepository.save(entity);
    }

    @Override
    public ProductReview update(Long id, ProductReview entity) {
        assert id != null;
        assert entity != null;

        ProductReview productReview = this.get(id);
        productReview.setReviewDate(LocalDateTime.now());
        productReview.setState(entity.getState());
        productReview.setComment(entity.getComment());
        productReview.setCreatedAt(productReview.getCreatedAt());
        productReviewRepository.save(entity);
        return productReview;
    }

    @Override
    public void delete(Long id) {
        assert id != null;

        ProductReview productReview = this.get(id);
        productReviewRepository.delete(productReview);
    }

}
