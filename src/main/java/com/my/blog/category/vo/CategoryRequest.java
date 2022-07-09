package com.my.blog.category.vo;

import com.my.blog.category.entity.Category;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Builder
public class CategoryRequest {
    private Long id;
    private String name;

    public Category toEntity() {
        return Category.builder()
                .name(this.name)
                .build();
    }
}
