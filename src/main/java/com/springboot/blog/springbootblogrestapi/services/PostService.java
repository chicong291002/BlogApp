package com.springboot.blog.springbootblogrestapi.services;

import com.springboot.blog.springbootblogrestapi.dto.PostDto;
import com.springboot.blog.springbootblogrestapi.dto.PostResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface PostService {
    PostDto createPost(PostDto postDto);
    PostDto getPostById(Long PostId);
    PostResponse getAllPosts(int pageNo, int pageSize, String sortBy, String sortDir);
    PostDto updatePost(Long PostId, PostDto postDto);
    void deletePost(Long postId);
    List<PostDto> getPostsByCategory(Long categoryId);
}
