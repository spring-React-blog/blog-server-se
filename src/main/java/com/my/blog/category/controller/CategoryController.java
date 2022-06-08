package com.my.blog.category.controller;

import com.my.blog.category.entity.Category;
import com.my.blog.category.service.CategoryService;
import com.my.blog.category.vo.CategoryRequest;
import com.my.blog.category.vo.CategoryResponse;
import com.my.blog.common.response.ResponseEnvelope;
import com.my.blog.common.response.ResponseHeader;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;

    @PostMapping("/category")
    public ResponseEnvelope<CategoryResponse> createCategory(@RequestBody CategoryRequest request)
    {
        Category category = request.toEntity();
        Long id = categoryService.save(category);

        ResponseHeader header = ResponseHeader.builder()
                .code("200")
                .message("success")
                .build();

        CategoryResponse body =  CategoryResponse.builder()
                .id(id)
                .build();

        return new ResponseEnvelope(header,body);
    }

}
