package com.guaitilsoft.web.controllers;

import com.guaitilsoft.services.activity.ActivityService;
import com.guaitilsoft.services.report.ReportService;
import com.guaitilsoft.utils.Utils;
import com.guaitilsoft.web.models.activity.ActivityRequest;
import com.guaitilsoft.web.models.activity.ActivityResponse;
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
@RequestMapping(path = "/api/activities")
public class ActivityController {

    public static final Logger logger = LoggerFactory.getLogger(ActivityController.class);

    private final ActivityService activityService;
    private final ReportService<ActivityResponse> reportService;

    @Autowired
    public ActivityController(ActivityService activityService, ReportService<ActivityResponse> reportService) {
        this.activityService = activityService;
        this.reportService = reportService;
    }

    @GetMapping
    public ResponseEntity<List<ActivityResponse>> get() {
        List<ActivityResponse> activities = activityService.list();
        return ResponseEntity.ok().body(activities);
    }

    @GetMapping("{id}")
    public ResponseEntity<ActivityResponse> getById(@PathVariable Long id) {
        logger.info("Fetching Activity with id {}", id);

        ActivityResponse activityResponse = activityService.get(id);
        return ResponseEntity.ok().body(activityResponse);
    }

    @GetMapping("/activities-active")
    public ResponseEntity<List<ActivityResponse>> getAllActivitiesActive() {
        List<ActivityResponse> activities = activityService.getAllActivitiesActive();
        return ResponseEntity.ok().body(activities);
    }

    @GetMapping("/is-active/{id}")
    public ResponseEntity<ActivityResponse> putIsActive(@PathVariable Long id) {
        logger.info("Updating Activity with id {}", id);

        ActivityResponse activityResponse = activityService.updateIsActive(id);

        logger.info("Updated Activity with id {}", id);
        return ResponseEntity.ok().body(activityResponse);
    }

    @PostMapping
    public ResponseEntity<ActivityResponse> post(@RequestBody ActivityRequest activityRequest) {
        logger.info("Creating activity");

        ActivityResponse activityResponse = activityService.save(activityRequest);
        URI location = getUriResourceLocation(activityResponse.getId());

        logger.info("Created activity : {}", activityResponse.getId());
        return ResponseEntity.created(location).body(activityResponse);
    }

    @PutMapping("{id}")
    public ResponseEntity<ActivityResponse> put(@PathVariable Long id, @RequestBody ActivityRequest activityRequest) {
        logger.info("Updating Activity with id {}", id);

        ActivityResponse activityResponse = activityService.update(id, activityRequest);

        logger.info("Updated Activity with id {}", id);
        return ResponseEntity.ok().body(activityResponse);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<ActivityResponse> delete(@PathVariable Long id) {
        logger.info("Deleting Activity with id {}", id);

        ActivityResponse activityResponse = activityService.get(id);
        activityService.delete(id);

        logger.info("Deleted Activity with id {}", id);
        return ResponseEntity.ok().body(activityResponse);
    }

    @DeleteMapping("/delete/multimedia-by-id")
    public ResponseEntity<ActivityResponse> deleteMultimediaById(@RequestParam Long id,
                                                                 @RequestParam Long idMultimedia) {
        logger.info("Deleting Activity Multimedia with id {}", id);

        ActivityResponse activityResponse =  activityService.deleteMultimediaById(id, idMultimedia);

        logger.info("Deleted Activity Multimedia with id {}", id);
        return ResponseEntity.ok().body(activityResponse);
    }

    @GetMapping("/pdf-report")
    public ResponseEntity<byte[]> generatePDFReport() {
        String template = "activityReports/activityPdfReport.jrxml";
        List<ActivityResponse> activities = activityService.list();
        String time = Utils.getDateReport();

        byte[] bytes = reportService.exportPDF(activities, template);
        String nameFile = "Reporte Actividades "+time+".pdf";

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_PDF)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + nameFile + "\"")
                .body(bytes);
    }

    @GetMapping("/xlsx-report")
    public ResponseEntity<byte[]> generateXLSXReport() {
        String template = "activityReports/activityXlsxReport.jrxml";
        List<ActivityResponse> activities = activityService.list();
        String time = Utils.getDateReport();

        byte[] bytes = reportService.exportXLSX(activities, template);
        String nameFile = "Reporte Actividades "+time+".xlsx";

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
