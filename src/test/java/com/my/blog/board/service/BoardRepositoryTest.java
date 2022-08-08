package com.my.blog.board.service;

import com.my.blog.board.domain.vo.Content;
import com.my.blog.board.domain.vo.Status;
import com.my.blog.board.domain.vo.Title;
import com.my.blog.board.dto.request.CreateRequest;
import com.my.blog.category.service.CategoryService;
import com.my.blog.member.service.MemberService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
public class BoardRepositoryTest {

    @MockBean
    MemberService memberService;

    @MockBean
    CategoryService categoryService;

    @Test
    public void save(){

        //보드생성
        CreateRequest req =  CreateRequest.builder()
                .title(Title.from("tt"))
                .content(Content.from("dd"))
                .status(Status.TRUE)
                .categoryId(1L)
                .build();

    }
}
