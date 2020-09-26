package com.guaitilsoft.web.controllers;


import com.guaitilsoft.exceptions.ApiRequestException;
import com.guaitilsoft.services.MultimediaService;
import com.guaitilsoft.web.models.multimedia.MultimediaRequest;
import com.guaitilsoft.web.models.multimedia.MultimediaResponse;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URI;
import java.util.List;


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

    @GetMapping("{id}")
    public ResponseEntity<MultimediaResponse> getById(@PathVariable Long id){
        MultimediaResponse multimediaResponse = modelMapper.map(multimediaService.get(id), MultimediaResponse.class);
        String url = getUrlHost(multimediaResponse);
        multimediaResponse.setUrl(url);
        return ResponseEntity.ok().body(multimediaResponse);
    }

    @GetMapping
    public ResponseEntity<List<MultimediaResponse>> list(HttpServletRequest request){
        Type listType = new TypeToken<List<MultimediaResponse>>(){}.getType();
        List<MultimediaResponse> multimediaResponses = modelMapper.map(multimediaService.list(), listType);
        multimediaResponses.forEach(m -> {
            String url = getUrlHost(m);
            m.setUrl(url);
        });
        return ResponseEntity.ok().body(multimediaResponses);
    }

    @PostMapping("upload")
    public ResponseEntity<MultimediaResponse> uploadFile(@ModelAttribute MultimediaRequest multimedia) {
        try {
            MultimediaResponse multimediaResponse = modelMapper.map(multimediaService.store(multimedia), MultimediaResponse.class);
            String url = getUrlHost(multimediaResponse);
            multimediaResponse.setUrl(url);

            URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(multimediaResponse.getId())
                    .toUri();
            return ResponseEntity.created(location).body(multimediaResponse);

        } catch (Exception e) {
            throw new ApiRequestException("No se pudo guardar el archivo");
        }
    }

    @GetMapping("load/{filename:.+}")
    public ResponseEntity<Resource> getFile(@PathVariable String filename, HttpServletRequest request) {
        Resource file = multimediaService.load(filename);
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
                .body(file);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<MultimediaResponse> delete(@PathVariable Long id){
        MultimediaResponse multimediaResponse = modelMapper.map(multimediaService.get(id), MultimediaResponse.class);
        multimediaService.delete(id);
        return ResponseEntity.ok().body(multimediaResponse);
    }

    private String getUrlHost(MultimediaResponse multimediaResponse) {
        String resourcePath = "/api/multimedia/load/";
        return ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(resourcePath)
                .path(multimediaResponse.getFileName())
                .toUriString();
    }
}
