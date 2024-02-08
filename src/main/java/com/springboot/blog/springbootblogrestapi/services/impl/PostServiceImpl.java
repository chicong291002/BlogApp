package com.springboot.blog.springbootblogrestapi.services.impl;

import com.springboot.blog.springbootblogrestapi.dto.CommentDto;
import com.springboot.blog.springbootblogrestapi.dto.PostDto;
import com.springboot.blog.springbootblogrestapi.dto.PostResponse;
import com.springboot.blog.springbootblogrestapi.entity.Category;
import com.springboot.blog.springbootblogrestapi.entity.Comment;
import com.springboot.blog.springbootblogrestapi.entity.Post;
import com.springboot.blog.springbootblogrestapi.exception.ResourceNotFoundException;
import com.springboot.blog.springbootblogrestapi.repository.CategoryRepository;
import com.springboot.blog.springbootblogrestapi.repository.PostRepository;
import com.springboot.blog.springbootblogrestapi.services.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.awt.print.Pageable;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {

    private PostRepository _postRepository;
    private ModelMapper mapper;

    private CategoryRepository categoryRepository;
    public PostServiceImpl(PostRepository postRepository,ModelMapper mapper,CategoryRepository categoryRepository){
        this._postRepository = postRepository;
        this.mapper = mapper;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public PostDto createPost(PostDto postDto) {
        //get post by id from the database
        Category category = categoryRepository.findById(postDto.getCategoryId()).orElseThrow(() -> new ResourceNotFoundException("Category","Id",postDto.getCategoryId()));

        //convert Dto to entity
        Post post = mapToPost(postDto);
        post.setCategory(category);
        Post savePost = _postRepository.save(post);
        return mapToPostDto(savePost);
    }

    @Override
    public PostDto getPostById(Long PostId) {
        Post post = _postRepository.findById(PostId).orElseThrow(() -> new ResourceNotFoundException("Post","Id",PostId));
        return mapToPostDto(post);
    }

    @Override
    public PostResponse getAllPosts(int pageNo,int pageSize,String sortBy,String sortDir) {

        Sort sort =  sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();

        //create Pageble instance
        PageRequest pageRequest = PageRequest.of(pageNo, pageSize, sort);
        Page<Post> posts = _postRepository.findAll(pageRequest);

        //get content for page object
        List<Post>listOfPosts = posts.getContent();
        List<PostDto> content =  listOfPosts.stream().map(post -> mapToPostDto(post)).collect(Collectors.toList());

        PostResponse postResponse = new PostResponse();
        postResponse.setContent(content);
        postResponse.setPageNo(posts.getNumber());
        postResponse.setPageSize(posts.getSize());
        postResponse.setTotalElements(posts.getTotalElements());
        postResponse.setTotalPages(posts.getTotalPages());
        postResponse.setLast(posts.isLast());
        return postResponse;
    }

    @Override
    public PostDto updatePost(Long PostId, PostDto postDto) {

        //get post by id from the database
        Post post = _postRepository.findById(PostId).orElseThrow(() -> new ResourceNotFoundException("Post","Id",PostId));


        Category category = categoryRepository.findById(postDto.getCategoryId()).orElseThrow(() -> new ResourceNotFoundException("Category","Id",postDto.getCategoryId()));

        post.setTitle(postDto.getTitle());
        post.setDescription(postDto.getDescription());
        post.setContent(postDto.getContent());
        post.setCategory(category);
        Post updatePost = _postRepository.save(post);
        return mapToPostDto(updatePost);
    }

    @Override
    public void deletePost(Long postId) {
        Post post = _postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post","Id",postId));
        _postRepository.delete(post);
    }

    @Override
    public List<PostDto> getPostsByCategory(Long categoryId) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category","Id",categoryId));
        List<Post>posts = _postRepository.findByCategoryId(categoryId);
        return posts.stream().map((post -> mapToPostDto(post))).collect(Collectors.toList());
    }

    //convert Entity to DTO
    public PostDto mapToPostDto(Post post) {
        return mapper.map(post,PostDto.class);
    }

    //convert DTO To Entity
    public Post mapToPost(PostDto postDto) {
        return mapper.map(postDto,Post.class);
    }
}
