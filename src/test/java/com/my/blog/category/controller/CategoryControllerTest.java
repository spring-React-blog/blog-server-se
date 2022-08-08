package com.my.blog.category.controller;

import com.my.blog.category.service.CategoryService;
import com.my.blog.category.vo.CreateRequest;
import com.my.blog.member.entity.vo.RoleType;
import com.my.blog.support.config.AuthUser;
import com.my.blog.support.controller.RestDocsTestSupport;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import static org.springframework.restdocs.headers.HeaderDocumentation.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


class CategoryControllerTest extends RestDocsTestSupport {
    @Autowired
    CategoryService categoryService;
    @Test
    @DisplayName("카테고리 생성")
    @AuthUser(username = "seung90@gmail.com" , password = "q1w2e3r4", role = RoleType.USER)
    public void validTest() throws Exception {
        CreateRequest req = CreateRequest.builder().name("스프링").build();
        String body = objectMapper.writeValueAsString(req);

       mockMvc.perform(post("/api/category")
                        .content(body)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
               )
                .andExpect(status().isOk())
                .andDo(restDocs.document(
                       requestHeaders(
                               headerWithName(HttpHeaders.CONTENT_TYPE).description(MediaType.APPLICATION_JSON)
                       ),
                       requestFields(
                               fieldWithPath("name").description("카테고리명").type(MediaType.APPLICATION_JSON)
                       ),
                       responseHeaders(
                               headerWithName(HttpHeaders.CONTENT_TYPE).description(MediaType.APPLICATION_JSON)
                       ),
                       responseFields(
                               fieldWithPath("id").description("카테고리번호").type(MediaType.APPLICATION_JSON)
                               ,fieldWithPath("name").description("카테고리명").type(MediaType.APPLICATION_JSON)
                       )
               ));

    }
}