package com.guaitilsoft.web.controllers;

import com.guaitilsoft.models.constant.Role;
import com.guaitilsoft.services.report.ReportService;
import com.guaitilsoft.services.user.UserService;
import com.guaitilsoft.utils.Utils;
import com.guaitilsoft.web.models.user.UserLazyResponse;
import com.guaitilsoft.web.models.user.UserReportResponse;
import com.guaitilsoft.web.models.user.UserRequest;
import com.guaitilsoft.web.models.user.UserResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserService userService;
    private final ReportService<UserReportResponse> reportService;

    @Autowired
    public AuthController(UserService userService, ReportService<UserReportResponse> reportService) {
        this.userService = userService;
        this.reportService = reportService;
    }

    @GetMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<List<UserLazyResponse>> get(){
        List<UserLazyResponse> users = userService.getAllUsers();
        return  ResponseEntity.ok().body(users);
    }

    @GetMapping("{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<UserResponse> getById(@PathVariable Long id){
        UserResponse user = userService.get(id);
        return  ResponseEntity.ok().body(user);
    }

    @GetMapping("/member/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<UserResponse> getByMemberId(@PathVariable Long id){
        UserResponse user = userService.getByMemberID(id);
        return  ResponseEntity.ok().body(user);
    }

    @PostMapping("/login")
    public ResponseEntity<UserResponse> login(@RequestParam String email,
                                               @RequestParam String password) {
        UserResponse userResponse = userService.login(email, password);
        return ResponseEntity.ok().body(userResponse);
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponse> register(@RequestBody UserRequest userRequest) {
        UserResponse userResponse = userService.register(userRequest);
        return ResponseEntity.ok().body(userResponse);
    }

    @PutMapping("/reset")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<UserResponse> reset(@RequestParam Long id,
                                              @RequestParam String newPassword) {
        UserResponse userResponse = userService.resetPassword(id, newPassword);
        return ResponseEntity.ok().body(userResponse);
    }

    @PutMapping("/update-roles/{id}")
    @PreAuthorize("hasRole('ROLE_SUPER_ADMIN')")
    public ResponseEntity<UserResponse> updateRoles(@RequestBody List<Role> roles, @PathVariable Long id) {
        UserResponse userResponse = userService.updateRoles(roles, id);
        return ResponseEntity.ok().body(userResponse);
    }

    @DeleteMapping("{email}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<UserResponse> delete(@PathVariable String email) {
        UserResponse userResponse = userService.search(email);
        userService.deleteByEmail(email);
        return ResponseEntity.ok().body(userResponse);
    }

    @GetMapping("/pdf-report")
    public ResponseEntity<byte[]> generatePDFReport() {
        String template = "classpath:reports/userReports/usersPDFReport.jrxml";
        List<UserReportResponse> users = userService.getUsersReport();
        String time = Utils.getDateReport();

        byte[] bytes = reportService.exportPDF(users, template);
        String nameFile = "Reporte usuarios "+time+".pdf";

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_PDF)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + nameFile + "\"")
                .body(bytes);
    }

    @GetMapping("/xlsx-report")
    public ResponseEntity<byte[]> generateXLSXReport() {
        String template = "classpath:reports/activityReports/activityXlsxReport.jrxml";
        List<UserReportResponse> users = userService.getUsersReport();
        String time = Utils.getDateReport();

        byte[] bytes = reportService.exportXLSX(users, template);
        String nameFile = "Reporte usuarios "+time+".xlsx";

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType("application/x-xlsx"))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + nameFile + "\"")
                .body(bytes);
    }
}
