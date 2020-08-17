package com.guaitilsoft.web.controllers;

import com.guaitilsoft.exceptions.ApiRequestException;
import com.guaitilsoft.models.Member;
import com.guaitilsoft.services.MemberService;
import com.guaitilsoft.web.models.member.MemberView;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.persistence.EntityNotFoundException;
import java.net.URI;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping(path = "/api/member")
public class MemberController {

    public static final Logger logger = LoggerFactory.getLogger(PersonController.class);

    private MemberService memberService;
    private ModelMapper modelMapper;

    @Autowired
    public MemberController(MemberService memberService, ModelMapper modelMapper) {
        this.memberService = memberService;
        this.modelMapper = modelMapper;
    }

    @GetMapping
    public ResponseEntity<List<Member>> get(){
        return  ResponseEntity.ok().body(memberService.list());
    }

    @GetMapping("{id}")
    public ResponseEntity<Member> getById(@PathVariable Long id) throws Exception, EntityNotFoundException {
        logger.info("Fetching Associated with id: {}", id);
        return ResponseEntity.ok().body(memberService.get(id));
    }

    @PostMapping
    public ResponseEntity<MemberView> post(@RequestBody MemberView memberView) {
        Member member = modelMapper.map(memberView, Member.class);
        logger.info("Creating Associated");

        memberService.save(member);
        member.getLocals().forEach(m -> m.setMember(member));
        memberService.update(member.getId(), member);

        MemberView memberViewResponse = modelMapper.map(member, MemberView.class);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(member.getId())
                .toUri();
        logger.info("Created Associated: {}", memberViewResponse.getId());

        return ResponseEntity.created(location).body(memberViewResponse);
    }

    @PutMapping("{id}")
    public ResponseEntity<MemberView> put(@PathVariable Long id, @RequestBody MemberView memberView) {
        if(!id.equals(memberView.getId())){
            throw new ApiRequestException("El id del asociado: " + memberView.getId() + " es diferente al id del parametro: " + id);
        }
        Member member = modelMapper.map(memberView, Member.class);
        logger.info("Updating Associated with id: {}", id);

        memberService.update(id, member);

        MemberView memberViewResponse = modelMapper.map(member, MemberView.class);
        logger.info("Updated Associated with id: {}", id);
        return ResponseEntity.ok().body(memberViewResponse);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<MemberView> delete(@PathVariable Long id) throws Exception, EntityNotFoundException{
        MemberView memberView = modelMapper.map(memberService.get(id), MemberView.class);
        logger.info("Deleting Associated with id: {}", id);
        memberService.delete(id);
        logger.info("Deleted Associated with id: {}", id);
        return ResponseEntity.ok().body(memberView);
    }
}
