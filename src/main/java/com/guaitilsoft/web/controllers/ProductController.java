package com.guaitilsoft.web.controllers;

import com.guaitilsoft.models.Product;
import com.guaitilsoft.services.ProductService;
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
@RequestMapping(path = "/api/product")
public class ProductController {
    public static final Logger logger = LoggerFactory.getLogger(TourController.class);

    private ProductService productService;


    @Autowired
    public ProductController(ProductService productService){this.productService =productService;}

    @GetMapping
    public ResponseEntity<List<Product>> get(){
        return  ResponseEntity.ok().body(productService.list());
    }

    @GetMapping("{id}")
    public ResponseEntity<Product> getById(@PathVariable Long id) throws Exception, EntityNotFoundException {
        logger.info("Fetching Product with id {}", id);
        return ResponseEntity.ok().body(productService.get(id));
    }

    @PostMapping
    public ResponseEntity<Product> post(@RequestBody Product product) throws  Exception{
        logger.info("Creating product: {}", product);
        productService.save(product);


        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(product.getId())
                .toUri();
        logger.info("Created product: {}", product);

        return ResponseEntity.created(location).body(product);
    }

    @PutMapping("{id}")
    public ResponseEntity<Object> put(@PathVariable Long id, @RequestBody Product productRequest) throws Exception, EntityNotFoundException {
        logger.info("Updating Product with id: {}", id);
        productService.update(id, productRequest);
        logger.info("Updated Product with id: {}", id);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Object> delete(@PathVariable Long id) throws Exception, EntityNotFoundException{
        logger.info("Deleting Product with id {}", id);
        productService.delete(id);
        logger.info("Deleted Product with id {}", id);
        return ResponseEntity.ok().build();
    }
}
