package com.my.blog.board.api;

import com.my.blog.board.domain.Board;
import com.my.blog.board.domain.BoardImage;
import com.my.blog.board.domain.vo.Content;
import com.my.blog.board.domain.vo.Status;
import com.my.blog.board.domain.vo.Title;
import com.my.blog.board.dto.request.BoardRequest;
import com.my.blog.board.dto.response.BoardResponse;
import com.my.blog.board.service.BoardService;
import com.my.blog.category.entity.Category;
import com.my.blog.category.service.CategoryService;
import com.my.blog.global.common.utils.StringUtil;
import com.my.blog.member.service.dto.MemberDTO;
import com.my.blog.support.config.AuthUser;
import com.my.blog.support.controller.RestDocsTestSupport;
import com.my.blog.member.entity.Member;
import com.my.blog.member.entity.vo.*;
import com.my.blog.member.service.MemberService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.restdocs.headers.HeaderDocumentation;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.restdocs.payload.PayloadDocumentation;
import org.springframework.restdocs.request.RequestDocumentation;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.headers.HeaderDocumentation.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.partWithName;
import static org.springframework.restdocs.request.RequestDocumentation.requestParts;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class BoardControllerTest extends RestDocsTestSupport {

    @MockBean
    BoardService boardService;

    @MockBean
    CategoryService categoryService;

    @MockBean
    MemberService memberService;

    BoardRequest boardRequest;
    Category category;
    Member member;
    List<BoardImage> boardImages = new ArrayList<>();

    @BeforeEach
    public void initCategoryAndMember(){

        MemberDTO memberDTO = MemberDTO.builder()
                .id(1L)
                .email(Email.from("seung@gmail.com"))
                .build();

        given(memberService.findByEmail("seung@gmail.com"))
                .willReturn(memberDTO);

        member = Member.builder()
                .id(1L).email(Email.from("seung@gmail.com")).build();

        boardRequest = BoardRequest.builder()
                .title(Title.from("타이틀이 삼십자 이하."))
                .content(Content.from("content"))
                .categoryId(1L)
                .status(Status.TRUE)
                .memberId(1L)
                .build();




    }
    @Test
    @DisplayName("게시물 작성")
    @AuthUser(username = "seung@gmail.com" , password = "q1w2e3r4", role = RoleType.USER)
    public void save() throws Exception {
        category = Category.builder().id(1L).name("자바").build();
        given(categoryService.findById(1L)).willReturn(category);

        String body = objectMapper.writeValueAsString(boardRequest);

        Board board = Board.builder().id(1L)
                .title(Title.from("타이틀이 삼십자 이하."))
                .content(Content.from("content"))
                .category(category)
                .status(Status.TRUE)
                .member(member)
                .build();

        given(boardService.save( boardRequest.toBoardEntity(boardImages,member,category) )).willReturn(board);

        mockMvc.perform(post("/api/boards")
                        .content(body)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(restDocs.document(
                        requestHeaders(
                                headerWithName(HttpHeaders.CONTENT_TYPE).description(MediaType.MULTIPART_FORM_DATA)
                        ),
                        requestFields(
                                fieldWithPath("title").description("제목").type(JsonFieldType.STRING),
                                fieldWithPath("content").description("내용").type(JsonFieldType.STRING),
                                fieldWithPath("status").description("공개여부").type(JsonFieldType.STRING),
                                fieldWithPath("memberId").description("회원ID").type(JsonFieldType.NUMBER),
                                fieldWithPath("files").optional().description("첨부파일"),
                                fieldWithPath("categoryId").description("카테고리ID").type(JsonFieldType.NUMBER)

                        ),
                        responseHeaders(

                        ),
                        responseFields(
                                fieldWithPath("id").description("아이디").type(JsonFieldType.NUMBER),
                                fieldWithPath("title").description("제목").type(JsonFieldType.STRING),
                                fieldWithPath("content").description("내용").type(JsonFieldType.STRING),
                                fieldWithPath("categoryName").description("카테고리명").type(JsonFieldType.STRING),
                                fieldWithPath("email").description("이메일").type(JsonFieldType.STRING),
                                fieldWithPath("viewCount").description("조회수").type(JsonFieldType.NUMBER)
                        )
                ));
    }

    /*@Test
    @DisplayName("보드컨트롤러 create호출_title 글자 수 초과")
    public void validTestWithException() throws Exception {
        BoardRequest req = BoardRequest.builder()
                .title(Title.from("타이틀이 삼십자를 넘으면 에러가 나야한다.타이틀이 삼십자를 넘으면 에러가 나야한다.타이틀이 삼십자를 넘으면 에러가 나야한다."))
                .content(Content.from("content"))
                .categoryId(1L)
                .status(Status.TRUE)
                .memberId(1L)
                .build();

        String body = objectMapper.writeValueAsString(req);

        MvcResult result = mockMvc.perform(post("/board")
                .content(body)
                .contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        String message = result.getResolvedException().getMessage();
        assertThat(message).contains("30자 이하로 입력해주세요");

    }*/

}