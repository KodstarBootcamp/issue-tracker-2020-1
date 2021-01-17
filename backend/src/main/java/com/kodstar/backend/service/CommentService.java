package com.kodstar.backend.service;

import com.kodstar.backend.model.dto.Comment;
import com.kodstar.backend.model.entity.CommentEntity;
import com.kodstar.backend.utils.Converter;

import java.util.Collection;

public interface CommentService extends Converter<Comment,CommentEntity> {

    Comment saveComment (Comment comment);
    Comment updateComment (Long id, Comment comment);
    void deleteComment (Long id);
    Comment findById (Long id);
    Collection<Comment> findAllByIssueEntityId(Long id);
}
