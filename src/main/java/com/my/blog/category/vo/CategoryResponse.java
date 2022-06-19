package com.my.blog.category.vo;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CategoryResponse {
    private Long id;
    private String name;

    public static CategoryResponseBuilder builder(){return new CategoryResponseBuilder(); }

    private CategoryResponse(CategoryResponseBuilder builder){
        this.id = builder.getId();
        this.name = builder.getName();
    }

    @Getter
    public static class CategoryResponseBuilder{
        private Long id;
        private String name;
        CategoryResponseBuilder(){}

        public CategoryResponseBuilder id(Long id){
            this.id = id;
            return this;
        }

        public CategoryResponseBuilder name(String name){
            this.name = name;
            return this;
        }

        public CategoryResponse build(){
            return new CategoryResponse(this);
        }

    }
}
