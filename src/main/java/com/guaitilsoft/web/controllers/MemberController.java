package com.guaitilsoft.web.controllers;

import com.guaitilsoft.models.Member;
import com.guaitilsoft.services.report.ReportService;
import com.guaitilsoft.services.member.MemberService;
import com.guaitilsoft.utils.Utils;
import com.guaitilsoft.web.models.member.MemberRequest;
import com.guaitilsoft.web.models.member.MemberResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(path = "/api/members")
public class MemberController {

    public static final Logger logger = LoggerFactory.getLogger(MemberController.class);

    private final MemberService memberService;
    private final ReportService<Member> reportService;


    @Autowired
    public MemberController(MemberService memberService, ReportService<Member> reportService) {
        this.memberService = memberService;
        this.reportService = reportService;
    }

    @GetMapping
    @PreAuthorize("hasRole('ROLE_ADMIN') OR hasRole('ROLE_SUPER_ADMIN')")
    public ResponseEntity<List<MemberResponse>> get() {
        List<MemberResponse> members = memberService.list();
        return  ResponseEntity.ok().body(members);
    }

    @GetMapping("members-without-users")
    public ResponseEntity<List<MemberResponse>> getMembersWithoutUser() {
        List<MemberResponse> members = memberService.getMemberWithoutUser();
        return  ResponseEntity.ok().body(members);
    }

    @GetMapping("{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN') OR hasRole('ROLE_SUPER_ADMIN')")
    public ResponseEntity<MemberResponse> getById(@PathVariable Long id) {
        MemberResponse memberResponse = memberService.get(id);
        logger.info("Fetching Member with id: {}", id);
        return ResponseEntity.ok().body(memberResponse);
    }

    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN') OR hasRole('ROLE_SUPER_ADMIN')")
    public ResponseEntity<MemberResponse> post(@RequestBody MemberRequest memberRequest) {
        logger.info("Creating Member");

        MemberResponse memberResponse = memberService.save(memberRequest);
        URI location = getUriResourceLocation(memberResponse.getId());

        logger.info("Created Member: {}", memberResponse.getId());
        return ResponseEntity.created(location).body(memberResponse);
    }

    @PutMapping("{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN') OR hasRole('ROLE_SUPER_ADMIN')")
    public ResponseEntity<MemberResponse> put(@PathVariable Long id, @RequestBody MemberRequest memberRequest) {
        logger.info("Updating Member with id: {}", id);

        MemberResponse memberResponse =  memberService.update(id, memberRequest);

        logger.info("Updated Member with id: {}", id);
        return ResponseEntity.ok().body(memberResponse);
    }

    @DeleteMapping("{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN') OR hasRole('ROLE_SUPER_ADMIN')")
    public ResponseEntity<MemberResponse> delete(@PathVariable Long id) {
        logger.info("Deleting Member with id: {}", id);

        MemberResponse memberResponse = memberService.get(id);
        memberService.delete(id);

        logger.info("Deleted Member with id: {}", id);
        return ResponseEntity.ok().body(memberResponse);
    }

    @GetMapping("/pdf-report")
        public ResponseEntity<byte[]>generatePDFReport() {
            String template = "memberReports/memberPDFReport.jrxml";
            List<Member> members = memberService.memberList();
            String time = Utils.getDateReport();

            byte[] bytes = reportService.exportPDF(members, template);
            String nameFile = "Reporte Miembros "+time+".pdf";

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_PDF)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + nameFile + "\"")
                .body(bytes);
    }

    @GetMapping("/xlsx-report")
    public ResponseEntity<byte[]> generateXLSXReport(){
        String template = "classpath:reports/memberReports/memberXLSXReport.jrxml";
        List<Member> members = memberService.memberList();
        String time = Utils.getDateReport();

        byte[] bytes = reportService.exportXLSX(members, template);
        String nameFile = "Reporte Miembros "+time+".xlsx";

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
