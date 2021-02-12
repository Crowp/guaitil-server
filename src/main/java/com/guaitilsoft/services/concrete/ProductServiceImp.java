package com.guaitilsoft.services.concrete;

import com.guaitilsoft.models.Multimedia;
import com.guaitilsoft.models.Product;

import com.guaitilsoft.models.ProductReview;
import com.guaitilsoft.models.constant.NotificationMessage;
import com.guaitilsoft.models.constant.ReviewState;
import com.guaitilsoft.repositories.ProductRepository;
import com.guaitilsoft.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductServiceImp implements ProductService {

    private final ProductRepository productRepository;
    private final MultimediaService multimediaService;
    private final ProductReviewService productReviewService;
    private final SaleService saleService;
    private final NotificationService notificationService;

    @Autowired
    public ProductServiceImp(ProductRepository productRepository, MultimediaService multimediaService,
                             ProductReviewService productReviewService, SaleService saleService,
                             NotificationService notificationService) {
        this.productRepository = productRepository;
        this.multimediaService = multimediaService;
        this.productReviewService = productReviewService;
        this.saleService = saleService;
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
        throw new EntityNotFoundException("No se encontro un producto con el id: " + id);
    }

    @Override
    public void save(Product entity)   {
        assert entity != null;
        productRepository.save(entity);

        ProductReview review = new ProductReview();
        review.setProduct(entity);
        review.setCreatedAt(new Date());
        review.setUpdatedAt(new Date());
        review.setState(ReviewState.INPROCESS);
        productReviewService.save(review);

        notificationService.createAdminNotification(NotificationMessage.PRODUCT_NOTIFICATION.getMessage());
    }

    @Override
    public void update(Long id, Product entity) {
        assert id != null;
        assert entity != null;

        Product product = this.get(id);

        ProductReview review = productReviewService.getByProductId(id);
        if(review != null){
            if(review.getState() != ReviewState.ACCEPTED){
                review.setState(ReviewState.INPROCESS);
                productReviewService.update(review.getId(), review);
            }
        }

        product.setName(entity.getName());
        product.setDescription(entity.getDescription());
        product.setStatus(entity.getStatus());
        product.setProductType(entity.getProductType());
        product.setLocal(entity.getLocal());
        product.setMultimedia(entity.getMultimedia());
        product.setProductPrice(entity.getProductPrice());
        product.setUpdatedAt(new Date());

        productRepository.save(product);
    }

    @Override
    public void delete(Long id) {
        assert id != null;

        Product product = this.get(id);
        List<Multimedia> multimediaList = new ArrayList<>(product.getMultimedia());
        product.setMultimedia(null);
        productRepository.save(product);
        if(multimediaList.size() > 0){
            multimediaList.forEach(media -> multimediaService.delete(media.getId()));
        }
        productReviewService.deleteProductReviewByProductId(id);
        saleService.deleteSaleByProductId(id);
        productRepository.delete(product);
    }

    @Override
    public Product deleteMultimediaById(Long id, Long idMultimedia) {
        Product product = this.get(id);
        List<Multimedia> multimedia = product.getMultimedia()
                .stream()
                .filter(media -> !media.getId().equals(idMultimedia))
                .collect(Collectors.toList());
        product.setMultimedia(multimedia);
        productRepository.save(product);
        multimediaService.delete(idMultimedia);
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
