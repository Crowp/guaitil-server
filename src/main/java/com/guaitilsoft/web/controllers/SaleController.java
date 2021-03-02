package com.guaitilsoft.web.controllers;

import com.guaitilsoft.exceptions.ApiRequestException;
import com.guaitilsoft.models.Member;
import com.guaitilsoft.models.Sale;
import com.guaitilsoft.services.MemberService;
import com.guaitilsoft.services.ProductService;
import com.guaitilsoft.services.ReportService;
import com.guaitilsoft.services.SaleService;
import com.guaitilsoft.web.models.sale.SaleResponse;
import com.guaitilsoft.web.models.sale.SaleRequest;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Type;
import java.net.URI;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping(path = "/api/sales")
public class SaleController {
    public static final Logger logger = LoggerFactory.getLogger(SaleController.class);

    private final SaleService saleService;
    private final ProductService productService;
    private final MemberService memberService;
    private final ModelMapper modelMapper;
    private final ReportService<Sale> reportService;

    @Autowired
    public SaleController(SaleService saleService, ProductService productService, MemberService memberService, ModelMapper modelMapper, ReportService<Sale> reportService){
        this.saleService = saleService;
        this.productService = productService;
        this.memberService = memberService;
        this.modelMapper = modelMapper;
        this.reportService = reportService;
    }

    @GetMapping("/pdf-report")
    public ResponseEntity<String> generatePDFReport(HttpServletResponse response) throws IOException {
        String template = "classpath:\\reports\\productReport\\ProductSalePdfReport.jrxml";
        List<Sale> sales = saleService.list();

        response.setContentType("application/x-download");
        response.setHeader("Content-Disposition", "attachment; filename=\"Reporte de Ventas de Productos.pdf\"");
        OutputStream out = response.getOutputStream();
        reportService.exportPDF(out, sales, template);

        return ResponseEntity.ok().body("Se generó el reporte");
    }

    @GetMapping
    public ResponseEntity<List<SaleResponse>> get(){
        Type lisType = new TypeToken<List<SaleResponse>>(){}.getType();
        List<SaleResponse> sales = modelMapper.map(saleService.list(), lisType);
        return ResponseEntity.ok().body(sales);
    }

    @GetMapping("{id}")
    public ResponseEntity<SaleResponse> getById(@PathVariable Long id) {
        SaleResponse sale = modelMapper.map(saleService.get(id), SaleResponse.class);
        logger.info("Fetching Sale with {}", id);
        return ResponseEntity.ok().body(sale);
    }

    @GetMapping("/member-id/{id}")
    public ResponseEntity<List<SaleResponse>> getAllSaleByMemberId(@PathVariable Long id) {
        Member member = memberService.get(id);
        Type listType = new TypeToken<List<SaleResponse>>(){}.getType();
        List<SaleResponse> sale = modelMapper.map(saleService.getAllSaleByMemberId(member.getMemberId()), listType);
        logger.info("Fetching Sale with member id {}", id);
        return ResponseEntity.ok().body(sale);
    }

    @PostMapping
    public ResponseEntity<SaleRequest> post(@RequestBody SaleRequest saleRequest) {
        Sale sale = modelMapper.map(saleRequest, Sale.class);
        logger.info("Creating sale: {}", sale);
        Long productId = saleRequest.getProduct().getId();
        sale.setProductDescription(productService.get(productId).getProductDescription());
        saleService.save(sale);
        SaleRequest saleResponse = modelMapper.map(sale, SaleRequest.class);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(sale.getId())
                .toUri();
        logger.info("Created activity : {}", saleResponse.getId());

        return ResponseEntity.created(location).body(saleResponse);
    }

    @PutMapping("{id}")
    public ResponseEntity<SaleRequest> put(@PathVariable Long id, @RequestBody SaleRequest saleRequest) {
        if(!id.equals(saleRequest.getId())){
            throw new ApiRequestException("El id de la venta: " + saleRequest.getId() + " es diferente al id del parametro: " + id);
        }
        Sale sale = modelMapper.map(saleRequest, Sale.class);
        logger.info("Updating Sale with id: {}", id);
        Long productId = saleRequest.getProduct().getId();
        sale.setProductDescription(productService.get(productId).getProductDescription());
        saleService.update(id, sale);
        SaleRequest saleResponse = modelMapper.map(sale, SaleRequest.class);
        logger.info("Updated Sale with id: {}", id);
        return ResponseEntity.ok().body(saleResponse);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<SaleRequest> delete(@PathVariable Long id) {
        SaleRequest saleResponse = modelMapper.map(saleService.get(id), SaleRequest.class);
        logger.info("Deleting Sale with id {}", id);
        saleService.delete(id);
        logger.info("Deleted Tour with id {}", id);
        return ResponseEntity.ok().body(saleResponse);
    }
}
