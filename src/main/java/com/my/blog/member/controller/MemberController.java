package com.my.blog.member.controller;

import com.my.blog.global.common.response.ResponseEnvelope;
import com.my.blog.global.common.response.ResponseHeader;
import com.my.blog.member.dto.MemberSchCondition;
import com.my.blog.member.service.dto.MemberDTO;
import com.my.blog.member.service.MemberService;
import com.my.blog.member.dto.request.CreateRequest;
import com.my.blog.member.dto.MemberResponse;
import com.my.blog.member.dto.request.UpdateRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@AllArgsConstructor
@RestController
@RequestMapping("/api")
public class MemberController {
    private MemberService memberService;
    private ModelMapper mapper;

    @PostMapping("/members")
    public ResponseEntity<MemberResponse> join(@RequestBody @Valid CreateRequest request){
        Long saved = memberService.save(mapper.createMember(request));
        MemberResponse response = MemberResponse.builder().id(saved).build();
        return new ResponseEntity(response, HttpStatus.OK);
    }

    @GetMapping("/members/{id}")
    public ResponseEnvelope<MemberResponse> profile(@PathVariable Long id){
        MemberDTO dto = memberService.findById(id);
        MemberResponse content = mapper.getResponse(dto);
        return new ResponseEnvelope<>(ResponseHeader.ok(),content);
    }

    @GetMapping("/public/members")
    public ResponseEnvelope<MemberResponse> memberList(@RequestBody MemberSchCondition condition){
        return new ResponseEnvelope<>(ResponseHeader.ok(),null);
    }

    @PutMapping("/members")
    public ResponseEnvelope<MemberResponse> update(@RequestBody @Valid UpdateRequest request){
        MemberDTO dto = memberService.update(mapper.updateMember(request));
        MemberResponse response = mapper.getResponse(dto);
        return new ResponseEnvelope<>(ResponseHeader.ok(), response);
    }
    @DeleteMapping("/members")
    public ResponseEnvelope<MemberResponse> delete(@PathVariable Long id){
         memberService.deleteById(id);
        return new ResponseEnvelope<>(ResponseHeader.ok(), null);
    }
}
