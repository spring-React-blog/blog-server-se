package com.my.blog.member.controller;

import com.my.blog.member.dto.MemberResponse;
import com.my.blog.member.dto.MemberSchCondition;
import com.my.blog.member.dto.request.CreateRequest;
import com.my.blog.member.dto.request.UpdateRequest;
import com.my.blog.member.service.MemberService;
import com.my.blog.member.service.dto.MemberDTO;
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

    @PostMapping("/public/members")
    public ResponseEntity<MemberResponse> joinMember(@RequestBody @Valid CreateRequest request){
        Long saved = memberService.save(mapper.createMember(request));
        MemberResponse response = MemberResponse.builder().id(saved).build();
        return new ResponseEntity(response, HttpStatus.OK);
    }

    @GetMapping("/public/members")
    public ResponseEntity<MemberResponse> memberList(@RequestBody MemberSchCondition condition){
        return new ResponseEntity<>(null,HttpStatus.OK);
    }

    @PutMapping("/members")
    public ResponseEntity<MemberResponse> update(@RequestBody @Valid UpdateRequest request){
        MemberDTO dto = memberService.update(mapper.updateMember(request));
        MemberResponse response = mapper.getResponse(dto);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @GetMapping("/members/{id}")
    public ResponseEntity<MemberResponse> profile(@PathVariable Long id){
        MemberDTO dto = memberService.findById(id);
        MemberResponse content = mapper.getResponse(dto);
        return new ResponseEntity<>(content,HttpStatus.OK);
    }

    @DeleteMapping("/members")
    public ResponseEntity<MemberResponse> delete(@PathVariable Long id){
        memberService.deleteById(id);
        return new ResponseEntity<>( null,HttpStatus.OK);
    }





}
