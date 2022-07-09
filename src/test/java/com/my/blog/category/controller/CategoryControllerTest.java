package com.my.blog.category.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.my.blog.category.vo.CategoryRequest;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest
@AutoConfigureMockMvc
@Rollback(value = false)
@Disabled
class CategoryControllerTest {

    @Autowired
    MockMvc mvc;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    @DisplayName("카테고리컨트롤러 create호출")
    public void validTest() throws Exception {
        CategoryRequest req = CategoryRequest.builder().name("스프링").build();

        String body = objectMapper.writeValueAsString(req);

        MvcResult result = mvc.perform(post("/category")
                        .content(body)
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        String contentAsString = result.getResponse().getContentAsString();


       /* JavaType type = objectMapper.getTypeFactory().constructParametricType(ResponseEnvelope.class, CategoryResponse.class);
        ResponseEnvelope<CategoryResponse> responseEnvelope = objectMapper.readValue(contentAsString, type);


        ResponseHeader header = responseEnvelope.getHeader();
        assertThat(header.getCode()).isEqualTo("200");
        assertThat(header.getMessage()).isEqualTo("success");

        CategoryResponse response =(CategoryResponse) responseEnvelope.getBody();
        Long id = response.getId();
        System.out.println("id>>"+id);//0*/


    }
}