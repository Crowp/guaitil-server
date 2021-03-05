package com.guaitilsoft.web.controllers;

import com.guaitilsoft.exceptions.ApiRequestException;
import com.guaitilsoft.models.*;
import com.guaitilsoft.services.*;
import com.guaitilsoft.utils.Utils;
import com.guaitilsoft.web.models.product.ProductLazyResponse;
import com.guaitilsoft.web.models.product.ProductResponse;
import com.guaitilsoft.web.models.product.ProductRequest;
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
    public ResponseEntity<List<ProductResponse>> get(){
        Type listType = new TypeToken<List<ProductResponse>>(){}.getType();
        List<ProductResponse> products = modelMapper.map(productService.list(), listType);
        products.forEach(p -> this.utils.addUrlToMultimedia(p.getMultimedia()));
        return  ResponseEntity.ok().body(products);
    }

    @GetMapping("{id}")
    public ResponseEntity<ProductResponse> getById(@PathVariable Long id) {
        ProductResponse product = modelMapper.map(productService.get(id), ProductResponse.class);
        this.utils.addUrlToMultimedia(product.getMultimedia());
        logger.info("Fetching Product with id {}", id);
        return ResponseEntity.ok().body(product);
    }

    @GetMapping("/local-id/{localId}")
    public ResponseEntity<List<ProductResponse>>getProductsByLocalId(@PathVariable Long localId) {
        Type listType = new TypeToken<List<ProductRequest>>(){}.getType();
        List<ProductResponse> products = modelMapper.map(productService.getAllProductByLocalId(localId), listType);
        products.forEach(p -> this.utils.addUrlToMultimedia(p.getMultimedia()));
        logger.info("Fetching Product with local id {}", localId);
        return ResponseEntity.ok().body(products);
    }

    @GetMapping("/member-id/{memberId}")
    public ResponseEntity<List<ProductResponse>>getAllProductByMemberId(@PathVariable Long memberId) {
        Type listType = new TypeToken<List<ProductRequest>>(){}.getType();
        List<ProductResponse> products = modelMapper.map(productService.getAllProductByMemberId(memberId), listType);
        products.forEach(p -> this.utils.addUrlToMultimedia(p.getMultimedia()));
        logger.info("Fetching Product with id {}", memberId);
        return ResponseEntity.ok().body(products);
    }

    @GetMapping("/state/local-id/{localId}")
    public ResponseEntity<List<ProductResponse>>getAllProductAcceptedByLocalId(@PathVariable Long localId) {
        Type listType = new TypeToken<List<ProductRequest>>(){}.getType();
        List<ProductResponse> products = modelMapper.map(productService.getAllProductAcceptedByLocalId(localId), listType);
        products.forEach(p -> this.utils.addUrlToMultimedia(p.getMultimedia()));
        logger.info("Fetching Product with state accepted {}", localId);
        return ResponseEntity.ok().body(products);
    }

    @PostMapping
    public ResponseEntity<ProductRequest> post(@RequestBody ProductRequest productRequest) {
        Product product = modelMapper.map(productRequest, Product.class);
        logger.info("Creating product");

        this.utils.loadMultimedia(product.getMultimedia());

        Local local = this.utils.loadFullLocal(product.getLocal().getId());
        product.setLocal(local);

        productService.save(product);

        ProductRequest productResponse = modelMapper.map(product, ProductRequest.class);

        this.utils.addUrlToMultimedia(productResponse.getMultimedia());
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(product.getId())
                .toUri();
        logger.info("Created product : {}", productResponse.getId());

        return ResponseEntity.created(location).body(productResponse);
    }

    @PutMapping("{id}")
    public ResponseEntity<ProductLazyResponse> put(@PathVariable Long id, @RequestBody ProductLazyResponse productRequest) {
        if(!id.equals(productRequest.getId())){
            throw new ApiRequestException("El id del producto: " + productRequest.getId() + " es diferente al id del parametro: " + id);
        }
        Product product = modelMapper.map(productRequest, Product.class);
        logger.info("Updating Product with id: {}", id);
        this.utils.loadMultimedia(product.getMultimedia());
        productService.update(id, product);
        ProductLazyResponse productResponse = modelMapper.map(product, ProductLazyResponse.class);
        this.utils.addUrlToMultimedia(productResponse.getMultimedia());
        logger.info("Updated Product with id: {}", id);
        return ResponseEntity.ok().body(productResponse);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<ProductRequest> delete(@PathVariable Long id) {
        ProductRequest productResponse = modelMapper.map(productService.get(id), ProductRequest.class);
        logger.info("Deleting Product with id {}", id);
        productService.delete(id);
        logger.info("Deleted Product with id {}", id);
        return ResponseEntity.ok().body(productResponse);
    }

    @DeleteMapping("/delete/multimedia-by-id")
    public ResponseEntity<ProductRequest> deleteMultimediaById(@RequestParam Long id,
                                                               @RequestParam Long idMultimedia) {
        logger.info("Deleting Product with id {}", id);
        ProductRequest productResponse = modelMapper.map(
                productService.deleteMultimediaById(id, idMultimedia), ProductRequest.class);
        this.utils.addUrlToMultimedia(productResponse.getMultimedia());
        logger.info("Deleted Product with id {}", id);
        return ResponseEntity.ok().body(productResponse);
    }

}
