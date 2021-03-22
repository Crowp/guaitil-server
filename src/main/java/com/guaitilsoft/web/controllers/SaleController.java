package com.guaitilsoft.web.controllers;

import com.guaitilsoft.models.Sale;
import com.guaitilsoft.services.report.ReportService;
import com.guaitilsoft.services.sale.SaleService;
import com.guaitilsoft.utils.Utils;
import com.guaitilsoft.web.models.sale.SaleRequest;
import com.guaitilsoft.web.models.sale.SaleResponse;
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
@RequestMapping(path = "/api/sales")
public class SaleController {
    public static final Logger logger = LoggerFactory.getLogger(SaleController.class);

    private final SaleService saleService;
    private final ReportService<Sale> reportService;

    @Autowired
    public SaleController(SaleService saleService, ReportService<Sale> reportService){
        this.saleService = saleService;
        this.reportService = reportService;
    }

    @GetMapping
    public ResponseEntity<List<SaleResponse>> get(){
        List<SaleResponse> sales = saleService.list();
        return ResponseEntity.ok().body(sales);
    }

    @GetMapping("{id}")
    public ResponseEntity<SaleResponse> getById(@PathVariable Long id) {
        SaleResponse sale = saleService.get(id);
        logger.info("Fetching Sale with {}", id);
        return ResponseEntity.ok().body(sale);
    }

    @GetMapping("/member-id/{id}")
    public ResponseEntity<List<SaleResponse>> getAllSaleByMemberId(@PathVariable Long id) {
        List<SaleResponse> sale = saleService.getAllSaleByMemberId(id);
        logger.info("Fetching Sale with member id {}", id);
        return ResponseEntity.ok().body(sale);
    }

    @PostMapping
    public ResponseEntity<SaleResponse> post(@RequestBody SaleRequest saleRequest) {
        logger.info("Creating sale");

        SaleResponse saleResponse = saleService.save(saleRequest);
        URI location = getUriResourceLocation(saleResponse.getId());

        logger.info("Created activity : {}", saleResponse.getId());
        return ResponseEntity.created(location).body(saleResponse);
    }

    @PutMapping("{id}")
    public ResponseEntity<SaleResponse> put(@PathVariable Long id, @RequestBody SaleRequest saleRequest) {
        logger.info("Updating Sale with id: {}", id);
        SaleResponse saleResponse = saleService.update(id, saleRequest);
        logger.info("Updated Sale with id: {}", id);
        return ResponseEntity.ok().body(saleResponse);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<SaleResponse> delete(@PathVariable Long id) {
        logger.info("Deleting Sale with id {}", id);
        SaleResponse saleResponse = saleService.get(id);
        saleService.delete(id);
        logger.info("Deleted Tour with id {}", id);
        return ResponseEntity.ok().body(saleResponse);
    }

    @GetMapping("/pdf-report")
    public ResponseEntity<byte[]> generatePDFReport() {
        String template = "classpath:\\reports\\productSaleReport\\ProductSalePdfReport.jrxml";
        List<Sale> sales = saleService.saleList();
        String time = Utils.getDateReport();

        byte[] bytes = reportService.exportPDF(sales, template);
        String nameFile = " Reporte Productos Vendidos "+time+".pdf";

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_PDF)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + nameFile + "\"")
                .body(bytes);
    }
    @GetMapping("/xlsx-report")
    public ResponseEntity<byte[]> generateXLSXReport(){
        String template = "classpath:\\reports\\productSaleReport\\ProductSaleXlsxReport.jrxml";
        List<Sale> sales = saleService.saleList();
        String time = Utils.getDateReport();

        byte[] bytes = reportService.exportXLSX(sales, template);
        String nameFile = "Reporte Productos Vendidos "+time+".xlsx";

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
