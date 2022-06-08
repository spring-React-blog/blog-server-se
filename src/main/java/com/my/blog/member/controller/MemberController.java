package com.my.blog.member.controller;

import com.my.blog.member.service.MemberService;
import com.my.blog.member.vo.MemberRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MemberController {
    private MemberService memberService;

    @PostMapping("/member")
    public void join(@RequestBody MemberRequest request){

    }

}
