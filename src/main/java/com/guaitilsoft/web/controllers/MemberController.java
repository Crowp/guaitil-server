package com.guaitilsoft.web.controllers;

import com.guaitilsoft.exceptions.ApiRequestException;
import com.guaitilsoft.models.Member;
import com.guaitilsoft.models.Multimedia;
import com.guaitilsoft.services.LocalService;
import com.guaitilsoft.services.MemberService;
import com.guaitilsoft.services.MultimediaService;
import com.guaitilsoft.web.models.member.*;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.persistence.EntityNotFoundException;
import java.lang.reflect.Type;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(path = "/api/member")
public class MemberController {

    public static final Logger logger = LoggerFactory.getLogger(PersonController.class);

    private MemberService memberService;
    private MultimediaService multimediaService;
    private LocalService localService;
    private ModelMapper modelMapper;

    @Autowired
    public MemberController(MemberService memberService, MultimediaService multimediaService, ModelMapper modelMapper, LocalService localService) {
        this.memberService = memberService;
        this.multimediaService = multimediaService;
        this.localService = localService;
        this.modelMapper = modelMapper;
    }

    @GetMapping
    public ResponseEntity<List<GetMemberDTO>> get(){
        Type listType = new TypeToken<List<GetMemberDTO>>(){}.getType();
        List<GetMemberDTO> members = modelMapper.map(memberService.list(), listType);
        return  ResponseEntity.ok().body(members);
    }

    @GetMapping("{id}")
    public ResponseEntity<GetMemberDTO> getById(@PathVariable Long id) throws Exception, EntityNotFoundException {
        GetMemberDTO getMemberDTO = modelMapper.map(memberService.get(id), GetMemberDTO.class);
        logger.info("Fetching Member with id: {}", id);
        return ResponseEntity.ok().body(getMemberDTO);
    }

    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<CreateMemberDTO> post(@RequestBody CreateMemberDTO memberRequest) {
        memberRequest.setId(null);
        Member member = modelMapper.map(memberRequest, Member.class);
        logger.info("Creating Member");
        if(member.getLocals().size() > 0){
            member.getLocals().forEach(local -> {
                local.setMember(member);

                if(local.getMultimedia().size() > 0){
                    List<Multimedia> multimediaList = new ArrayList<>();
                    local.getMultimedia().forEach(media -> {
                        Multimedia multimedia = multimediaService.get(media.getId());
                        multimediaList.add(multimedia);
                    });
                    local.setMultimedia(multimediaList);
                }
            });
        }
        memberService.save(member);
        CreateMemberDTO memberResponse = modelMapper.map(member, CreateMemberDTO.class);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(member.getId())
                .toUri();
        logger.info("Created Member: {}", memberResponse.getId());

        return ResponseEntity.created(location).body(memberResponse);
    }

    @PutMapping("{id}")
    public ResponseEntity<UpdateMemberDTO> put(@PathVariable Long id, @RequestBody UpdateMemberDTO memberView) {
        if(!id.equals(memberView.getId())){
            throw new ApiRequestException("El id del miembro: " + memberView.getId() + " es diferente al id del parametro: " + id);
        }
        Member member = modelMapper.map(memberView, Member.class);
        logger.info("Updating Member with id: {}", id);

        memberService.update(id, member);

        UpdateMemberDTO memberViewResponse = modelMapper.map(member, UpdateMemberDTO.class);
        logger.info("Updated Member with id: {}", id);
        return ResponseEntity.ok().body(memberViewResponse);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<DeleteMemberDTO> delete(@PathVariable Long id) throws Exception, EntityNotFoundException{
        DeleteMemberDTO memberResponse = modelMapper.map(memberService.get(id), DeleteMemberDTO.class);
        logger.info("Deleting Member with id: {}", id);
        memberService.delete(id);
        logger.info("Deleted Member with id: {}", id);
        return ResponseEntity.ok().body(memberResponse);
    }
}
