package com.my.blog.board.service;

import com.my.blog.board.domain.vo.Content;
import com.my.blog.board.domain.vo.Status;
import com.my.blog.board.domain.vo.Title;
import com.my.blog.board.dto.request.BoardRequest;
import com.my.blog.category.entity.Category;
import com.my.blog.category.service.CategoryService;
import com.my.blog.category.vo.CategoryRequest;
import com.my.blog.member.given.MemberGiven;
import com.my.blog.member.controller.ModelMapper;
import com.my.blog.member.service.MemberService;
import com.my.blog.member.service.dto.MemberDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class BoardRepositoryTest {

    @Autowired
    BoardService boardService;

    @Autowired
    MemberService memberService;

    @Autowired
    CategoryService categoryService;


    @Test
    public void save(){
        MemberDTO member = ModelMapper.createMember(MemberGiven.getMemberRequest());
        memberService.save(member);

        //카테고리 mock 정의
        CategoryRequest categoryRequest = CategoryRequest.builder().name("스프링").build();
        Category category = categoryRequest.toEntity();
        categoryService.save(category);

        //보드생성
        BoardRequest req =  BoardRequest.builder()
                .title(Title.from("tt"))
                .content(Content.from("dd"))
                .status(Status.TRUE)
                .categoryId(1L)
                .memberId(1L)
                .build();

       // Board board = req.toEntity();
        MemberDTO memberDTO = memberService.findById(req.getMemberId());

      /*  board.setMember(EntityMapper.toEntity(memberDTO));
        board.setCategory(category);

        boardService.save(board);*/
    }
}
