package com.my.blog.category.service;

import com.my.blog.category.entity.Category;
import com.my.blog.category.error.CategoryError;
import com.my.blog.category.repository.CategoryRepository;
import com.my.blog.global.common.exception.CommonException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Transactional
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public Long save(Category category){
        Category savedCategory = categoryRepository.save(category);
        return savedCategory.getId();
    }

    public Category findById(Long id){
        return categoryRepository.findById(id).orElseThrow(()-> new CommonException(CategoryError.NOT_FOUND));
    }
}
