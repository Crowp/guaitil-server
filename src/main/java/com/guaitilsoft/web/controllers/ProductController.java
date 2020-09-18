package com.guaitilsoft.web.controllers;

import com.guaitilsoft.exceptions.ApiRequestException;
import com.guaitilsoft.models.*;
import com.guaitilsoft.services.*;
import com.guaitilsoft.web.models.local.LocalView;
import com.guaitilsoft.web.models.multimedia.MultimediaResponse;
import com.guaitilsoft.web.models.product.ProductView;
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
import java.util.ArrayList;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping(path = "/api/product")
public class ProductController {
    public static final Logger logger = LoggerFactory.getLogger(TourController.class);

    private ProductService productService;
    private LocalService localService;
    private MemberService memberService;
    private SaleService saleService;
    private MultimediaService multimediaService;
    private ModelMapper modelMapper;

    @Autowired
    public ProductController(ProductService productService,
                             MultimediaService multimediaService,
                             LocalService localService,
                             MemberService memberService,
                             ModelMapper modelMapper){
        this.productService = productService;
        this.multimediaService = multimediaService;
        this.localService = localService;
        this.modelMapper = modelMapper;
        this.memberService = memberService;
    }

    @GetMapping
    public ResponseEntity<List<ProductView>> get(){
        Type listType = new TypeToken<List<ProductView>>(){}.getType();
        List<ProductView> products = modelMapper.map(productService.list(), listType);
        products.forEach(this::addUrlToMultimedia);
        return  ResponseEntity.ok().body(products);
    }

    @GetMapping("{id}")
    public ResponseEntity<ProductView> getById(@PathVariable Long id) throws Exception {
        ProductView product = modelMapper.map(productService.get(id), ProductView.class);
        addUrlToMultimedia(product);
        logger.info("Fetching Product with id {}", id);
        return ResponseEntity.ok().body(product);
    }

    @GetMapping("/local-id/{id}")
    public ResponseEntity<List<ProductView>>getProductsByLocalId(@PathVariable Long id) throws Exception {
        Local local = localService.get(id);
        Type listType = new TypeToken<List<ProductView>>(){}.getType();
        List<ProductView> products = modelMapper.map(productService.getAllProductByLocalId(local.getId()), listType);
        products.forEach(this::addUrlToMultimedia);
        logger.info("Fetching Product with local id {}", id);
        return ResponseEntity.ok().body(products);
    }

    @GetMapping("/member-id/{id}")
    public ResponseEntity<List<ProductView>>getAllProductByMemberId(@PathVariable Long id) throws Exception {
        Member member = memberService.get(id);
        Type listType = new TypeToken<List<ProductView>>(){}.getType();
        List<ProductView> products = modelMapper.map(productService.getAllProductByMemberId(member.getId()), listType);
        products.forEach(this::addUrlToMultimedia);
        logger.info("Fetching Product with id {}", id);
        return ResponseEntity.ok().body(products);
    }
    @GetMapping("/state/local-id/{id}")
    public ResponseEntity<List<ProductView>>getAllProductAcceptedByLocalId(@PathVariable Long id) throws Exception, EntityNotFoundException  {
        Local local = localService.get(id);
        Type listType = new TypeToken<List<ProductView>>(){}.getType();
        List<ProductView> products = modelMapper.map(productService.getAllProductByMemberId(local.getId()), listType);
        products.forEach(this::addUrlToMultimedia);
        logger.info("Fetching Product with state accepted {}", id);
        return ResponseEntity.ok().body(products);
    }

    @PostMapping
    public ResponseEntity<ProductView> post(@RequestBody ProductView productRequest) throws Exception {
        Product product = modelMapper.map(productRequest, Product.class);
        logger.info("Creating product");

        loadMultimedia(product);
        productService.save(product);
        ProductView productResponse = modelMapper.map(product, ProductView.class);
        addUrlToMultimedia(productResponse);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(product.getId())
                .toUri();
        logger.info("Created product : {}", productResponse.getId());

        return ResponseEntity.created(location).body(productResponse);
    }

    @PutMapping("{id}")
    public ResponseEntity<ProductView> put(@PathVariable Long id, @RequestBody ProductView productRequest) throws Exception {
        if(!id.equals(productRequest.getId())){
            throw new ApiRequestException("El id del producto: " + productRequest.getId() + " es diferente al id del parametro: " + id);
        }
        Product product = modelMapper.map(productRequest, Product.class);
        logger.info("Updating Product with id: {}", id);
        loadMultimedia(product);
        productService.update(id, product);
        ProductView productResponse = modelMapper.map(product, ProductView.class);
        addUrlToMultimedia(productResponse);
        logger.info("Updated Product with id: {}", id);
        return ResponseEntity.ok().body(productResponse);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<ProductView> delete(@PathVariable Long id)throws Exception {
        ProductView productResponse = modelMapper.map(productService.get(id), ProductView.class);
        logger.info("Deleting Product with id {}", id);
        productService.delete(id);
        logger.info("Deleted Product with id {}", id);
        return ResponseEntity.ok().body(productResponse);
    }

    @DeleteMapping("delete-multimedia-by-id")
    public ResponseEntity<ProductView> deleteMultimediaById(@RequestParam Long id,
                                                          @RequestParam Long idMultimedia) throws Exception {
        logger.info("Deleting Product with id {}", id);
        ProductView productResponse = modelMapper.map(
                productService.deleteMultimediaById(id, idMultimedia), ProductView.class);
        addUrlToMultimedia(productResponse);
        logger.info("Deleted Product with id {}", id);
        return ResponseEntity.ok().body(productResponse);
    }


    private void addUrlToMultimedia(ProductView productView){
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

    private void loadMultimedia(Product product){
        if(product.getMultimedia().size() > 0){
            List<Multimedia> multimediaList = new ArrayList<>();
            product.getMultimedia().forEach(media -> {
                Multimedia multimedia = multimediaService.get(media.getId());
                multimediaList.add(multimedia);
            });
            product.setMultimedia(multimediaList);
        }
    }
}
