package com.guaitilsoft.web.controllers;
import com.guaitilsoft.services.product.ProductService;
import com.guaitilsoft.services.report.ReportService;
import com.guaitilsoft.utils.Utils;
import com.guaitilsoft.web.models.product.ProductRequest;
import com.guaitilsoft.web.models.product.ProductResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping(path = "/api/products")
public class ProductController {
    public static final Logger logger = LoggerFactory.getLogger(ProductController.class);

    private final ProductService productService;
    private final ReportService<ProductResponse> reportService;

    @Autowired
    public ProductController(ProductService productService, ReportService<ProductResponse> reportService){
        this.productService = productService;
        this.reportService = reportService;
    }

    @GetMapping
    public ResponseEntity<List<ProductResponse>> get(){
        List<ProductResponse> products = productService.list();
        return  ResponseEntity.ok().body(products);
    }

    @GetMapping("{id}")
    public ResponseEntity<ProductResponse> getById(@PathVariable Long id) {
        ProductResponse product = productService.get(id);
        logger.info("Fetching Product with id {}", id);
        return ResponseEntity.ok().body(product);
    }

    @GetMapping("product-description/{id}")
    public ResponseEntity<ProductResponse> getByProductDescriptionId(@PathVariable Long id) {
        ProductResponse product = productService.getByProductDescriptionId(id);
        logger.info("Fetching Product with product description id {}", id);
        return ResponseEntity.ok().body(product);
    }

    @GetMapping("/local-id/{localId}")
    public ResponseEntity<List<ProductResponse>>getProductsByLocalId(@PathVariable Long localId) {
        List<ProductResponse> products = productService.getAllProductByLocalId(localId);
        logger.info("Fetching Product with local id {}", localId);
        return ResponseEntity.ok().body(products);
    }

    @GetMapping("/member-id/{memberId}")
    public ResponseEntity<List<ProductResponse>>getAllProductByMemberId(@PathVariable Long memberId) {
        List<ProductResponse> products = productService.getAllProductByMemberId(memberId);
        logger.info("Fetching Product with id {}", memberId);
        return ResponseEntity.ok().body(products);
    }

    @GetMapping("/state/local-id/{localId}")
    public ResponseEntity<List<ProductResponse>>getAllProductAcceptedByLocalId(@PathVariable Long localId) {
        List<ProductResponse> products = productService.getAllProductAcceptedByLocalId(localId);
        logger.info("Fetching Product with state accepted {}", localId);
        return ResponseEntity.ok().body(products);
    }

    @GetMapping("/show-product/{id}")
    public ResponseEntity<ProductResponse> putShowProduct(@PathVariable Long id) {
        logger.info("Updating Product with id: {}", id);
        ProductResponse productResponse = productService.updateShowProduct(id);
        logger.info("Updated Product with id: {}", id);
        return ResponseEntity.ok().body(productResponse);
    }

    @PostMapping
    public ResponseEntity<ProductResponse> post(@RequestBody ProductRequest productRequest) {
        logger.info("Creating product");

        ProductResponse productResponse = productService.save(productRequest);
        URI location = getUriResourceLocation(productResponse.getId());

        logger.info("Created product : {}", productResponse.getId());
        return ResponseEntity.created(location).body(productResponse);
    }

    @PutMapping("{id}")
    public ResponseEntity<ProductResponse> put(@PathVariable Long id, @RequestBody ProductRequest productRequest) {
        logger.info("Updating Product with id: {}", id);
        ProductResponse productResponse = productService.update(id, productRequest);
        logger.info("Updated Product with id: {}", id);
        return ResponseEntity.ok().body(productResponse);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<ProductResponse> delete(@PathVariable Long id) {
        logger.info("Deleting Product with id {}", id);
        ProductResponse productResponse = productService.get(id);
        productService.delete(id);
        logger.info("Deleted Product with id {}", id);
        return ResponseEntity.ok().body(productResponse);
    }

    @DeleteMapping("/delete/multimedia-by-id")
    public ResponseEntity<ProductResponse> deleteMultimediaById(@RequestParam Long id,
                                                               @RequestParam Long idMultimedia) {
        logger.info("Deleting Product with id {}", id);
        ProductResponse productResponse = productService.deleteMultimediaById(id, idMultimedia);
        logger.info("Deleted Product with id {}", id);
        return ResponseEntity.ok().body(productResponse);
    }

    @GetMapping("/pdf-report")
    public ResponseEntity<byte[]> generatePDFReport() {
        String template = "productReports/productPdfReport.jrxml";
        List<ProductResponse> productResponses = productService.list();
        String time = Utils.getDateReport();

        byte[] bytes = reportService.exportPDF(productResponses, template);
        String nameFile = "Reporte Productos "+time+".pdf";

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_PDF)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + nameFile + "\"")
                .body(bytes);
    }

    @GetMapping("/xlsx-report")
    public ResponseEntity<byte[]> generateXLSXReport() {
        String template = "productReports/productXlsxReport.jrxml";
        List<ProductResponse> productResponses = productService.list();
        String time = Utils.getDateReport();

        byte[] bytes = reportService.exportXLSX(productResponses, template);
        String nameFile = "Reporte Productos "+time+".xlsx";

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType("application/x-xlsx"))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + nameFile + "\"")
                .body(bytes);
    }

    private URI getUriResourceLocation(Long id) {
        return ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(id)
                .toUri();
    }
}
