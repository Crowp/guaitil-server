package com.guaitilsoft.web.controllers;

import com.guaitilsoft.exceptions.ApiRequestException;
import com.guaitilsoft.models.Local;
import com.guaitilsoft.models.Member;
import com.guaitilsoft.services.LocalService;
import com.guaitilsoft.services.MemberService;
import com.guaitilsoft.services.ReportService;
import com.guaitilsoft.web.models.member.MemberView;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.lang.reflect.Type;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "/api/member")
public class MemberController {

    public static final Logger logger = LoggerFactory.getLogger(PersonController.class);

    private final MemberService memberService;
    private final LocalService localService;
    private final ModelMapper modelMapper;
    private final ReportService<Member> reportService;


    @Autowired
    public MemberController(MemberService memberService, LocalService localService, ModelMapper modelMapper, ReportService<Member> reportService) {
        this.memberService = memberService;
        this.localService = localService;
        this.modelMapper = modelMapper;
        this.reportService = reportService;
    }

    @GetMapping
    @PreAuthorize("hasRole('ROLE_ADMIN') OR hasRole('ROLE_SUPER_ADMIN')")
    public ResponseEntity<List<MemberView>> get() {
        Type listType = new TypeToken<List<MemberView>>(){}.getType();
        List<MemberView> members = modelMapper.map(memberService.list(), listType);
        return  ResponseEntity.ok().body(members);
    }

    @GetMapping("members-without-users")
    public ResponseEntity<List<MemberView>> getMembersWithoutUser() {
        Type listType = new TypeToken<List<MemberView>>(){}.getType();
        List<MemberView> members = modelMapper.map(memberService.getMemberWithoutUser(), listType);
        return  ResponseEntity.ok().body(members);
    }

    @GetMapping("{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN') OR hasRole('ROLE_SUPER_ADMIN')")
    public ResponseEntity<MemberView> getById(@PathVariable Long id) {
        MemberView getMember= modelMapper.map(memberService.get(id), MemberView.class);
        logger.info("Fetching Member with id: {}", id);
        return ResponseEntity.ok().body(getMember);
    }

    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN') OR hasRole('ROLE_SUPER_ADMIN')")
    public ResponseEntity<MemberView> post(@RequestBody MemberView memberRequest) {
        memberRequest.setId(null);
        Member member = modelMapper.map(memberRequest, Member.class);
        logger.info("Creating Member");
        memberService.save(member);
        MemberView memberResponse = modelMapper.map(member, MemberView.class);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(member.getId())
                .toUri();
        logger.info("Created Member: {}", memberResponse.getId());

        return ResponseEntity.created(location).body(memberResponse);
    }

    @PutMapping("{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN') OR hasRole('ROLE_SUPER_ADMIN')")
    public ResponseEntity<MemberView> put(@PathVariable Long id, @RequestBody MemberView memberRequest) {
        if(!id.equals(memberRequest.getId())){
            throw new ApiRequestException("El id del miembro: " + memberRequest.getId() + " es diferente al id del parametro: " + id);
        }
        Member member = modelMapper.map(memberRequest, Member.class);
        logger.info("Updating Member with id: {}", id);
        memberService.update(id, member);
        MemberView memberResponse = modelMapper.map(member, MemberView.class);
        logger.info("Updated Member with id: {}", id);
        return ResponseEntity.ok().body(memberResponse);
    }

    @DeleteMapping("{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN') OR hasRole('ROLE_SUPER_ADMIN')")
    public ResponseEntity<MemberView> delete(@PathVariable Long id) {
        MemberView memberResponse = modelMapper.map(memberService.get(id), MemberView.class);
        logger.info("Deleting Member with id: {}", id);
        memberService.delete(id);
        logger.info("Deleted Member with id: {}", id);
        return ResponseEntity.ok().body(memberResponse);
    }

    @GetMapping("/pdf-report")
    public ResponseEntity<String> generatePDFReport(HttpServletResponse response) throws IOException {
        String template = "classpath:\\reports\\personPDFReport.jrxml";
        List<Member> members = memberService.list().stream().filter(member -> member.getId() != 1).collect(Collectors.toList());

        response.setContentType("application/x-download");
        response.setHeader("Content-Disposition", "attachment; filename=\"ReporteGuaitil.pdf\"");
        OutputStream out = response.getOutputStream();
        reportService.exportPDF(out, members, template);

        return ResponseEntity.ok().body("Se generó el reporte");
    }

    @GetMapping("/xlsx-report")
    public ResponseEntity<String> generateXLSXReport(HttpServletResponse response) throws IOException {
        String template = "classpath:\\reports\\personXLSXReport.jrxml";
        List<Member> members = memberService.list().stream().filter(member -> member.getId() != 1).collect(Collectors.toList());

        response.setContentType("application/x-xlsx");
        response.setHeader("Content-Disposition", "attachment; filename=\"ReporteGuaitil.xlsx\"");
        OutputStream out = response.getOutputStream();
        reportService.exportXLSX(out, members, template);

        return ResponseEntity.ok().body("Se generó el reporte");
    }
}
