package com.my.blog.category.controller;

import com.my.blog.category.entity.Category;
import com.my.blog.category.service.CategoryService;
import com.my.blog.category.vo.CreateRequest;
import com.my.blog.category.vo.CategoryResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class CategoryController {
    private final CategoryService categoryService;

    @PostMapping("/category")
    public ResponseEntity<CategoryResponse> createCategory(@RequestBody CreateRequest request)
    {
        Category category = request.toEntity();
        Long id = categoryService.save(category);

        CategoryResponse response =  CategoryResponse.builder()
                .id(id)
                .build();

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
