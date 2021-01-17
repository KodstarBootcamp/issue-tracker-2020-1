package com.kodstar.backend.service.impl;

import com.kodstar.backend.model.dto.Comment;
import com.kodstar.backend.model.entity.CommentEntity;
import com.kodstar.backend.model.entity.IssueEntity;
import com.kodstar.backend.repository.CommentRepository;
import com.kodstar.backend.repository.IssueRepository;
import com.kodstar.backend.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;

    @Autowired
    private IssueRepository issueRepository;


    @Override
    public Comment saveComment(Comment comment) {

        CommentEntity commentEntity = commentRepository.save(convertToEntity(comment));

        return convertToDTO(commentEntity);
    }

    @Override
    public Comment updateComment(Long id, Comment comment) {

        CommentEntity commentEntity = commentRepository.findById(id)
                .orElseThrow(()-> new EntityNotFoundException("Error: Comment not found for this id " + id));

        commentEntity.setContent(comment.getContent());
        commentEntity.setModified(LocalDateTime.now());
        commentEntity = commentRepository.save(commentEntity);
        return convertToDTO(commentEntity);
    }

    @Override
    public void deleteComment(Long id) {
        CommentEntity comment = commentRepository.findById(id)
                .orElseThrow(()-> new EntityNotFoundException("Error: Comment not found for this id " + id));

        commentRepository.delete(comment);
    }

    @Override
    public Comment findById(Long id) {
        CommentEntity commentEntity = commentRepository.findById(id)
                .orElseThrow(()-> new EntityNotFoundException("Error: Comment not found for this id " + id));

        return convertToDTO(commentEntity);
    }

    @Override
    public Collection<Comment> findAllByIssueEntityId(Long id) {

        IssueEntity issueEntity = issueRepository.findById(id).
                orElseThrow(()-> new EntityNotFoundException("Error: Issue not found for this id " + id));

        return commentRepository.findAllByIssueEntity(issueEntity).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Comment convertToDTO(CommentEntity commentEntity) {

        Comment comment = new Comment();
        comment.setId(commentEntity.getId());
        comment.setContent(commentEntity.getContent());
        comment.setIssueId(commentEntity.getIssueEntity().getId());
        return comment;
    }

    @Override
    public CommentEntity convertToEntity(Comment comment) {

        CommentEntity commentEntity = new CommentEntity();

        IssueEntity issueEntity = issueRepository.findById(comment.getIssueId())
                .orElseThrow(()-> new EntityNotFoundException("Error: Issue not found for this id " + comment.getIssueId()));

        commentEntity.setIssueEntity(issueEntity);
        commentEntity.setContent(comment.getContent());
        commentEntity.setId(comment.getId());
        commentEntity.setModified(LocalDateTime.now());

        return commentEntity;
    }
}
