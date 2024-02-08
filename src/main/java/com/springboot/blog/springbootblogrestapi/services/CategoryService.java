package com.springboot.blog.springbootblogrestapi.services;

import com.springboot.blog.springbootblogrestapi.dto.CategoryDto;
import com.springboot.blog.springbootblogrestapi.dto.CommentDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CategoryService {
    CategoryDto createCategory(CategoryDto categoryDto);

    CategoryDto getCategoryById(Long categoryId);

    List<CategoryDto> getAllCategories();

    CategoryDto updateCategory(Long categoryId, CategoryDto categoryDto);

    void deleteCategory(Long categoryId);
}
