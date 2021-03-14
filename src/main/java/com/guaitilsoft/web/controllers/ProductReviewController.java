package com.guaitilsoft.web.controllers;

import com.guaitilsoft.services.productReview.ProductReviewService;
import com.guaitilsoft.web.models.productReview.ProductReviewResponse;
import com.guaitilsoft.web.models.productReview.ProductReviewRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping(path = "/api/products-reviews")
public class ProductReviewController {
    public static final Logger logger = LoggerFactory.getLogger(ProductReviewController.class);

    private final ProductReviewService productReviewService;

    @Autowired
    public ProductReviewController(ProductReviewService productReviewService){
        this.productReviewService = productReviewService;
    }


    @GetMapping
    public ResponseEntity<List<ProductReviewResponse>> get(){
        List<ProductReviewResponse> productReviewViews = productReviewService.list();
        return  ResponseEntity.ok().body(productReviewViews);
    }

    @GetMapping("/member-id/{id}")
    public ResponseEntity<List<ProductReviewResponse>> getByMemberId(@PathVariable Long id){
        List<ProductReviewResponse> productReviewViews = productReviewService.listByIdMember(id);
        return  ResponseEntity.ok().body(productReviewViews);
    }

    @GetMapping("{id}")
    public ResponseEntity<ProductReviewResponse> getById(@PathVariable Long id) {
        ProductReviewResponse productReview = productReviewService.get(id);
        logger.info("Fetching Product with id {}", id);
        return ResponseEntity.ok().body(productReview);
    }

    @PostMapping
    public ResponseEntity<ProductReviewResponse> post(@RequestBody ProductReviewRequest productReviewRequest) {
        logger.info("Creating a product review");
        ProductReviewResponse productReviewResponse = productReviewService.save(productReviewRequest);
        URI location = getUriResourceLocation(productReviewResponse.getId());

        logger.info("Created product review : {}", productReviewResponse.getId());
        return ResponseEntity.created(location).body(productReviewResponse);
    }

    @PutMapping("{id}")
    public ResponseEntity<ProductReviewResponse> put(@PathVariable Long id, @RequestBody ProductReviewRequest productReviewRequest) {
        logger.info("Updating Product Review with id: {}", id);
        ProductReviewResponse productReviewResponse = productReviewService.update(id, productReviewRequest);
        logger.info("Updated Product Review with id: {}", id);
        return ResponseEntity.ok().body(productReviewResponse);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<ProductReviewResponse> delete(@PathVariable Long id) {
        logger.info("Deleting Product Review with id {}", id);
        ProductReviewResponse productReviewResponse = productReviewService.get(id);
        productReviewService.delete(id);
        logger.info("Deleted Product Review with id {}", id);
        return ResponseEntity.ok().body(productReviewResponse);
    }

    private URI getUriResourceLocation(Long id) {
        return ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(id)
                .toUri();
    }
}
