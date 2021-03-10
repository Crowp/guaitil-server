package com.guaitilsoft.services.productReview;

import com.guaitilsoft.models.ProductReview;
import com.guaitilsoft.web.models.productReview.ProductReviewRequest;
import com.guaitilsoft.web.models.productReview.ProductReviewResponse;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.util.List;

@Service
public class ProductReviewServiceImp implements ProductReviewService {
    private final ProductReviewRepositoryService productReviewRepositoryService;
    private final ModelMapper modelMapper;

    public ProductReviewServiceImp(@Qualifier("ProductReviewRepositoryServiceValidation") ProductReviewRepositoryService productReviewRepositoryService, ModelMapper modelMapper) {
        this.productReviewRepositoryService = productReviewRepositoryService;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<ProductReviewResponse> list() {
        return this.parseToListProductReviewResponses(productReviewRepositoryService.list());
    }

    @Override
    public List<ProductReviewResponse> listByIdMember(Long memberId) {
        return this.parseToListProductReviewResponses(productReviewRepositoryService.listByIdMember(memberId));
    }

    @Override
    public ProductReviewResponse get(Long id) {
        return this.parseToProductReviewResponse(productReviewRepositoryService.get(id));
    }

    @Override
    public ProductReviewResponse save(ProductReviewRequest entity) {
        ProductReview productReview = this.parseToProductReview(entity);
        return onSaveProductReviewResponse(productReview);
    }
    private ProductReviewResponse onSaveProductReviewResponse (ProductReview productReviewToStore){
        ProductReview productReview = productReviewRepositoryService.save(productReviewToStore);
        return this.parseToProductReviewResponse(productReview);
    }
    @Override
    public ProductReviewResponse update(Long id, ProductReviewRequest entity) {
        return parseToProductReviewResponse(productReviewRepositoryService.update(id, this.parseToProductReview(entity)));
    }

    @Override
    public void delete(Long id) {productReviewRepositoryService.delete(id); }

    private List<ProductReviewResponse> parseToListProductReviewResponses (List<ProductReview> productReviewList){
        Type lisType = new TypeToken<List<ProductReviewResponse>>(){}.getType();
        return modelMapper.map(productReviewList, lisType);
    }
    private ProductReviewResponse parseToProductReviewResponse (ProductReview productReview){
        return modelMapper.map(productReview, ProductReviewResponse.class);
    }
    private ProductReview parseToProductReview (ProductReviewRequest productReviewRequest){
        return modelMapper.map(productReviewRequest, ProductReview.class);
    }
}
