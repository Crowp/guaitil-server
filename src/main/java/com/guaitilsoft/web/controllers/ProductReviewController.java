package com.guaitilsoft.web.controllers;

import com.guaitilsoft.exceptions.ApiRequestException;
import com.guaitilsoft.models.ProductReview;
import com.guaitilsoft.services.ProductReviewService;
import com.guaitilsoft.web.models.multimedia.MultimediaResponse;
import com.guaitilsoft.web.models.productReview.ProductReviewView;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.persistence.EntityNotFoundException;
import java.lang.reflect.Type;
import java.net.URI;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping(path = "/api/product-review")
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
    public ResponseEntity<List<ProductReviewView>> get(){
        Type listType = new TypeToken<List<ProductReviewView>>(){}.getType();
        List<ProductReviewView> productReviewViews = modelMapper.map(productReviewService.list(), listType);
        productReviewViews.forEach(this::addUrlToMultimedia);
        return  ResponseEntity.ok().body(productReviewViews);
    }

    @GetMapping("member-id/{id}")
    public ResponseEntity<List<ProductReviewView>> getByMemberId(@PathVariable Long id){
        Type listType = new TypeToken<List<ProductReviewView>>(){}.getType();
        List<ProductReviewView> productReviewViews = modelMapper.map(productReviewService.listByIdMember(id), listType);
        productReviewViews.forEach(this::addUrlToMultimedia);
        return  ResponseEntity.ok().body(productReviewViews);
    }

    @GetMapping("{id}")
    public ResponseEntity<ProductReviewView> getById(@PathVariable Long id) throws Exception {
        ProductReviewView productReview = modelMapper.map(productReviewService.get(id), ProductReviewView.class);
        this.addUrlToMultimedia(productReview);
        logger.info("Fetching Product with id {}", id);
        return ResponseEntity.ok().body(productReview);
    }

    @PostMapping
    public ResponseEntity<ProductReviewView> post(@RequestBody ProductReviewView productReviewRequest) throws Exception {
        ProductReview productReview = modelMapper.map(productReviewRequest, ProductReview.class);
        logger.info("Creating a product review");
        productReviewService.save(productReview);
        ProductReviewView productReviewResponse = modelMapper.map(productReview, ProductReviewView.class);
        this.addUrlToMultimedia(productReviewResponse);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(productReview.getId())
                .toUri();
        logger.info("Created product review : {}", productReviewResponse.getId());

        return ResponseEntity.created(location).body(productReviewResponse);
    }

    @PutMapping("{id}")
    public ResponseEntity<ProductReviewView> put(@PathVariable Long id, @RequestBody ProductReviewView productReviewRequest) throws Exception {
        if(!id.equals(productReviewRequest.getId())){
            throw new ApiRequestException("El id de la revision del producto: " + productReviewRequest.getId() + " es diferente al id del parametro: " + id);
        }
        ProductReview productReview = modelMapper.map(productReviewRequest, ProductReview.class);
        logger.info("Updating Product Review with id: {}", id);
        ProductReviewView productReviewResponse = modelMapper.map(productReviewService.update(id, productReview), ProductReviewView.class);
        this.addUrlToMultimedia(productReviewResponse);
        logger.info("Updated Product Review with id: {}", id);
        return ResponseEntity.ok().body(productReviewResponse);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<ProductReviewView> delete(@PathVariable Long id)throws Exception {
        ProductReviewView productReviewResponse = modelMapper.map(productReviewService.get(id), ProductReviewView.class);
        logger.info("Deleting Product Review with id {}", id);
        productReviewService.delete(id);
        logger.info("Deleted Product Review with id {}", id);
        return ResponseEntity.ok().body(productReviewResponse);
    }

    private void addUrlToMultimedia(ProductReviewView productView){
        productView.getMultimedia().forEach(m -> {
            String url = getUrlHost(m);
            m.setUrl(url);
        });
    }

    private String getUrlHost(MultimediaResponse multimediaResponse){
        String resourcePath = "/api/multimedia/load/";
        return ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(resourcePath)
                .path(multimediaResponse.getFileName())
                .toUriString();
    }
}
