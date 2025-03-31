package com.plus.forum.controllers;

import com.plus.forum.repositories.Comment;
import com.plus.forum.services.CommentService;
import com.plus.forum.services.TopicService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/comments")
public class CommentController {
    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping("/add/{topicId}")
    public String addComment(@PathVariable Long topicId, @RequestParam String content) {
        Comment comment = new Comment();
        comment.setContent(content);
        commentService.createComment(topicId, comment);
        return "redirect:/topics/topic/" + topicId;
    }

    @PostMapping("/update/{id}")
    public String updateComment(@PathVariable Long id, @RequestParam String content, @RequestParam Long topicId) {
        Comment updatedComment = new Comment();
        updatedComment.setContent(content);
        commentService.updateComment(id, updatedComment);
        return "redirect:/topics/topic/" + topicId;
    }

    @PostMapping("/delete/{id}")
    public String deleteComment(@PathVariable Long id, @RequestParam Long topicId) {
        commentService.deleteComment(id);
        return "redirect:/topics/topic/" + topicId;
    }
}
