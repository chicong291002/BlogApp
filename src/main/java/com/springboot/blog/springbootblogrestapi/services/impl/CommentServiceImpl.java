package com.springboot.blog.springbootblogrestapi.services.impl;

import com.springboot.blog.springbootblogrestapi.dto.CommentDto;
import com.springboot.blog.springbootblogrestapi.entity.Comment;
import com.springboot.blog.springbootblogrestapi.entity.Post;
import com.springboot.blog.springbootblogrestapi.exception.BlogAPIException;

import com.springboot.blog.springbootblogrestapi.exception.ResourceNotFoundException;
import com.springboot.blog.springbootblogrestapi.repository.CommentRepository;
import com.springboot.blog.springbootblogrestapi.repository.PostRepository;
import com.springboot.blog.springbootblogrestapi.services.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private PostRepository postRepository;

    @Autowired
    private ModelMapper mapper;

    @Override
    public CommentDto createComment(Long postId, CommentDto commentDto) {
        Comment comment = mapToComment(commentDto);

        //retrive post entity by id

        Post post = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post","id",postId));

        //set post to comment entity
        comment.setPost(post);

        //comment entity to DATABASE
        Comment newComment = commentRepository.save(comment);
        return mapToCommentDto(newComment);
    }

    @Override
    public List<CommentDto> getCommentByPostId(Long postId) {
        //retrive comment entity by id
        List<Comment> comments = commentRepository.findByPostId(postId);
        //covert list of comment entities to  list of comment dto
        return comments.stream().map(comment -> mapToCommentDto(comment)).collect(Collectors.toList());
    }

    @Override
    public CommentDto getCommentById(Long postId, Long commentId) {
        //retrive post entity by id

        Post post = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post","id",postId));

        //retrive comment entity by id

        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new ResourceNotFoundException("Comment","id",commentId));
        if(!comment.getPost().getId().equals(post.getId())){
            throw new BlogAPIException(HttpStatus.BAD_REQUEST,"Comment does not belong to post");
        }
        return mapToCommentDto(comment);
    }

    @Override
    public CommentDto updateComment(Long postId, Long commentId, CommentDto commentRequest) {
        //retrive post entity by id

        Post post = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post","id",postId));

        //retrive comment entity by id

        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new ResourceNotFoundException("Comment","id",commentId));
        if(!comment.getPost().getId().equals(post.getId())){
            throw new BlogAPIException(HttpStatus.BAD_REQUEST,"Comment does not belong to post");
        }
        comment.setName(commentRequest.getName());
        comment.setEmail(comment.getEmail());
        comment.setBody(comment.getBody());
        Comment updateComment = commentRepository.save(comment);
        return mapToCommentDto(updateComment);
    }

    @Override
    public void deleteComment(Long postId, Long commentId) {
        //retrive post entity by id

        Post post = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post","id",postId));

        //retrive comment entity by id

        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new ResourceNotFoundException("Comment","id",commentId));
        if(!comment.getPost().getId().equals(post.getId())){
            throw new BlogAPIException(HttpStatus.BAD_REQUEST,"Comment does not belong to post");
        }
        commentRepository.delete(comment);
    }

    //convert Entity to DTO
    public CommentDto mapToCommentDto(Comment comment) {
        return mapper.map(comment,CommentDto.class);
    }

    //convert DTO To Entity
    public Comment mapToComment(CommentDto CommentDto) {
        return mapper.map(CommentDto,Comment.class);
    }
}
