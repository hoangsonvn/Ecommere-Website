package com.demo6.shop.service;

import com.demo6.shop.entity.Comment;

import java.util.List;

public interface CommentService {
    List<Comment> findAllByProductIdAutho(Long id) ;
    void showComment(Long id);
    void persist(Comment comment);
    List<Comment> findAllByProductId(Long id);
    void delete(Long id);
}
