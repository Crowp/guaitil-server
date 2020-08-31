package com.guaitilsoft.web.controllers;

import com.guaitilsoft.exceptions.ApiRequestException;
import com.guaitilsoft.models.Local;
import com.guaitilsoft.models.Multimedia;
import com.guaitilsoft.services.LocalService;
import com.guaitilsoft.services.MemberService;
import com.guaitilsoft.services.MultimediaService;
import com.guaitilsoft.web.models.local.*;
import com.guaitilsoft.web.models.member.GetMemberDTO;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.lang.reflect.Type;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping(path = "/api/local")
public class LocalController {

    public static final Logger logger = LoggerFactory.getLogger(LocalController.class);

    private LocalService localService;
    private MemberService memberService;
    private MultimediaService multimediaService;
    private ModelMapper modelMapper;

    @Autowired
    public LocalController(LocalService localService, MemberService memberService, MultimediaService multimediaService,ModelMapper modelMapper) {
        this.localService = localService;
        this.memberService = memberService;
        this.multimediaService = multimediaService;
        this.modelMapper = modelMapper;
    }

    @GetMapping
    public ResponseEntity<List<GetLocalDTO>> get(){
        Type listType = new TypeToken<List<GetLocalDTO>>(){}.getType();
        List<GetLocalDTO> locals = modelMapper.map(localService.list(), listType);
        return  ResponseEntity.ok().body(locals);
    }

    @GetMapping("{id}")
    public ResponseEntity<GetLocalDTO> getById(@PathVariable Long id) {
        GetLocalDTO getLocalDTO = modelMapper.map(localService.get(id), GetLocalDTO.class);
        logger.info("Fetching Local with id: {}", id);
        return ResponseEntity.ok().body(getLocalDTO);
    }

    @PostMapping
    public ResponseEntity<CreateLocalDTO> post(@RequestBody CreateLocalDTO localRequest) {
        Local local = modelMapper.map(localRequest, Local.class);
        logger.info("Creating local");
        if(local.getMultimedia().size() > 0){
            List<Multimedia> multimediaList = new ArrayList<>();
            local.getMultimedia().forEach(media -> {
                Multimedia multimedia = multimediaService.get(media.getId());
                multimediaList.add(multimedia);
            });
            local.setMultimedia(multimediaList);
        }
        local.setMember(memberService.get(localRequest.getMember().getId()));
        localService.save(local);

        CreateLocalDTO localResponse = modelMapper.map(local, CreateLocalDTO.class);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(local.getId())
                .toUri();
        logger.info("Created local: {}", localResponse.getId());

        return ResponseEntity.created(location).body(localResponse);
    }

    @PutMapping("{id}")
    public ResponseEntity<UpdateLocalDTO> put(@PathVariable Long id, @RequestBody UpdateLocalDTO localRequest) {
        if(!id.equals(localRequest.getId())){
            throw new ApiRequestException("El id del local: " + localRequest.getId() + " es diferente al id del parametro: " + id);
        }
        Local local = modelMapper.map(localRequest, Local.class);
        logger.info("Updating Local with id: {}", id);

        localService.update(id, local);

        UpdateLocalDTO localResponse = modelMapper.map(local, UpdateLocalDTO.class);
        logger.info("Updated Local with id: {}", id);
        return ResponseEntity.ok().body(localResponse);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<DeleteLocalDTO> delete(@PathVariable Long id) {
        DeleteLocalDTO localResponse = modelMapper.map(localService.get(id), DeleteLocalDTO.class);
        logger.info("Deleting Local with id: {}", id);
        localService.delete(id);
        logger.info("Deleted Local with id: {}", id);
        return ResponseEntity.ok().body(localResponse);
    }
}
