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
    private final EmailSenderService emailSenderService;

    public ProductReviewValidationRepositoryServiceImp(ProductReviewRepositoryService productReviewRepositoryService,
                                                       EmailSenderService emailSenderService) {
        this.productReviewRepositoryService = productReviewRepositoryService;
        this.emailSenderService = emailSenderService;
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
        ProductReview productReview = productReviewRepositoryService.update(id, entity);
        productReview.setComment("A");
        return productReview;
    }

    @Override
    public void delete(Long id) {
        productReviewRepositoryService.delete(id);
    }

    private void sendEmailProduct(ProductReview productReview){
        String fullName = "";
        String productName = productReview.getProductDescription().getName();
        String email = "";

        String template = new EmailProductTemplate()
                .addFullName(fullName)
                .addProductName(productName)
                .addTypeInformation(TypeEmail.REVISED_PRODUCT)
                .getTemplate();
        emailSenderService.sendEmail("Revisión de producto, GuaitilTour", GuaitilEmailInfo.getEmailFrom(), email, template);
    }
}
