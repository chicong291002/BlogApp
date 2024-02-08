package com.springboot.blog.springbootblogrestapi.services.impl;

import com.springboot.blog.springbootblogrestapi.dto.CategoryDto;
import com.springboot.blog.springbootblogrestapi.dto.PostDto;
import com.springboot.blog.springbootblogrestapi.entity.Category;
import com.springboot.blog.springbootblogrestapi.entity.Comment;
import com.springboot.blog.springbootblogrestapi.entity.Post;
import com.springboot.blog.springbootblogrestapi.exception.BlogAPIException;
import com.springboot.blog.springbootblogrestapi.exception.ResourceNotFoundException;
import com.springboot.blog.springbootblogrestapi.repository.CategoryRepository;
import com.springboot.blog.springbootblogrestapi.services.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public CategoryDto createCategory(CategoryDto categoryDto) {
        //convert Dto to entity
        Category category = mapToCategory(categoryDto);
        Category saveCategory = categoryRepository.save(category);
        return mapToCategoryDto(saveCategory);
    }

    @Override
    public CategoryDto getCategoryById(Long categoryId) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category","Id",categoryId));
        return mapToCategoryDto(category);
    }

    @Override
    public List<CategoryDto> getAllCategories() {
        List<Category> category = categoryRepository.findAll();
        //covert list of comment entities to  list of comment dto
        return category.stream().map(comment -> mapToCategoryDto(comment)).collect(Collectors.toList());
    }

    @Override
    public CategoryDto updateCategory(Long categoryId, CategoryDto categoryDto) {
        //retrive post entity by id

        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category","id",categoryId));

        category.setName(categoryDto.getName());
        category.setDescription(categoryDto.getDescription());

        Category updateCategory = categoryRepository.save(category);
        return mapToCategoryDto(updateCategory);
    }

    @Override
    public void deleteCategory(Long categoryId) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category","Id",categoryId));
        categoryRepository.delete(category);
    }

    //convert Entity to DTO
    public CategoryDto mapToCategoryDto(Category category) {
        return modelMapper.map(category,CategoryDto.class);
    }

    //convert DTO To Entity
    public Category mapToCategory(CategoryDto categoryDto) {
        return modelMapper.map(categoryDto,Category.class);
    }
}
