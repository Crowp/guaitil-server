package com.guaitilsoft.web.controllers;


import com.guaitilsoft.message.ResponseMessage;
import com.guaitilsoft.models.Multimedia;
import com.guaitilsoft.services.MultimediaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import java.util.List;
import java.util.stream.Collectors;


@CrossOrigin
@RestController
@RequestMapping(path = "/api/multimedia")
public class MultimediaController {

      MultimediaService multimediaService;

    @Autowired
    public MultimediaController(MultimediaService multimediaService){this.multimediaService =multimediaService;}

    @GetMapping("/files/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> getFile(@PathVariable String filename) {
        Resource file = multimediaService.load(filename);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"").body(file);
    }

    @GetMapping("/files")
    public ResponseEntity<List<Multimedia>> getListFiles() {
        List<Multimedia> multimediaList = multimediaService.loadAll().map(path -> {
            String name = path.getFileName().toString();
            String url = MvcUriComponentsBuilder
                    .fromMethodName(MultimediaController.class, "getFile", path.getFileName().toString()).build().toString();

            return new Multimedia(name, url);
        }).collect(Collectors.toList());

        return ResponseEntity.status(HttpStatus.OK).body(multimediaList);
    }

    @PostMapping("/upload")
    public ResponseEntity<ResponseMessage> uploadFile(@RequestParam("file") MultipartFile file) {
        String message = "";
        try {
            multimediaService.save(file);

            message = "Uploaded the file successfully: " + file.getOriginalFilename();
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
        } catch (Exception e) {
            message = "Could not upload the file: " + file.getOriginalFilename() + "!";
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
        }
    }

}
