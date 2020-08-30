package com.guaitilsoft.web.controllers;

import com.guaitilsoft.exceptions.ApiRequestException;
import com.guaitilsoft.models.Product;
import com.guaitilsoft.models.ProductReview;
import com.guaitilsoft.services.ProductReviewService;
import com.guaitilsoft.web.models.product.ProductView;
import com.guaitilsoft.web.models.productReview.ProductReviewView;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.persistence.EntityNotFoundException;
import java.net.URI;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping(path = "/api/productReview")
public class ProductReviewController {
    public static final Logger logger = LoggerFactory.getLogger(TourController.class);

    private ProductReviewService productReviewService;
    private ModelMapper modelMapper;

    @Autowired
    public ProductReviewController(ProductReviewService productReviewService, ModelMapper modelMapper){
        this.productReviewService = productReviewService;
        this.modelMapper = modelMapper;
    }

    @GetMapping
    public ResponseEntity<List<ProductReview>> get(){
        return  ResponseEntity.ok().body(productReviewService.list());
    }

    @GetMapping("{id}")
    public ResponseEntity<ProductReview> getById(@PathVariable Long id) throws Exception, EntityNotFoundException {
        logger.info("Fetching Product with id {}", id);
        return ResponseEntity.ok().body(productReviewService.get(id));
    }

    @PostMapping
    public ResponseEntity<ProductReviewView> post(@RequestBody ProductReviewView productReviewRequest) throws Exception, EntityNotFoundException  {
        ProductReview productReview = modelMapper.map(productReviewRequest, ProductReview.class);
        logger.info("Creating a product review");
        productReviewService.save(productReview);
        ProductReviewView productReviewResponse = modelMapper.map(productReview, ProductReviewView.class);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(productReview.getId())
                .toUri();
        logger.info("Created product review : {}", productReviewResponse.getId());

        return ResponseEntity.created(location).body(productReviewResponse);
    }

    @PutMapping("{id}")
    public ResponseEntity<ProductReviewView> put(@PathVariable Long id, @RequestBody ProductReviewView productReviewRequest) throws Exception, EntityNotFoundException  {
        if(!id.equals(productReviewRequest.getId())){
            throw new ApiRequestException("El id de la revision del producto: " + productReviewRequest.getId() + " es diferente al id del parametro: " + id);
        }
        ProductReview productReview = modelMapper.map(productReviewRequest, ProductReview.class);
        logger.info("Updating Product Review with id: {}", id);
        productReviewService.update(id, productReview);
        ProductReviewView productReviewResponse = modelMapper.map(productReview, ProductReviewView.class);
        logger.info("Updated Product Review with id: {}", id);
        return ResponseEntity.ok().body(productReviewResponse);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<ProductReviewView> delete(@PathVariable Long id)throws Exception, EntityNotFoundException {
        ProductReviewView productReviewResponse = modelMapper.map(productReviewService.get(id), ProductReviewView.class);
        logger.info("Deleting Product Review with id {}", id);
        productReviewService.delete(id);
        logger.info("Deleted Product Review with id {}", id);
        return ResponseEntity.ok().body(productReviewResponse);
    }
}
