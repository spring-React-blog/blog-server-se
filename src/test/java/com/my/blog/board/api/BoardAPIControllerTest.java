package com.my.blog.board.api;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.my.blog.board.domain.vo.Status;
import com.my.blog.board.domain.vo.Content;
import com.my.blog.board.domain.vo.Title;
import com.my.blog.board.dto.request.BoardRequest;
import com.my.blog.board.dto.response.BoardResponse;
import com.my.blog.category.entity.Category;
import com.my.blog.category.service.CategoryService;
import com.my.blog.common.response.ResponseEnvelope;
import com.my.blog.common.response.ResponseHeader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

//@WebMvcTest(BoardAPIController.class)
@SpringBootTest
@AutoConfigureMockMvc
@Rollback(value = false)
class BoardAPIControllerTest {
    @Autowired
    MockMvc mvc;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    CategoryService categoryService;

    @BeforeEach
    public void createCategory(){
        Category category = Category.builder().name("자바").build();
        categoryService.save(category);
    }

    @Test
    @DisplayName("보드컨트롤러 create호출")
    public void validTest() throws Exception {
        BoardRequest req = new BoardRequest();

        req.setTitle(Title.of("타이틀이 삼십자 이하인 글"));
        req.setContent(Content.of("내용.."));
        req.setStatus(Status.TRUE);
        req.setCategoryId(1L);

        String body = objectMapper.writeValueAsString(req);

        MvcResult result = mvc.perform(post("/board")
                        .content(body)
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn();
        String contentAsString = result.getResponse().getContentAsString();

       /* ResponseEnvelope<BoardResponse> responseEnvelope = objectMapper.readValue(contentAsString, new TypeReference<ResponseEnvelope<BoardResponse>>() {
        });*/
        JavaType type = objectMapper.getTypeFactory().constructParametricType(ResponseEnvelope.class, BoardResponse.class);
        ResponseEnvelope<BoardResponse> responseEnvelope = objectMapper.readValue(contentAsString, type);


        ResponseHeader header = responseEnvelope.getHeader();
        assertThat(header.getCode()).isEqualTo("200");
        assertThat(header.getMessage()).isEqualTo("success");

        BoardResponse response =(BoardResponse) responseEnvelope.getBody();
        Long id = response.getId();
        System.out.println("id>>"+id);//0


    }

    @Test
    @DisplayName("보드컨트롤러 create호출_title 글자 수 초과")
    public void validTestWithException() throws Exception {
        BoardRequest req = new BoardRequest();
        req.setTitle(Title.of("타이틀이 삼십자를 넘으면 메세지가 나온다. 지금 이 글자는 삼십자 이상이다."));
        req.setContent(Content.of("내용.."));
        req.setCategoryId(0L);

        String body = objectMapper.writeValueAsString(req);

        MvcResult result = mvc.perform(post("/board")
                .content(body)
                .contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        String message = result.getResolvedException().getMessage();
        assertThat(message).contains("30자 이하로 입력해주세요");

    }

}