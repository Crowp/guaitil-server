package com.guaitilsoft.services.concrete;

import com.guaitilsoft.models.Multimedia;
import com.guaitilsoft.models.Product;
import com.guaitilsoft.models.ProductReview;
import com.guaitilsoft.models.constant.ReviewState;
import com.guaitilsoft.repositories.ProductRepository;
import com.guaitilsoft.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.guaitilsoft.models.constant.NotificationMessage.PRODUCT_NOTIFICATION;

@Service
public class ProductServiceImp implements ProductService {

    private final ProductRepository productRepository;
    private final ProductReviewService productReviewService;
    private final NotificationService notificationService;

    @Autowired
    public ProductServiceImp(ProductRepository productRepository,
                             ProductReviewService productReviewService,
                             NotificationService notificationService) {
        this.productRepository = productRepository;
        this.productReviewService = productReviewService;
        this.notificationService = notificationService;
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
        throw new EntityNotFoundException("No se encontró un producto con el id: " + id);
    }

    @Override
    public void save(Product entity)   {
        assert entity != null;
        productRepository.save(entity);

        ProductReview review = new ProductReview();
        review.setProductDescription(entity.getProductDescription());
        review.setState(ReviewState.INPROCESS);
        productReviewService.save(review);

        notificationService.createAdminNotification(PRODUCT_NOTIFICATION.getMessage());
    }

    @Override
    public void update(Long id, Product entity) {
        assert id != null;
        assert entity != null;

        Product product = this.get(id);

        ProductReview review = productReviewService.getByProductId(entity.getProductDescription().getId());
        if(review != null){
            if(review.getState() != ReviewState.ACCEPTED){
                review.setState(ReviewState.INPROCESS);
                productReviewService.update(review.getId(), review);
            }
        }

        product.setProductDescription(entity.getProductDescription());
        product.setStatus(entity.getStatus());
        product.setMultimedia(entity.getMultimedia());
        product.setLocal(product.getLocal());


        productRepository.save(product);
    }

    @Override
    public void delete(Long id) {
        assert id != null;

        Product product = this.get(id);
        productRepository.delete(product);
    }

    @Override
    public Product deleteMultimediaById(Long id, Long idMultimedia) {
        Product product = this.get(id);
        Optional<Multimedia> multimediaToDelete = product.getMultimedia()
                .stream()
                .filter(m -> m.getId().equals(idMultimedia))
                .findFirst();

        multimediaToDelete.ifPresent(product::removeMultimediaById);
        productRepository.save(product);
        return product;
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
