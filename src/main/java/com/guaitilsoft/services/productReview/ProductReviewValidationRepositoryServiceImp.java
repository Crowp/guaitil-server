package com.guaitilsoft.services.productReview;

import com.guaitilsoft.exceptions.ApiRequestException;
import com.guaitilsoft.models.Product;
import com.guaitilsoft.models.ProductReview;
import com.guaitilsoft.models.constant.TypeEmail;
import com.guaitilsoft.services.EmailSender.EmailSenderService;
import com.guaitilsoft.utils.EmailProductTemplate;
import com.guaitilsoft.utils.GuaitilEmailInfo;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Primary
@Service("ProductReviewRepositoryServiceValidation")
public class ProductReviewValidationRepositoryServiceImp implements ProductReviewRepositoryService{

    private final ProductReviewRepositoryService productReviewRepositoryService;

    public ProductReviewValidationRepositoryServiceImp(ProductReviewRepositoryService productReviewRepositoryService) {
        this.productReviewRepositoryService = productReviewRepositoryService;
    }

    @Override
    public List<ProductReview> list() {
        return productReviewRepositoryService.list();
    }

    @Override
    public List<ProductReview> listByIdMember(Long memberId) {
        return productReviewRepositoryService.listByIdMember(memberId);
    }

    @Override
    public ProductReview get(Long id) {
        ProductReview productReview = productReviewRepositoryService.get(id);
        if(productReview != null){
            return productReview;
        }
        throw new EntityNotFoundException("No se encontró la revisión del producto con el id: " + id);
    }

    @Override
    public ProductReview getByProductId(Long productId) {
        ProductReview productReview = productReviewRepositoryService.getByProductId(productId);
        if(productReview != null){
            return productReview;
        }
        throw new EntityNotFoundException("No se encontró la revisión del producto con el id: " + productId);
    }

    @Override
    public ProductReview save(ProductReview entity) {
        return productReviewRepositoryService.save(entity);
    }

    @Override
    public ProductReview update(Long id, ProductReview entity) {
        if(!id.equals(entity.getId())){
            throw new ApiRequestException("El id de la revision del producto: " + entity.getId() + " es diferente al id del parametro: " + id);
        }
        return productReviewRepositoryService.update(id, entity);
    }

    @Override
    public void delete(Long id) {
        productReviewRepositoryService.delete(id);
    }
}
