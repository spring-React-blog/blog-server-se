package com.my.blog.category.vo;

import com.my.blog.category.entity.Category;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class CategoryRequest {
    private Long id;
    private String name;

    public Category toEntity() {
        System.out.println("this.nemae > " + this.name);
        return Category.builder()
                .name(this.name)
                .build();
    }
}
