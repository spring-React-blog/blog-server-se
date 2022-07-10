package com.my.blog.board.api;

import com.my.blog.board.domain.vo.Content;
import com.my.blog.board.domain.vo.Status;
import com.my.blog.board.domain.vo.Title;
import com.my.blog.board.dto.request.BoardRequest;
import com.my.blog.board.service.BoardService;
import com.my.blog.category.entity.Category;
import com.my.blog.category.service.CategoryService;
import com.my.blog.support.controller.RestDocsTestSupport;
import com.my.blog.member.entity.Member;
import com.my.blog.member.entity.vo.*;
import com.my.blog.member.service.MemberService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

class BoardControllerTest extends RestDocsTestSupport {
    private static final String API_URL = "/api/boards";
    @MockBean
    BoardService boardService;

    @Autowired
    CategoryService categoryService;

    @Autowired
    MemberService memberService;

    @BeforeEach
    public void initCategoryAndMember(){
        Category category = Category.builder().name("자바").build();
        categoryService.save(category);

        Member.builder()
                .email(Email.from("seung@googlg"))
                .password(Password.from("as23"))
                .name(Name.from("자바"))
                .nickName(NickName.from("dd"))
                .roleType(RoleType.USER)
                .build();
        categoryService.save(category);
    }

    @Test
    @DisplayName("게시물 작성")
    public void create() throws Exception {
        BoardRequest req = BoardRequest.builder()
                .title(Title.from("타이틀이 삼십자 이하."))
                .content(Content.from("content"))
                .categoryId(1L)
                .status(Status.TRUE)
                .memberId(1L)
                .build();

        String body = objectMapper.writeValueAsString(req);
        given(boardService.save( req.toEntity())).willReturn(1L);

       /* mockMvc.perform(post("/boards")
                        .content(body)
                        .contentType(MediaType.APPLICATION_JSON))
              .andExpect(status().isOk())
                .andDo(restDocs.document(
                        requestHeaders(
                                headerWithName(HttpHeaders.CONTENT_TYPE).description(MediaType.APPLICATION_JSON)
                        ),
                        requestFields(
                                fieldWithPath("title").description("제목").type(JsonFieldType.STRING),
                                fieldWithPath("content").description("내용").type(JsonFieldType.STRING),
                                fieldWithPath("status").description("공개여부").type(JsonFieldType.STRING),
                                fieldWithPath("memberId").description("회원ID").type(JsonFieldType.NUMBER),
                                fieldWithPath("categoryId").description("카테고리ID").type(JsonFieldType.NUMBER)
                        ),
                        responseHeaders(

                        ),
                        responseFields(
                                fieldWithPath("message").description("설명").type("설명")
                        )

                        ));*/
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