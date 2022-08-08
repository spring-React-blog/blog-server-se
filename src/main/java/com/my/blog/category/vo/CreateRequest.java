package com.my.blog.category.vo;

import com.my.blog.category.entity.Category;
import lombok.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class CreateRequest {
    private String name;

    public Category toEntity() {
        return Category.builder()
                .name(this.name)
                .build();
    }
    @Builder
    public CreateRequest(String name){
        this.name=name;
    }
}
