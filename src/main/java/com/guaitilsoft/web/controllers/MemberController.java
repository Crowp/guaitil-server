package com.guaitilsoft.web.controllers;

import com.guaitilsoft.exceptions.ApiRequestException;
import com.guaitilsoft.models.Local;
import com.guaitilsoft.models.Member;
import com.guaitilsoft.services.MemberService;
import com.guaitilsoft.services.ReportService;
import com.guaitilsoft.web.models.member.MemberResponse;
import com.guaitilsoft.web.models.member.MemberRequest;
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
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "/api/members")
public class MemberController {

    public static final Logger logger = LoggerFactory.getLogger(PersonController.class);

    private final MemberService memberService;
    private final ModelMapper modelMapper;
    private final ReportService<Member> reportService;


    @Autowired
    public MemberController(MemberService memberService, ModelMapper modelMapper, ReportService<Member> reportService) {
        this.memberService = memberService;
        this.modelMapper = modelMapper;
        this.reportService = reportService;
    }

    @GetMapping
    @PreAuthorize("hasRole('ROLE_ADMIN') OR hasRole('ROLE_SUPER_ADMIN')")
    public ResponseEntity<List<MemberResponse>> get() {
        Type listType = new TypeToken<List<MemberResponse>>(){}.getType();
        List<MemberResponse> members = modelMapper.map(memberService.list(), listType);
        return  ResponseEntity.ok().body(members);
    }

    @GetMapping("members-without-users")
    public ResponseEntity<List<MemberResponse>> getMembersWithoutUser() {
        Type listType = new TypeToken<List<MemberResponse>>(){}.getType();
        List<MemberResponse> members = modelMapper.map(memberService.getMemberWithoutUser(), listType);
        return  ResponseEntity.ok().body(members);
    }

    @GetMapping("{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN') OR hasRole('ROLE_SUPER_ADMIN')")
    public ResponseEntity<MemberResponse> getById(@PathVariable Long id) {
        MemberResponse memberResponse = modelMapper.map(memberService.get(id), MemberResponse.class);
        logger.info("Fetching Member with id: {}", id);
        return ResponseEntity.ok().body(memberResponse);
    }

    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN') OR hasRole('ROLE_SUPER_ADMIN')")
    public ResponseEntity<MemberRequest> post(@RequestBody MemberRequest memberRequest) {
        memberRequest.setMemberId(null);
        Member member = mapperMemberWithLocal(memberRequest);
        logger.info("Creating Member");
        memberService.save(member);
        MemberRequest memberResponse = modelMapper.map(member, MemberRequest.class);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(member.getMemberId())
                .toUri();
        logger.info("Created Member: {}", memberResponse.getMemberId());

        return ResponseEntity.created(location).body(memberResponse);
    }

    private Member mapperMemberWithLocal(@RequestBody MemberRequest memberRequest) {
        Type listType = new TypeToken<List<Local>>(){}.getType();
        List<Local> locals = modelMapper.map(memberRequest.getLocals(), listType);
        Member member = modelMapper.map(memberRequest, Member.class);
        member.setLocals(locals);
        return member;
    }

    @PutMapping("{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN') OR hasRole('ROLE_SUPER_ADMIN')")
    public ResponseEntity<MemberRequest> put(@PathVariable Long id, @RequestBody MemberRequest memberRequest) {
        if(!id.equals(memberRequest.getMemberId())){
            throw new ApiRequestException("El id del miembro: " + memberRequest.getMemberId() + " es diferente al id del parametro: " + id);
        }
        Member member = modelMapper.map(memberRequest, Member.class);
        logger.info("Updating Member with id: {}", id);
        memberService.update(id, member);
        MemberRequest memberResponse = modelMapper.map(member, MemberRequest.class);
        logger.info("Updated Member with id: {}", id);
        return ResponseEntity.ok().body(memberResponse);
    }

    @DeleteMapping("{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN') OR hasRole('ROLE_SUPER_ADMIN')")
    public ResponseEntity<MemberRequest> delete(@PathVariable Long id) {
        MemberRequest memberResponse = modelMapper.map(memberService.get(id), MemberRequest.class);
        logger.info("Deleting Member with id: {}", id);
        memberService.delete(id);
        logger.info("Deleted Member with id: {}", id);
        return ResponseEntity.ok().body(memberResponse);
    }

    @GetMapping("/pdf-report")
    public ResponseEntity<String> generatePDFReport(HttpServletResponse response) throws IOException {
        String template = "classpath:\\reports\\memberReports\\memberPDFReport.jrxml";
        List<Member> members = memberService.list().stream().filter(member -> member.getMemberId() != 1).collect(Collectors.toList());

        response.setContentType("application/x-download");
        response.setHeader("Content-Disposition", "attachment; filename=\"Reporte de asociados.pdf\"");
        OutputStream out = response.getOutputStream();
        reportService.exportPDF(out, members, template);

        return ResponseEntity.ok().body("Se generó el reporte");
    }

    @GetMapping("/xlsx-report")
    public ResponseEntity<String> generateXLSXReport(HttpServletResponse response) throws IOException {
        String template = "classpath:\\reports\\memberReports\\memberXLSXReport.jrxml";
        List<Member> members = memberService.list().stream().filter(member -> member.getMemberId() != 1).collect(Collectors.toList());

        response.setContentType("application/x-xlsx");
        response.setHeader("Content-Disposition", "attachment; filename=\"ReporteAsociados.xlsx\"");
        OutputStream out = response.getOutputStream();
        reportService.exportXLSX(out, members, template);

        return ResponseEntity.ok().body("Se generó el reporte");
    }
}
