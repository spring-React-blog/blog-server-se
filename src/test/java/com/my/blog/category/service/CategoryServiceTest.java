package com.my.blog.category.service;

import com.my.blog.category.entity.Category;
import com.my.blog.category.vo.CategoryRequest;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
class CategoryServiceTest {

    @Autowired
    CategoryService categoryService;

    @Test
    @DisplayName("카테고리 생성")
    public void create(){
        CategoryRequest categoryRequest = CategoryRequest.builder().name("스프링").build();
        Category category = categoryRequest.toEntity();

        Long savedId = categoryService.save(category);
        assertThat(savedId).isEqualTo(1);

    }

}