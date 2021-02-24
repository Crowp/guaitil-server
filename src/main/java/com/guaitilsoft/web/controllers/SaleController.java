package com.guaitilsoft.web.controllers;

import com.guaitilsoft.exceptions.ApiRequestException;
import com.guaitilsoft.models.Member;
import com.guaitilsoft.models.Sale;
import com.guaitilsoft.services.MemberService;
import com.guaitilsoft.services.ProductService;
import com.guaitilsoft.services.SaleService;
import com.guaitilsoft.web.models.sale.SaleView;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

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

    @Autowired
    public SaleController(SaleService saleService, ProductService productService, MemberService memberService, ModelMapper modelMapper){
        this.saleService = saleService;
        this.productService = productService;
        this.memberService = memberService;
        this.modelMapper = modelMapper;
    }

    @GetMapping
    public ResponseEntity<List<SaleView>> get(){
        Type lisType = new TypeToken<List<SaleView>>(){}.getType();
        List<SaleView> sales = modelMapper.map(saleService.list(), lisType);
        return ResponseEntity.ok().body(sales);
    }

    @GetMapping("{id}")
    public ResponseEntity<SaleView> getById(@PathVariable Long id) {
        SaleView sale = modelMapper.map(saleService.get(id), SaleView.class);
        logger.info("Fetching Sale with {}", id);
        return ResponseEntity.ok().body(sale);
    }

    @GetMapping("/member-id/{id}")
    public ResponseEntity<List<SaleView>> getAllSaleByMemberId(@PathVariable Long id) {
        Member member = memberService.get(id);
        Type listType = new TypeToken<List<SaleView>>(){}.getType();
        List<SaleView> sale = modelMapper.map(saleService.getAllSaleByMemberId(member.getId()), listType);
        logger.info("Fetching Sale with member id {}", id);
        return ResponseEntity.ok().body(sale);
    }

    @PostMapping
    public ResponseEntity<SaleView> post(@RequestBody SaleView saleRequest) {
        Sale sale = modelMapper.map(saleRequest, Sale.class);
        logger.info("Creating sale: {}", sale);
        sale.setProduct(productService.get(sale.getProduct().getId()));
        saleService.save(sale);
        SaleView saleResponse = modelMapper.map(sale, SaleView.class);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(sale.getId())
                .toUri();
        logger.info("Created activity : {}", saleResponse.getId());

        return ResponseEntity.created(location).body(saleResponse);
    }

    @PutMapping("{id}")
    public ResponseEntity<SaleView> put(@PathVariable Long id, @RequestBody SaleView saleRequest) {
        if(!id.equals(saleRequest.getId())){
            throw new ApiRequestException("El id de la venta: " + saleRequest.getId() + " es diferente al id del parametro: " + id);
        }
        Sale sale = modelMapper.map(saleRequest, Sale.class);
        logger.info("Updating Sale with id: {}", id);
        sale.setProduct(productService.get(sale.getProduct().getId()));
        saleService.update(id, sale);
        SaleView saleResponse = modelMapper.map(sale, SaleView.class);
        logger.info("Updated Sale with id: {}", id);
        return ResponseEntity.ok().body(saleResponse);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<SaleView> delete(@PathVariable Long id) {
        SaleView saleResponse = modelMapper.map(saleService.get(id), SaleView.class);
        logger.info("Deleting Sale with id {}", id);
        saleService.delete(id);
        logger.info("Deleted Tour with id {}", id);
        return ResponseEntity.ok().body(saleResponse);
    }
}
