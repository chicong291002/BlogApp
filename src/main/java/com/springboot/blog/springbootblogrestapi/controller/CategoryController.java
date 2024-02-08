package com.springboot.blog.springbootblogrestapi.controller;

import com.springboot.blog.springbootblogrestapi.dto.CategoryDto;
import com.springboot.blog.springbootblogrestapi.dto.CommentDto;
import com.springboot.blog.springbootblogrestapi.services.CategoryService;
import com.springboot.blog.springbootblogrestapi.utils.AppConstants;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {
    @Autowired
    private CategoryService CategoryService;

    //create blog Category REST API
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CategoryDto> createCategory(@Valid @RequestBody CategoryDto CategoryDto){
        return new ResponseEntity<>(CategoryService.createCategory(CategoryDto), HttpStatus.CREATED);
    }

    //Build Get Categorys By Id REST API
    @GetMapping("/{id}")
    public ResponseEntity<CategoryDto> getCategoryById(@PathVariable("id") Long id){
        return ResponseEntity.ok(CategoryService.getCategoryById(id));
    }

    @GetMapping
    public List<CategoryDto> getAllCategories(){
        return CategoryService.getAllCategories();
    }

    //Build Update Categorys REST API
    @PutMapping("{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CategoryDto> updateCategory(@Valid @PathVariable("id") Long CategoryId,@RequestBody CategoryDto CategoryDto) {
        CategoryDto Category = CategoryService.updateCategory(CategoryId,CategoryDto);
        return ResponseEntity.ok(Category);
    }

    //Build Delete Category REST API
    @DeleteMapping("{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> deleteCategory(@PathVariable("id") Long employeeId) {
        CategoryService.deleteCategory(employeeId);
        return ResponseEntity.ok("Category deleted successfully!");
    }
}
