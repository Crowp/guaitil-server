package com.guaitilsoft.web.controllers;

import com.guaitilsoft.exceptions.ApiRequestException;
import com.guaitilsoft.models.Product;
import com.guaitilsoft.services.ProductService;
import com.guaitilsoft.web.models.product.ProductView;
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
@RequestMapping(path = "/api/product")
public class ProductController {
    public static final Logger logger = LoggerFactory.getLogger(TourController.class);

    private ProductService productService;
    private ModelMapper modelMapper;

    @Autowired
    public ProductController(ProductService productService, ModelMapper modelMapper){
        this.productService =productService;
        this.modelMapper = modelMapper;
    }

    @GetMapping
    public ResponseEntity<List<Product>> get(){
        return  ResponseEntity.ok().body(productService.list());
    }

    @GetMapping("{id}")
    public ResponseEntity<Product> getById(@PathVariable Long id) throws Exception, EntityNotFoundException  {
        logger.info("Fetching Product with id {}", id);
        return ResponseEntity.ok().body(productService.get(id));
    }

    @PostMapping
    public ResponseEntity<ProductView> post(@RequestBody ProductView productRequest) throws Exception, EntityNotFoundException  {
        Product product = modelMapper.map(productRequest, Product.class);
        logger.info("Creating product: {}", product);
        productService.save(product);
        ProductView productResponse = modelMapper.map(product, ProductView.class);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(product.getId())
                .toUri();
        logger.info("Created activity : {}", productResponse.getId());

        return ResponseEntity.created(location).body(productResponse);
    }

    @PutMapping("{id}")
    public ResponseEntity<ProductView> put(@PathVariable Long id, @RequestBody ProductView productRequest) throws Exception, EntityNotFoundException  {
        if(!id.equals(productRequest.getId())){
            throw new ApiRequestException("El id del producto: " + productRequest.getId() + " es diferente al id del parametro: " + id);
        }
        Product product = modelMapper.map(productRequest, Product.class);
        logger.info("Updating Product with id: {}", id);
        productService.update(id, product);
        ProductView productResponse = modelMapper.map(product, ProductView.class);
        logger.info("Updated Product with id: {}", id);
        return ResponseEntity.ok().body(productResponse);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<ProductView> delete(@PathVariable Long id)throws Exception, EntityNotFoundException {
        ProductView productResponse = modelMapper.map(productService.get(id), ProductView.class);
        logger.info("Deleting Product with id {}", id);
        productService.delete(id);
        logger.info("Deleted Product with id {}", id);
        return ResponseEntity.ok().body(productResponse);
    }
}
