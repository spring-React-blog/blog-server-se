package com.my.blog.category.service;

import com.my.blog.category.entity.Category;
import com.my.blog.category.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Transactional
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public Long save(Category category){
        System.out.println("service id > "+category.getId());
        Category savedCategory = categoryRepository.save(category);
        return savedCategory.getId();
    }

    public Category findById(Long id){
        return categoryRepository.findById(id).orElseThrow(IllegalArgumentException::new);
    }
}
