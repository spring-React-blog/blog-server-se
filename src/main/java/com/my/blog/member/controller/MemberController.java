package com.my.blog.member.controller;

import com.my.blog.common.response.ResponseEnvelope;
import com.my.blog.common.response.ResponseHeader;
import com.my.blog.member.service.MemberService;
import com.my.blog.member.dto.ModelMapper;
import com.my.blog.member.dto.request.CreateRequest;
import com.my.blog.member.dto.MemberResponse;
import com.my.blog.member.dto.request.UpdateRequest;
import org.springframework.web.bind.annotation.*;

@RestController
public class MemberController {
    private MemberService memberService;

    @PostMapping("/members")
    public ResponseEnvelope<MemberResponse> join(@RequestBody CreateRequest request){
        Long saved = memberService.save(ModelMapper.createMember(request));
        MemberResponse response = MemberResponse.builder().id(saved).build();

        return new ResponseEnvelope(ResponseHeader.ok(),response);
    }

    @GetMapping("/members/{id}")
    public ResponseEnvelope<MemberResponse> profile(@PathVariable Long id){
        MemberResponse content = memberService.findById(id);
        return new ResponseEnvelope<>(ResponseHeader.ok(),content);
    }

    @PutMapping("/members")
    public ResponseEnvelope<MemberResponse> update(@RequestBody UpdateRequest request){
        MemberResponse update = memberService.update(ModelMapper.updateMember(request));
        return new ResponseEnvelope<>(ResponseHeader.ok(), update);
    }
}
