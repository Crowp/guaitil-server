package com.guaitilsoft.web.controllers;

import com.guaitilsoft.models.Reservation;
import com.guaitilsoft.services.report.ReportService;
import com.guaitilsoft.services.reservation.ReservationService;
import com.guaitilsoft.utils.Utils;
import com.guaitilsoft.web.models.reservation.ReservationRequest;
import com.guaitilsoft.web.models.reservation.ReservationResponse;
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
@RequestMapping(path = "/api/reservations")
public class ReservationController {

    public static final Logger logger = LoggerFactory.getLogger(ReservationController.class);

    private final ReservationService reservationService;
    private final ReportService<Reservation> reportService;

    @Autowired
    public ReservationController(ReservationService reservationService, ReportService<Reservation> reportService){
        this.reservationService = reservationService;
        this.reportService = reportService;
    }

    @GetMapping
    public ResponseEntity<List<ReservationResponse>> get () {
        List<ReservationResponse> reservations = reservationService.list();
        return ResponseEntity.ok().body(reservations);
    }

    @GetMapping("{id}")
    public ResponseEntity<ReservationResponse> getById(@PathVariable Long id) {
        ReservationResponse reservations = reservationService.get(id);
        logger.info("Fetching Reservation with id {}", id);
        return ResponseEntity.ok().body(reservations);
    }

    @PostMapping
    public ResponseEntity<ReservationResponse> post(@RequestBody ReservationRequest reservationRequest){
        logger.info("Creating reservation");

        ReservationResponse reservationResponse =  reservationService.save(reservationRequest);
        URI location = getUriResourceLocation(reservationResponse.getId());

        logger.info("Created reservation : {}", reservationResponse.getId());
        return ResponseEntity.created(location).body(reservationResponse);
    }

    @PutMapping("{id}")
    public ResponseEntity<ReservationResponse> put(@PathVariable Long id, @RequestBody ReservationRequest reservationRequest) {
        logger.info("Updating Reservation with id {}", id);
        ReservationResponse reservationResponse = reservationService.update(id, reservationRequest);
        logger.info("Updated Reservation with id {}", id);
        return ResponseEntity.ok().body(reservationResponse);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<ReservationResponse> delete(@PathVariable Long id) {
        ReservationResponse reservationResponse = reservationService.get(id);
        logger.info("Deleting Reservation with id {}", id);
        reservationService.delete(id);
        logger.info("Deleted Reservation with id {}", id);
        return ResponseEntity.ok().body(reservationResponse);
    }

    @GetMapping("/pdf-report")
    public ResponseEntity<byte[]> generatePDFReport() {
        String template = "classpath:reports/reservationReports/ReservationPdfReport.jrxml";
        List<Reservation> reservations = reservationService.listReservation();
        String time = Utils.getDateReport();

        byte[] bytes = reportService.exportPDF(reservations, template);
        String nameFile = "Reporte Reservaciones "+time+".pdf";

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_PDF)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + nameFile + "\"")
                .body(bytes);
    }

    @GetMapping("/xlsx-report")
    public ResponseEntity<byte[]> generateXLSXReport(){
        String template = "classpath:reports/reservationReports/reservationXlsxReport.jrxml";
        List<Reservation> reservations = reservationService.listReservation();
        String time = Utils.getDateReport();

        byte[] bytes = reportService.exportXLSX(reservations, template);
        String nameFile = "Reporte Reservaciones "+time+".xlsx";

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
