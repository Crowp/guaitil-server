package com.guaitilsoft.web.controllers;

import com.guaitilsoft.exceptions.ApiRequestException;
import com.guaitilsoft.models.Local;
import com.guaitilsoft.models.Member;
import com.guaitilsoft.models.constant.LocalType;
import com.guaitilsoft.services.LocalService;
import com.guaitilsoft.services.MemberService;
import com.guaitilsoft.web.models.local.GetLocal;
import com.guaitilsoft.web.models.local.LocalView;
import com.guaitilsoft.web.models.multimedia.MultimediaResponse;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
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
    private final MemberService memberService;
    private final ModelMapper modelMapper;

    @Autowired
    public LocalController(LocalService localService, MemberService memberService,ModelMapper modelMapper) {
        this.localService = localService;
        this.memberService = memberService;
        this.modelMapper = modelMapper;
    }

    @GetMapping
    public ResponseEntity<List<GetLocal>> get(){
        Type listType = new TypeToken<List<GetLocal>>(){}.getType();
        List<GetLocal> locals = modelMapper.map(localService.list(), listType);
        locals.forEach(l -> addUrlToMultimedia(l.getMultimedia()));
        return  ResponseEntity.ok().body(locals);
    }

    @GetMapping("/workshops")
    public ResponseEntity<List<GetLocal>> getWorkshops(){
        LocalType localType = LocalType.WORKSHOP;
        Type listType = new TypeToken<List<GetLocal>>(){}.getType();
        List<GetLocal> locals = modelMapper.map(localService.getLocalByLocalType(localType), listType);
        locals.forEach(l -> addUrlToMultimedia(l.getMultimedia()));
        return  ResponseEntity.ok().body(locals);
    }

    @GetMapping("/kitchens")
    public ResponseEntity<List<GetLocal>> getKitchens(){
        LocalType localType = LocalType.KITCHEN;
        Type listType = new TypeToken<List<GetLocal>>(){}.getType();
        List<GetLocal> locals = modelMapper.map(localService.getLocalByLocalType(localType), listType);
        locals.forEach(l -> addUrlToMultimedia(l.getMultimedia()));
        return  ResponseEntity.ok().body(locals);
    }

    @GetMapping("/lodging")
    public ResponseEntity<List<GetLocal>> getLodgings(){
        LocalType localType = LocalType.LODGING;
        Type listType = new TypeToken<List<GetLocal>>(){}.getType();
        List<GetLocal> locals = modelMapper.map(localService.getLocalByLocalType(localType), listType);
        locals.forEach(l -> addUrlToMultimedia(l.getMultimedia()));
        return  ResponseEntity.ok().body(locals);
    }

    @GetMapping("/others-locales")
    public ResponseEntity<List<GetLocal>> getOthers(){
        LocalType localType = LocalType.OTHERS;
        Type listType = new TypeToken<List<GetLocal>>(){}.getType();
        List<GetLocal> locals = modelMapper.map(localService.getLocalByLocalType(localType), listType);
        locals.forEach(l -> addUrlToMultimedia(l.getMultimedia()));
        return  ResponseEntity.ok().body(locals);
    }

    @GetMapping("{id}")
    public ResponseEntity<GetLocal> getById(@PathVariable Long id) {
        GetLocal local = modelMapper.map(localService.get(id), GetLocal.class);
        addUrlToMultimedia(local.getMultimedia());
        logger.info("Fetching Local with id: {}", id);
        return ResponseEntity.ok().body(local);
    }

    @GetMapping("/member-id/{id}")
    public ResponseEntity<List<GetLocal>> getLocalsByMemberId(@PathVariable Long id) {
        Member member = memberService.get(id);
        Type listType = new TypeToken<List<GetLocal>>(){}.getType();
        List<GetLocal> locals = modelMapper.map(localService.getAllLocalByIdMember(member.getId()), listType);
        locals.forEach(l -> addUrlToMultimedia(l.getMultimedia()));
        logger.info("Fetching Local with id: {}", id);
        return ResponseEntity.ok().body(locals);
    }

    @PostMapping
    public ResponseEntity<LocalView> post(@RequestBody LocalView localRequest) {
        Local local = modelMapper.map(localRequest, Local.class);
        logger.info("Creating local");
        local.setMember(memberService.get(localRequest.getMember().getId()));
        localService.save(local);
        LocalView localResponse = modelMapper.map(local, LocalView.class);
        addUrlToMultimedia(localResponse.getMultimedia());

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(local.getId())
                .toUri();
        logger.info("Created local: {}", localResponse.getId());

        return ResponseEntity.created(location).body(localResponse);
    }

    @PutMapping("{id}")
    public ResponseEntity<LocalView> put(@PathVariable Long id, @RequestBody LocalView localRequest) {
        if(!id.equals(localRequest.getId())){
            throw new ApiRequestException("El id del local: " + localRequest.getId() + " es diferente al id del parametro: " + id);
        }
        Local local = modelMapper.map(localRequest, Local.class);
        logger.info("Updating Local with id: {}", id);

        localService.update(id, local);
        LocalView localResponse = modelMapper.map(local, LocalView.class);
        addUrlToMultimedia(localResponse.getMultimedia());
        logger.info("Updated Local with id: {}", id);
        return ResponseEntity.ok().body(localResponse);
    }

    @DeleteMapping("{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN') AND hasRole('ROLE_SUPER_ADMIN')")
    public ResponseEntity<LocalView> delete(@PathVariable Long id) {
        LocalView localResponse = modelMapper.map(localService.get(id), LocalView.class);
        logger.info("Deleting Local with id: {}", id);
        localService.delete(id);
        logger.info("Deleted Local with id: {}", id);
        return ResponseEntity.ok().body(localResponse);
    }

    @DeleteMapping("delete-multimedia-by-id")
    public ResponseEntity<LocalView> deleteMultimediaById(@RequestParam Long id,
                                                          @RequestParam Long idMultimedia) {
        logger.info("Deleting Local Multimedia with id {}", id);
        LocalView localResponse = modelMapper.map(
                localService.deleteMultimediaById(id, idMultimedia), LocalView.class);
        addUrlToMultimedia(localResponse.getMultimedia());
        logger.info("Deleting Local Multimedia with id {}", id);
        return ResponseEntity.ok().body(localResponse);
    }

    private void addUrlToMultimedia(List<MultimediaResponse> multimedia){
        multimedia.forEach(m ->{
            String url = getUrlHost(m);
            m.setUrl(url);
        });
    }

    private String getUrlHost(MultimediaResponse multimediaResponse){
        String resourcePath = "/api/multimedia/load/";
        return ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(resourcePath)
                .path(multimediaResponse.getFileName())
                .toUriString();
    }
}
