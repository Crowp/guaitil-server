package com.guaitilsoft.web.controllers;


import com.guaitilsoft.exceptions.ApiRequestException;
import com.guaitilsoft.message.ResponseMessage;
import com.guaitilsoft.services.MultimediaService;
import com.guaitilsoft.web.models.multimedia.MultimediaRequest;
import com.guaitilsoft.web.models.multimedia.MultimediaResponse;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;


@CrossOrigin
@RestController
@RequestMapping(path = "/api/multimedia")
public class MultimediaController {

    public static final Logger logger = LoggerFactory.getLogger(LocalController.class);

    private MultimediaService multimediaService;
    private ModelMapper modelMapper;

    @Autowired
    public MultimediaController(MultimediaService multimediaService, ModelMapper modelMapper){
        this.multimediaService = multimediaService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/downloadFile/{filename:.+}")
    public ResponseEntity<String> getFile(@PathVariable String filename, HttpServletRequest request) {
        /*Resource file = multimediaService.load(filename);
        // Try to determine file's content type
        String contentType = null;
        try {
            contentType = request.getServletContext().getMimeType(file.getFile().getAbsolutePath());
        } catch (IOException ex) {
            logger.info("Could not determine file type.");
        }
        // Fallback to the default content type if type could not be determined
        if(contentType == null) {
            contentType = "application/octet-stream";
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
                .body(file);*/
        return ResponseEntity.ok().body(".");
    }

    /*@GetMapping
    public ResponseEntity<List<Multimedia>> getListFiles() {
        List<Multimedia> multimediaList = multimediaService.loadAll().map(path -> {
            String name = path.getFileName().toString();
            String url = MvcUriComponentsBuilder
                    .fromMethodName(MultimediaController.class, "getFile", path.getFileName().toString()).build().toString();

            return new Multimedia(name, url);
        }).collect(Collectors.toList());

        return ResponseEntity.status(HttpStatus.OK).body(multimediaList);
    }*/

    @PostMapping("upload")
    public ResponseEntity<MultimediaResponse> uploadFile(@ModelAttribute MultimediaRequest multimedia) {
        try {
            MultimediaResponse multimediaResponse = modelMapper.map(multimediaService.store(multimedia), MultimediaResponse.class);
            String url = ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path("/downloadFile/")
                    .path(multimediaResponse.getFileName())
                    .toUriString();
            multimediaResponse.setUrl(url);


            return ResponseEntity.status(HttpStatus.OK).body(multimediaResponse);
        } catch (Exception e) {
            throw new ApiRequestException("No se pudo guardar el archivo");
        }
    }
}
