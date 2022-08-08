package com.my.blog.category.given;

import com.my.blog.category.entity.Category;

public class CategoryGiven {
    public static Category getCategoryEntity(){
        return  Category.builder().id(1L).name("자바").build();
    }
}
