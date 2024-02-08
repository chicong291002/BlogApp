package com.springboot.blog.springbootblogrestapi.controller;

import com.springboot.blog.springbootblogrestapi.dto.PostDto;
import com.springboot.blog.springbootblogrestapi.dto.PostResponse;
import com.springboot.blog.springbootblogrestapi.services.PostService;
import com.springboot.blog.springbootblogrestapi.utils.AppConstants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
@Tag(name = "CRUD REST API FOR POST RESOURCE")
public class PostController {
    @Autowired
    private PostService postService;

    @Operation(summary = "Create Post REST API",
            description = "Create Post REST API is user to save post into database")

    @ApiResponse(responseCode = "201",description = "HTTP STATUS 201 CREATED")

    @SecurityRequirement(
            name = "Bearer Authentication"
    )

    //create blog post REST API
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<PostDto> createPost(@Valid @RequestBody PostDto postDto) {
        return new ResponseEntity<>(postService.createPost(postDto), HttpStatus.CREATED);
    }


    @Operation(summary = "GET ALL Post REST API",
            description = "GET ALL Post REST API is user to fetch all the posts from the database")
    @ApiResponse(responseCode = "200",description = "HTTP STATUS 200 SUCCESS")
    //get all  posts REST API
    @GetMapping
    public PostResponse getAllPosts(
            @RequestParam(value = "pageNo", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = AppConstants.DEFAULT_SORT_BY, required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = AppConstants.DEFAULT_SORT_DIRECTION, required = false) String sortDir
    ) {
        return postService.getAllPosts(pageNo, pageSize, sortBy, sortDir);
    }


    @Operation(summary = "Get Post By Id REST API",
            description = "Get Post By Id REST API is user to get single post from the database")
    @ApiResponse(responseCode = "200",description = "HTTP STATUS 200 SUCCESS")

    //Build Get posts By Id REST API
    @GetMapping("/{id}")
    public ResponseEntity<PostDto> getPostById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(postService.getPostById(id));
    }

    @SecurityRequirement(
            name = "Bearer Authentication"
    )


    @Operation(summary = "Update Post REST API",
            description = "Update Post REST API is user to update particular post in the database")
    @ApiResponse(responseCode = "200",description = "HTTP STATUS 200 SUCCESS")

    //Build Update posts REST API
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("{id}")
    public ResponseEntity<PostDto> updatePost(@Valid @PathVariable("id") Long postId, @RequestBody PostDto postDto) {
        PostDto post = postService.updatePost(postId, postDto);
        return ResponseEntity.ok(post);
    }

    @SecurityRequirement(
            name = "Bearer Authentication"
    )

    @Operation(summary = "Delete Post REST API",
            description = "Delete Post REST API is user to Delete particular post in the database")
    @ApiResponse(responseCode = "200",description = "HTTP STATUS 200 SUCCESS")

    //Build Delete Post REST API
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("{id}")
    public ResponseEntity<String> deletePost(@PathVariable("id") Long employeeId) {
        postService.deletePost(employeeId);
        return ResponseEntity.ok("Post deleted successfully!");
    }

    //Build Get Posts By Category REST API
    @GetMapping("/categories/{id}")
    public ResponseEntity<List<PostDto>> getPostsByCategory(@PathVariable("id") Long categoryId) {
        List<PostDto> postDtos = postService.getPostsByCategory(categoryId);
        return ResponseEntity.ok(postDtos);
    }
}
