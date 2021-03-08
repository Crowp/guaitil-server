package com.guaitilsoft.services.product;

import com.guaitilsoft.models.Local;
import com.guaitilsoft.models.Multimedia;
import com.guaitilsoft.models.Product;
import com.guaitilsoft.services.MultimediaService;
import com.guaitilsoft.services.local.LocalRepositoryService;
import com.guaitilsoft.web.models.multimedia.MultimediaResponse;
import com.guaitilsoft.web.models.product.ProductRequest;
import com.guaitilsoft.web.models.product.ProductResponse;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImp implements ProductService{

    private final ProductRepositoryService productRepositoryService;
    private final MultimediaService multimediaService;
    private final ModelMapper modelMapper;
    private final LocalRepositoryService localRepositoryService;

    @Autowired
    public ProductServiceImp(@Qualifier("ProductRepositoryServiceValidation") ProductRepositoryService productRepositoryService,
                             MultimediaService multimediaService, 
                             ModelMapper modelMapper,
                             @Qualifier("LocalRepositoryServiceValidation") LocalRepositoryService localRepositoryService) {
        this.productRepositoryService = productRepositoryService;
        this.multimediaService = multimediaService;
        this.modelMapper = modelMapper;
        this.localRepositoryService = localRepositoryService;
    }

    @Override
    public List<ProductResponse> list() {
        return this.parseToProductResponseList(productRepositoryService.list());
    }

    @Override
    public ProductResponse get(Long id) {
        return this.parseToProductResponse(productRepositoryService.get(id));
    }

    @Override
    public ProductResponse getByProductDescriptionId(Long id) {
        return this.parseToProductResponse(productRepositoryService.getByProductDescriptionId(id));
    }

    @Override
    public ProductResponse save(ProductRequest entity) {
        Product product = this.parseToProduct(entity);
        Local local = loadFullLocal(product.getLocal().getId());
        product.setLocal(local);
        return onSaveProduct(product);
    }

    private ProductResponse onSaveProduct(Product productToStore){
        Product product = productRepositoryService.save(productToStore);
        return modelMapper.map(product, ProductResponse.class);
    }

    @Override
    public ProductResponse update(Long id, ProductRequest entity) {
        Product product = this.parseToProduct(entity);
        loadMultimedia(product.getMultimedia());
        return this.parseToProductResponse(productRepositoryService.update(id, product));
    }

    @Override
    public void delete(Long id) {
        productRepositoryService.delete(id);
    }

    @Override
    public List<ProductResponse> getAllProductByLocalId(Long id) {
        return this.parseToProductResponseList(productRepositoryService.getAllProductByLocalId(id));
    }

    @Override
    public List<ProductResponse> getAllProductByMemberId(Long id) {
        return this.parseToProductResponseList(productRepositoryService.getAllProductByMemberId(id));
    }

    @Override
    public List<ProductResponse> getAllProductAcceptedByLocalId(Long id) {
        return this.parseToProductResponseList(productRepositoryService.getAllProductAcceptedByLocalId(id));
    }

    @Override
    public ProductResponse deleteMultimediaById(Long id, Long idMultimedia) {
        Product product = productRepositoryService.get(id);
        Optional<Multimedia> multimediaToDelete = product.getMultimedia()
                .stream()
                .filter(m -> m.getId().equals(idMultimedia))
                .findFirst();

        multimediaToDelete.ifPresent(product::removeMultimediaById);
        productRepositoryService.save(product);
        return this.parseToProductResponse(product);
    }

    private List<ProductResponse>  parseToProductResponseList(List<Product> products){
        Type lisType = new TypeToken<List<ProductResponse>>(){}.getType();
        List<ProductResponse> productResponses = modelMapper.map(products, lisType);
        productResponses.forEach(p -> addUrlToMultimedia(p.getMultimedia()));
        return productResponses;
    }

    private ProductResponse parseToProductResponse(Product product){
        ProductResponse productResponse = modelMapper.map(product, ProductResponse.class);
        addUrlToMultimedia(productResponse.getMultimedia());
        return productResponse;
    }

    private Product parseToProduct(ProductRequest productRequest){
        return modelMapper.map(productRequest, Product.class);
    }

    private void loadMultimedia(List<Multimedia> multimediaList) {
        List<Multimedia> multimediaLoaded = new ArrayList<>();
        multimediaList.forEach(media -> multimediaLoaded.add(multimediaService.get(media.getId())));
        multimediaList.clear();
        multimediaList.addAll(multimediaLoaded);
    }

    private void addUrlToMultimedia(List<MultimediaResponse> multimedia){
        multimedia.forEach(m -> m.setUrl(getUrlHost(m)));
    }

    private static String getUrlHost(MultimediaResponse multimediaResponse){
        String resourcePath = "/api/multimedia/load/";
        return ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(resourcePath)
                .path(multimediaResponse.getFileName())
                .toUriString();
    }
    
    private Local loadFullLocal(Long id){
        return this.localRepositoryService.get(id);
    }
}
