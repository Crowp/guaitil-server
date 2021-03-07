package com.guaitilsoft.web.controllers;

import com.guaitilsoft.exceptions.ApiRequestException;
import com.guaitilsoft.models.ProductReview;
import com.guaitilsoft.services.ProductReviewService;
import com.guaitilsoft.web.models.productReview.ProductReviewResponse;
import com.guaitilsoft.web.models.productReview.ProductReviewRequest;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.lang.reflect.Type;
import java.net.URI;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping(path = "/api/products-reviews")
public class ProductReviewController {
    public static final Logger logger = LoggerFactory.getLogger(ProductReviewController.class);

    private final ProductReviewService productReviewService;
    private final ModelMapper modelMapper;

    @Autowired
    public ProductReviewController(ProductReviewService productReviewService,
                                   ModelMapper modelMapper){
        this.productReviewService = productReviewService;
        this.modelMapper = modelMapper;
    }

    @GetMapping
    public ResponseEntity<List<ProductReviewResponse>> get(){
        Type listType = new TypeToken<List<ProductReviewRequest>>(){}.getType();
        List<ProductReviewResponse> productReviewViews = modelMapper.map(productReviewService.list(), listType);
        return  ResponseEntity.ok().body(productReviewViews);
    }

    @GetMapping("/member-id/{id}")
    public ResponseEntity<List<ProductReviewResponse>> getByMemberId(@PathVariable Long id){
        Type listType = new TypeToken<List<ProductReviewResponse>>(){}.getType();
        List<ProductReviewResponse> productReviewViews = modelMapper.map(productReviewService.listByIdMember(id), listType);
        return  ResponseEntity.ok().body(productReviewViews);
    }

    @GetMapping("{id}")
    public ResponseEntity<ProductReviewResponse> getById(@PathVariable Long id) {
        ProductReviewResponse productReview = modelMapper.map(productReviewService.get(id), ProductReviewResponse.class);
        logger.info("Fetching Product with id {}", id);
        return ResponseEntity.ok().body(productReview);
    }

    @PostMapping
    public ResponseEntity<ProductReviewResponse> post(@RequestBody ProductReviewRequest productReviewRequest) {
        ProductReview productReview = modelMapper.map(productReviewRequest, ProductReview.class);
        logger.info("Creating a product review");
        productReviewService.save(productReview);
        ProductReviewResponse productReviewResponse = modelMapper.map(productReview, ProductReviewResponse.class);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(productReview.getId())
                .toUri();
        logger.info("Created product review : {}", productReviewResponse.getId());

        return ResponseEntity.created(location).body(productReviewResponse);
    }

    @PutMapping("{id}")
    public ResponseEntity<ProductReviewResponse> put(@PathVariable Long id, @RequestBody ProductReviewRequest productReviewRequest) {
        if(!id.equals(productReviewRequest.getId())){
            throw new ApiRequestException("El id de la revision del producto: " + productReviewRequest.getId() + " es diferente al id del parametro: " + id);
        }
        ProductReview productReview = modelMapper.map(productReviewRequest, ProductReview.class);
        logger.info("Updating Product Review with id: {}", id);
        ProductReviewResponse productReviewResponse = modelMapper.map(productReviewService.update(id, productReview), ProductReviewResponse.class);
        logger.info("Updated Product Review with id: {}", id);
        return ResponseEntity.ok().body(productReviewResponse);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<ProductReviewResponse> delete(@PathVariable Long id) {
        ProductReviewResponse productReviewResponse = modelMapper.map(productReviewService.get(id), ProductReviewResponse.class);
        logger.info("Deleting Product Review with id {}", id);
        productReviewService.delete(id);
        logger.info("Deleted Product Review with id {}", id);
        return ResponseEntity.ok().body(productReviewResponse);
    }

}
