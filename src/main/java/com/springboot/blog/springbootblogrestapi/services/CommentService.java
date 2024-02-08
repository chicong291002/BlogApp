package com.springboot.blog.springbootblogrestapi.services;

import com.springboot.blog.springbootblogrestapi.dto.CommentDto;
import com.springboot.blog.springbootblogrestapi.entity.Comment;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CommentService {
    CommentDto createComment(Long postId,CommentDto commentDto);
    List<CommentDto> getCommentByPostId(Long postId);

    CommentDto getCommentById(Long postId,Long commentId);
    CommentDto updateComment(Long postId,Long commentId,CommentDto commentRequest);

    void deleteComment(Long postId,Long commentId);
}
