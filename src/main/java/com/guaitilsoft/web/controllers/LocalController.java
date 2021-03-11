package com.guaitilsoft.web.controllers;

import com.guaitilsoft.models.Local;
import com.guaitilsoft.models.constant.LocalType;
import com.guaitilsoft.services.report.ReportService;
import com.guaitilsoft.services.local.LocalService;
import com.guaitilsoft.web.models.local.LocalRequest;
import com.guaitilsoft.web.models.local.LocalResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping(path = "/api/locals")
public class LocalController {

    public static final Logger logger = LoggerFactory.getLogger(LocalController.class);

    private final LocalService localService;
    private final ReportService<Local> reportService;

    @Autowired
    public LocalController(LocalService localService,
                           ReportService<Local> reportService) {
        this.localService = localService;
        this.reportService = reportService;
    }

    @GetMapping
    public ResponseEntity<List<LocalResponse>> get() {
        List<LocalResponse> locals = localService.list();
        return ResponseEntity.ok().body(locals);
    }

    @GetMapping("/local-types/{localType}")
    public ResponseEntity<List<LocalResponse>> getLocalsByLocalType(@PathVariable LocalType localType) {
        List<LocalResponse> locals = localService.getLocalByLocalType(localType);
        return ResponseEntity.ok().body(locals);
    }

    @GetMapping("{id}")
    public ResponseEntity<LocalResponse> getById(@PathVariable Long id) {
        LocalResponse local = localService.get(id);
        logger.info("Fetching Local with id: {}", id);
        return ResponseEntity.ok().body(local);
    }

    @GetMapping("/member-id/{memberId}")
    public ResponseEntity<List<LocalResponse>> getLocalsByMemberId(@PathVariable Long memberId) {
        List<LocalResponse> locals = localService.getAllLocalByIdMember(memberId);
        logger.info("Fetching Local with id: {}", memberId);
        return ResponseEntity.ok().body(locals);
    }

    @PostMapping
    public ResponseEntity<LocalResponse> post(@RequestBody LocalRequest localRequest) {
        logger.info("Creating local");

        LocalResponse localResponse = localService.save(localRequest);
        URI location = getUriResourceLocation(localResponse.getId());

        logger.info("Created local: {}", localResponse.getId());
        return ResponseEntity.created(location).body(localResponse);
    }

    @PutMapping("{id}")
    public ResponseEntity<LocalResponse> put(@PathVariable Long id, @RequestBody LocalRequest localRequest) {
        logger.info("Updating Local with id: {}", id);
        LocalResponse localResponse = localService.update(id, localRequest);
        logger.info("Updated Local with id: {}", id);
        return ResponseEntity.ok().body(localResponse);
    }

    @DeleteMapping("{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN') AND hasRole('ROLE_SUPER_ADMIN')")
    public ResponseEntity<LocalResponse> delete(@PathVariable Long id) {
        logger.info("Deleting Local with id: {}", id);
        LocalResponse localResponse = localService.get(id);
        localService.delete(id);
        logger.info("Deleted Local with id: {}", id);
        return ResponseEntity.ok().body(localResponse);
    }

    @DeleteMapping("delete-multimedia-by-id")
    public ResponseEntity<LocalResponse> deleteMultimediaById(@RequestParam Long id,
                                                              @RequestParam Long idMultimedia) {
        logger.info("Deleting Local Multimedia with id {}", id);
        LocalResponse localResponse = localService.deleteMultimediaById(id, idMultimedia);
        logger.info("Deleting Local Multimedia with id {}", id);
        return ResponseEntity.ok().body(localResponse);
    }

    @GetMapping("/pdf-report")
    public ResponseEntity<String> generatePDFReport(HttpServletResponse response) throws IOException {
        String template = "classpath:\\reports\\localReports\\localPDFReport.jrxml";
        List<Local> locals = localService.localList();

        response.setContentType("application/x-download");
        response.setHeader("Content-Disposition", "attachment; filename=\"Reporte de locales.pdf\"");
        OutputStream out = response.getOutputStream();
        reportService.exportPDF(out, locals, template);

        return ResponseEntity.ok().body("Se generó el reporte");
    }

    @GetMapping("/xlsx-report")
    public ResponseEntity<String> generateXLSXReport(HttpServletResponse response) throws IOException {
        String template = "classpath:\\reports\\localReports\\localXLSXReport.jrxml";
        List<Local> locals = localService.localList();

        response.setContentType("application/x-xlsx");
        response.setHeader("Content-Disposition", "attachment; filename=\"Reporte locales.xlsx\"");
        OutputStream out = response.getOutputStream();
        reportService.exportXLSX(out, locals, template);

        return ResponseEntity.ok().body("Se generó el reporte");
    }

    private URI getUriResourceLocation(Long id) {
        return ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(id)
                .toUri();
    }
}
