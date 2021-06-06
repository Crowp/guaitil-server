package com.guaitilsoft.services.product;

import com.guaitilsoft.exceptions.ApiRequestException;
import com.guaitilsoft.models.Product;
import com.guaitilsoft.models.ProductReview;
import com.guaitilsoft.models.constant.ReviewState;
import com.guaitilsoft.models.constant.TypeEmail;
import com.guaitilsoft.services.EmailSender.EmailSenderService;
import com.guaitilsoft.services.productReview.ProductReviewRepositoryService;
import com.guaitilsoft.services.user.UserRepositoryService;
import com.guaitilsoft.utils.EmailProductTemplate;
import com.guaitilsoft.utils.Utils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Primary
@Service("ProductRepositoryServiceValidation")
public class ProductValidationRepositoryServiceImp implements ProductRepositoryService {

    private final ProductRepositoryService productRepositoryService;
    private final ProductReviewRepositoryService productReviewService;
    private final UserRepositoryService userRepositoryService;
    private final EmailSenderService emailSenderService;

    @Value("${user.gmail-sender-email}")
    private String emailForm;

    @Value("${guaitil-domain.client}")
    private String clientDomain;


    public ProductValidationRepositoryServiceImp(ProductRepositoryService productRepositoryService,
                                                 ProductReviewRepositoryService productReviewService,
                                                 UserRepositoryService userRepositoryService,
                                                 EmailSenderService emailSenderService) {
        this.productRepositoryService = productRepositoryService;
        this.productReviewService = productReviewService;
        this.userRepositoryService = userRepositoryService;
        this.emailSenderService = emailSenderService;
    }

    @Override
    public List<Product> list() {
        return productRepositoryService.list();
    }

    @Override
    public Product get(Long id) {
        Product product = productRepositoryService.get(id);
        if(product != null){
            return product;
        }
        throw new EntityNotFoundException("No se encontró un producto con el id: " + id);
    }

    @Override
    public Product getByProductDescriptionId(Long id) {
        return productRepositoryService.getByProductDescriptionId(id);
    }

    @Override
    public Product save(Product entity) {
        Product product = productRepositoryService.save(entity);

        ProductReview review = new ProductReview();
        review.setProductDescription(entity.getProductDescription());
        review.setState(ReviewState.INPROCESS);
        productReviewService.save(review);
        sendEmailUserAdminForProduct(product);

        return product;
    }

    @Override
    public Product update(Long id, Product entity) {
        if(!id.equals(entity.getId())){
            throw new ApiRequestException("El id del producto: " + entity.getId() + " es diferente al id del parametro: " + id);
        }
        ProductReview review = productReviewService.getByProductId(entity.getProductDescription().getId());
        if(review != null){
            if(review.getState() != ReviewState.ACCEPTED){
                review.setState(ReviewState.INPROCESS);
                productReviewService.update(review.getId(), review);
            }
        }
        return productRepositoryService.update(id, entity);
    }

    @Override
    public void delete(Long id) {
        productRepositoryService.delete(id);
    }

    @Override
    public List<Product> getAllProductByLocalId(Long id) {
        return productRepositoryService.getAllProductByLocalId(id);
    }

    @Override
    public List<Product> getAllProductByMemberId(Long id) {
        return productRepositoryService.getAllProductByMemberId(id);
    }

    @Override
    public List<Product> getAllProductAcceptedByLocalId(Long id) {
        return productRepositoryService.getAllProductAcceptedByLocalId(id);
    }

    private void sendEmailUserAdminForProduct(Product product){
        userRepositoryService.getUsersAdmin().forEach(user -> {
            String productName = product.getProductDescription().getName();
            String productType = product.getProductDescription().getProductType().getMessage();

            String template = new EmailProductTemplate()
                    .addFullName(Utils.getFullMemberName(user.getMember()))
                    .addProductName(productName)
                    .addLocalName(product.getLocal().getLocalDescription().getLocalName())
                    .addProductType(productType)
                    .addTypeInformation(TypeEmail.NEW_PRODUCT)
                    .addRedirectUrl(clientDomain)
                    .getTemplate();
            emailSenderService.sendEmail("Nuevo producto añadido, GuaitilTour", emailForm, user.getEmail(), template);
        });
    }
}
