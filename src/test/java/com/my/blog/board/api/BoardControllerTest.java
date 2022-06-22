package com.my.blog.board.api;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.my.blog.board.controller.BoardController;
import com.my.blog.board.domain.vo.Status;
import com.my.blog.board.domain.vo.Content;
import com.my.blog.board.domain.vo.Title;
import com.my.blog.board.dto.request.BoardRequest;
import com.my.blog.board.dto.response.BoardResponse;
import com.my.blog.board.service.BoardSearchService;
import com.my.blog.board.service.BoardService;
import com.my.blog.category.entity.Category;
import com.my.blog.category.service.CategoryService;
import com.my.blog.global.common.response.ResponseEnvelope;
import com.my.blog.global.common.response.ResponseHeader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.InstanceOfAssertFactories.LONG;
import static org.assertj.core.api.InstanceOfAssertFactories.STRING;
import static org.springframework.restdocs.headers.HeaderDocumentation.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/*@SpringBootTest
@AutoConfigureMockMvc*/
//@DataJpaTest
@SpringBootTest
@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
//@WebMvcTest(BoardController.class)
@AutoConfigureRestDocs
class BoardControllerTest {
    private static final String API_URL = "/api/boards";
    @Autowired
    MockMvc mockMvc;
    @Autowired
    private WebApplicationContext context;
    @Autowired
    private RestDocumentationContextProvider documentationContextProvider;
    @Bean
    public MockMvc mockMvc() {
        return MockMvcBuilders.webAppContextSetup(context)
                .apply(
                        documentationConfiguration(documentationContextProvider)
                                .operationPreprocessors()
                                .withRequestDefaults(prettyPrint())
                                .withResponseDefaults(prettyPrint())
                )
                .apply(springSecurity())
                .addFilters(new CharacterEncodingFilter("UTF-8", true))
                .alwaysDo(print())
                .build();
    }
     @Autowired
    ObjectMapper objectMapper;

    @MockBean
    BoardService boardService;
    @MockBean
    BoardSearchService boardSearchService;
    @MockBean
    CategoryService categoryService;


    @BeforeEach
    public void createCategory(){
        Category category = Category.builder().name("자바").build();
        categoryService.save(category);
    }

    @Test
    @DisplayName("보드컨트롤러 create호출")
    public void create() throws Exception {
        BoardRequest req = new BoardRequest();

        req.setTitle(Title.of("타이틀이 삼십자 이하인 글"));
        req.setContent(Content.of("내용.."));
        req.setStatus(Status.TRUE);
        req.setCategoryId(1L);

        String body = objectMapper.writeValueAsString(req);

        mockMvc.perform(post("/board")
                        .content(body)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(document(API_URL ,
                        requestHeaders(
                                headerWithName(HttpHeaders.CONTENT_TYPE).description(MediaType.APPLICATION_JSON)
                        ),
                        requestFields(
                                fieldWithPath("title").description("제목").type(STRING),
                                fieldWithPath("content").description("내용").type(STRING),
                                fieldWithPath("status").description("공개여부").type(STRING),
                                fieldWithPath("memberId").description("회원ID").type(LONG),
                                fieldWithPath("categoryId").description("카테고리ID").type(LONG)
                        ),
                        responseHeaders(

                        ),
                        responseFields(
                                fieldWithPath("message").description("설명").type("설명")
                        )

                        ));
    }

    @Test
    @DisplayName("보드컨트롤러 create호출_title 글자 수 초과")
    public void validTestWithException() throws Exception {
        BoardRequest req = new BoardRequest();
        req.setTitle(Title.of("타이틀이 삼십자를 넘으면 메세지가 나온다. 지금 이 글자는 삼십자 이상이다."));
        req.setContent(Content.of("내용.."));
        req.setCategoryId(0L);

        String body = objectMapper.writeValueAsString(req);

        MvcResult result = mockMvc.perform(post("/board")
                .content(body)
                .contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        String message = result.getResolvedException().getMessage();
        assertThat(message).contains("30자 이하로 입력해주세요");

    }

}