package com.my.blog.category.service;

import com.my.blog.category.entity.Category;
import com.my.blog.category.repository.CategoryRepository;
import com.my.blog.category.vo.CategoryRequest;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class CategoryServiceTest {

    @Autowired
    CategoryService categoryService;

    @Autowired
    EntityManager entityManager;

    @Autowired
    CategoryRepository categoryRepository;

    @BeforeEach
    public void init() {
        categoryRepository.deleteAll();
        this.entityManager
                .createNativeQuery("ALTER TABLE category AUTO_INCREMENT = 1")
                .executeUpdate();
    }

    @AfterEach
    public void teardown() {
        categoryRepository.deleteAll();
        this.entityManager
                .createNativeQuery("ALTER TABLE category AUTO_INCREMENT = 1")
                .executeUpdate();
    }

    @Test
    @DisplayName("카테고리 생성")
    public void create(){
        CategoryRequest categoryRequest = CategoryRequest.builder().name("스프링").build();
        Category category = categoryRequest.toEntity();

        Long savedId = categoryService.save(category);
        assertThat(savedId).isEqualTo(1L);

    }

}