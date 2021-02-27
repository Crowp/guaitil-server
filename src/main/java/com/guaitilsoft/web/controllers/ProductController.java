package com.guaitilsoft.web.controllers;

import com.guaitilsoft.exceptions.ApiRequestException;
import com.guaitilsoft.models.*;
import com.guaitilsoft.services.*;
import com.guaitilsoft.utils.Utils;
import com.guaitilsoft.web.models.multimedia.MultimediaResponse;
import com.guaitilsoft.web.models.product.GetProduct;
import com.guaitilsoft.web.models.product.ProductView;
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
import java.util.ArrayList;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping(path = "/api/products")
public class ProductController {
    public static final Logger logger = LoggerFactory.getLogger(ProductController.class);

    private final ProductService productService;
    private final ModelMapper modelMapper;
    private final Utils utils;

    @Autowired
    public ProductController(ProductService productService,
                             ModelMapper modelMapper,
                             Utils utils){
        this.productService = productService;
        this.modelMapper = modelMapper;
        this.utils = utils;
    }

    @GetMapping
    public ResponseEntity<List<GetProduct>> get(){
        Type listType = new TypeToken<List<GetProduct>>(){}.getType();
        List<GetProduct> products = modelMapper.map(productService.list(), listType);
        products.forEach(p -> this.utils.addUrlToMultimedia(p.getMultimedia()));
        return  ResponseEntity.ok().body(products);
    }

    @GetMapping("{id}")
    public ResponseEntity<GetProduct> getById(@PathVariable Long id) {
        GetProduct product = modelMapper.map(productService.get(id), GetProduct.class);
        this.utils.addUrlToMultimedia(product.getMultimedia());
        logger.info("Fetching Product with id {}", id);
        return ResponseEntity.ok().body(product);
    }

    @GetMapping("/local-id/{localId}")
    public ResponseEntity<List<GetProduct>>getProductsByLocalId(@PathVariable Long localId) {
        Type listType = new TypeToken<List<ProductView>>(){}.getType();
        List<GetProduct> products = modelMapper.map(productService.getAllProductByLocalId(localId), listType);
        products.forEach(p -> this.utils.addUrlToMultimedia(p.getMultimedia()));
        logger.info("Fetching Product with local id {}", localId);
        return ResponseEntity.ok().body(products);
    }

    @GetMapping("/member-id/{memberId}")
    public ResponseEntity<List<GetProduct>>getAllProductByMemberId(@PathVariable Long memberId) {
        Type listType = new TypeToken<List<ProductView>>(){}.getType();
        List<GetProduct> products = modelMapper.map(productService.getAllProductByMemberId(memberId), listType);
        products.forEach(p -> this.utils.addUrlToMultimedia(p.getMultimedia()));
        logger.info("Fetching Product with id {}", memberId);
        return ResponseEntity.ok().body(products);
    }

    @GetMapping("/state/local-id/{localId}")
    public ResponseEntity<List<GetProduct>>getAllProductAcceptedByLocalId(@PathVariable Long localId) {
        Type listType = new TypeToken<List<ProductView>>(){}.getType();
        List<GetProduct> products = modelMapper.map(productService.getAllProductAcceptedByLocalId(localId), listType);
        products.forEach(p -> this.utils.addUrlToMultimedia(p.getMultimedia()));
        logger.info("Fetching Product with state accepted {}", localId);
        return ResponseEntity.ok().body(products);
    }

    @PostMapping
    public ResponseEntity<ProductView> post(@RequestBody ProductView productRequest) {
        Product product = modelMapper.map(productRequest, Product.class);
        logger.info("Creating product");

        this.utils.loadMultimedia(product.getMultimedia());

        productService.save(product);
        ProductView productResponse = modelMapper.map(product, ProductView.class);
        this.utils.addUrlToMultimedia(productResponse.getMultimedia());

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(product.getId())
                .toUri();
        logger.info("Created product : {}", productResponse.getId());

        return ResponseEntity.created(location).body(productResponse);
    }

    @PutMapping("{id}")
    public ResponseEntity<ProductView> put(@PathVariable Long id, @RequestBody ProductView productRequest) {
        if(!id.equals(productRequest.getId())){
            throw new ApiRequestException("El id del producto: " + productRequest.getId() + " es diferente al id del parametro: " + id);
        }
        Product product = modelMapper.map(productRequest, Product.class);
        logger.info("Updating Product with id: {}", id);
        this.utils.loadMultimedia(product.getMultimedia());
        productService.update(id, product);
        ProductView productResponse = modelMapper.map(product, ProductView.class);
        this.utils.addUrlToMultimedia(productResponse.getMultimedia());
        logger.info("Updated Product with id: {}", id);
        return ResponseEntity.ok().body(productResponse);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<ProductView> delete(@PathVariable Long id) {
        ProductView productResponse = modelMapper.map(productService.get(id), ProductView.class);
        logger.info("Deleting Product with id {}", id);
        productService.delete(id);
        logger.info("Deleted Product with id {}", id);
        return ResponseEntity.ok().body(productResponse);
    }

    @DeleteMapping("/delete/multimedia-by-id")
    public ResponseEntity<ProductView> deleteMultimediaById(@RequestParam Long id,
                                                          @RequestParam Long idMultimedia) {
        logger.info("Deleting Product with id {}", id);
        ProductView productResponse = modelMapper.map(
                productService.deleteMultimediaById(id, idMultimedia), ProductView.class);
        this.utils.addUrlToMultimedia(productResponse.getMultimedia());
        logger.info("Deleted Product with id {}", id);
        return ResponseEntity.ok().body(productResponse);
    }

}
