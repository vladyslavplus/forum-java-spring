package com.plus.forum.controllers;

import com.plus.forum.repositories.Comment;
import com.plus.forum.repositories.Topic;
import com.plus.forum.services.CommentService;
import com.plus.forum.services.TopicService;
import com.plus.forum.services.UserService;
import com.plus.forum.util.AuthUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/comments")
public class CommentController {
    private final CommentService commentService;
    private final TopicService topicService;

    public CommentController(CommentService commentService, TopicService topicService) {
        this.commentService = commentService;
        this.topicService = topicService;
    }

    @PostMapping("/add/{topicId}")
    public String addComment(@PathVariable Long topicId, @RequestParam String content) {
        Topic topic = topicService.getTopicById(topicId);

        var user = AuthUtils.getCurrentUser();
        if(user == null) {
            return "redirect:/login";
        }

        Comment comment = new Comment();
        comment.setContent(content);
        commentService.createComment(topicId, comment, user);
        return "redirect:/topics/topic/" + topicId;
    }

    @PostMapping("/update/{id}")
    public String updateComment(@PathVariable Long id, @RequestParam String content, @RequestParam Long topicId) {
        Topic topic = topicService.getTopicById(topicId);
        var user = AuthUtils.getCurrentUser();

        if(user == null) {
            return "redirect:/login";
        }

        Comment existingComment = commentService.getCommentById(id);
        if(existingComment == null) {
            return "redirect:/topics/topic/" + topicId;
        }

        existingComment.setContent(content);
        commentService.updateComment(id, existingComment);
        return "redirect:/topics/topic/" + topicId;
    }

    @PostMapping("/delete/{id}")
    public String deleteComment(@PathVariable Long id, @RequestParam Long topicId) {
        commentService.deleteComment(id);
        return "redirect:/topics/topic/" + topicId;
    }
}
