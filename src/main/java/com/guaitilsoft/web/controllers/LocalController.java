package com.guaitilsoft.web.controllers;

import com.guaitilsoft.exceptions.ApiRequestException;
import com.guaitilsoft.models.Local;
import com.guaitilsoft.models.Member;
import com.guaitilsoft.models.constant.LocalType;
import com.guaitilsoft.services.LocalService;
import com.guaitilsoft.services.MemberService;
import com.guaitilsoft.utils.Utils;
import com.guaitilsoft.web.models.local.LocalRequest;
import com.guaitilsoft.web.models.local.LocalResponse;
import com.guaitilsoft.web.models.multimedia.MultimediaResponse;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.modelmapper.convention.MatchingStrategies;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.lang.reflect.Type;
import java.net.URI;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping(path = "/api/locals")
public class LocalController {

    public static final Logger logger = LoggerFactory.getLogger(LocalController.class);

    private final LocalService localService;
    private final ModelMapper modelMapper;
    private final Utils utils;

    @Autowired
    public LocalController(LocalService localService,
                           ModelMapper modelMapper,
                           Utils utils) {
        this.localService = localService;
        this.modelMapper = modelMapper;
        this.utils = utils;

    }

    @GetMapping
    public ResponseEntity<List<LocalResponse>> get() {
        Type listType = new TypeToken<List<LocalResponse>>() {
        }.getType();
        List<LocalResponse> locals = modelMapper.map(localService.list(), listType);
        locals.forEach(l -> this.utils.addUrlToMultimedia(l.getMultimedia()));
        return ResponseEntity.ok().body(locals);
    }

    @GetMapping("/local-types/{localType}")
    public ResponseEntity<List<LocalResponse>> getLocalsByLocalType(@PathVariable LocalType localType) {
        Type listType = new TypeToken<List<LocalResponse>>() {
        }.getType();
        List<LocalResponse> locals = modelMapper.map(localService.getLocalByLocalType(localType), listType);
        locals.forEach(l -> this.utils.addUrlToMultimedia(l.getMultimedia()));
        return ResponseEntity.ok().body(locals);
    }

    @GetMapping("{id}")
    public ResponseEntity<LocalResponse> getById(@PathVariable Long id) {
        LocalResponse local = modelMapper.map(localService.get(id), LocalResponse.class);
        this.utils.addUrlToMultimedia(local.getMultimedia());
        logger.info("Fetching Local with id: {}", id);
        return ResponseEntity.ok().body(local);
    }

    @GetMapping("/member-id/{memberId}")
    public ResponseEntity<List<LocalResponse>> getLocalsByMemberId(@PathVariable Long memberId) {
        Type listType = new TypeToken<List<LocalResponse>>() {}.getType();
        List<LocalResponse> locals = modelMapper.map(localService.getAllLocalByIdMember(memberId), listType);
        locals.forEach(local -> this.utils.addUrlToMultimedia(local.getMultimedia()));
        logger.info("Fetching Local with id: {}", memberId);
        return ResponseEntity.ok().body(locals);
    }

    @PostMapping
    public ResponseEntity<LocalRequest> post(@RequestBody LocalRequest localRequest) {
        Local local = modelMapper.map(localRequest, Local.class);
        logger.info("Creating local");
        Long memberId = localRequest.getMember().getMemberId();
        local.setMember(this.utils.loadFullMember(memberId));
        localService.save(local);
        LocalRequest localResponse = modelMapper.map(local, LocalRequest.class);
        this.utils.addUrlToMultimedia(localResponse.getMultimedia());

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(localResponse.getId())
                .toUri();
        logger.info("Created local: {}", localResponse.getId());
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STANDARD);
        return ResponseEntity.created(location).body(localResponse);
    }

    @PutMapping("{id}")
    public ResponseEntity<LocalRequest> put(@PathVariable Long id, @RequestBody LocalRequest localRequest) {
        if (!id.equals(localRequest.getId())) {
            throw new ApiRequestException("El id del local: " + localRequest.getId() + " es diferente al id del parametro: " + id);
        }
        Local local = modelMapper.map(localRequest, Local.class);
        logger.info("Updating Local with id: {}", id);

        localService.update(id, local);
        LocalRequest localResponse = modelMapper.map(local, LocalRequest.class);
        this.utils.addUrlToMultimedia(localResponse.getMultimedia());
        logger.info("Updated Local with id: {}", id);
        return ResponseEntity.ok().body(localResponse);
    }

    @DeleteMapping("{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN') AND hasRole('ROLE_SUPER_ADMIN')")
    public ResponseEntity<LocalResponse> delete(@PathVariable Long id) {
        LocalResponse localResponse = modelMapper.map(localService.get(id), LocalResponse.class);
        logger.info("Deleting Local with id: {}", id);
        localService.delete(id);
        logger.info("Deleted Local with id: {}", id);
        return ResponseEntity.ok().body(localResponse);
    }

    @DeleteMapping("delete-multimedia-by-id")
    public ResponseEntity<LocalResponse> deleteMultimediaById(@RequestParam Long id,
                                                              @RequestParam Long idMultimedia) {
        logger.info("Deleting Local Multimedia with id {}", id);
        LocalResponse localResponse = modelMapper.map(
                localService.deleteMultimediaById(id, idMultimedia), LocalResponse.class);
        this.utils.addUrlToMultimedia(localResponse.getMultimedia());
        logger.info("Deleting Local Multimedia with id {}", id);
        return ResponseEntity.ok().body(localResponse);
    }

}
