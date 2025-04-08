package com.plus.forum.services;

import com.plus.forum.repositories.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CommentService {
    private final CommentRepository commentRepository;
    private final TopicRepository topicRepository;

    public CommentService(CommentRepository commentRepository, TopicRepository topicRepository) {
        this.commentRepository = commentRepository;
        this.topicRepository = topicRepository;
    }

    public List<Comment> getCommentsByTopicId(Long topicId) {
        return commentRepository.findByTopicId(topicId);
    }

    public Comment getCommentById(Long commentId) {
        Optional<Comment> comment = commentRepository.findById(commentId);
        return comment.orElse(null);
    }

    public Comment createComment(Long topicId, Comment comment, User user) {
        Optional<Topic> topic = topicRepository.findById(topicId);
        if (topic.isPresent()) {
            comment.setTopic(topic.get());
            comment.setUser(user);
            return commentRepository.save(comment);
        }
        return null;
    }

    public void updateComment(Long id, Comment newComment) {
        Comment oldComment = getCommentById(id);
        if (oldComment != null) {
            oldComment.setContent(newComment.getContent());
            commentRepository.save(oldComment);
        }
    }

    public void deleteComment(Long id) {
        commentRepository.deleteById(id);
    }
}
