package com.plus.forum.controllers;

import com.plus.forum.dto.RecentCommentDto;
import com.plus.forum.repositories.Comment;
import com.plus.forum.repositories.Topic;
import com.plus.forum.repositories.TopicCategory;
import com.plus.forum.services.*;
import com.plus.forum.util.AuthUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/topics")
public class TopicController {
    private final TopicService topicService;
    private final CommentService commentService;
    private final TimeFormatterService timeFormatterService;
    private final TextUtilsService textUtilsService;

    public TopicController(TopicService topicService, CommentService commentService, TimeFormatterService timeFormatterService, TextUtilsService textUtilsService) {
        this.topicService = topicService;
        this.commentService = commentService;
        this.timeFormatterService = timeFormatterService;
        this.textUtilsService = textUtilsService;
    }

    @GetMapping
    public String getAllTopics(
            @RequestParam(value = "sort", defaultValue = "desc") String sort,
            @RequestParam(value = "keyword", required = false) String keyword,
            Model model
    ) {
        List<Topic> topics = (keyword != null && !keyword.isBlank())
                ? topicService.searchTopicsByTitle(keyword, sort)
                : topicService.getAllTopics(sort);

        List<Comment> recentComments = commentService.getRecentComments();
        List<RecentCommentDto> formattedComments = new ArrayList<>();
        for (Comment comment : recentComments) {
            String formattedTime = timeFormatterService.formatTimeAgo(comment.getCreationDate());
            String snippet = textUtilsService.createSnippet(comment.getContent(), 20);
            formattedComments.add(new RecentCommentDto(comment, formattedTime, snippet));
        }

        model.addAttribute("topics", topics);
        model.addAttribute("recentComments", formattedComments);
        model.addAttribute("sort", sort);
        model.addAttribute("keyword", keyword);
        model.addAttribute("authenticated", AuthUtils.getCurrentUser() != null);
        return "topics/topic_list";
    }


    @GetMapping("/topic/{id}")
    public String getTopicById(@PathVariable Long id, Model model) {
        Topic topic = topicService.getTopicById(id);
        model.addAttribute("topic", topic);
        model.addAttribute("comments", commentService.getCommentsByTopicId(id));
        return "topics/topic";
    }

    @GetMapping("/create")
    public String createTopicForm(Model model) {
        model.addAttribute("topic", new Topic());
        model.addAttribute("categories", TopicCategory.values());
        return "topics/create_topic";
    }

    @PostMapping("/create")
    public String createTopic(@ModelAttribute Topic topic) {
        var user = AuthUtils.getCurrentUser();
        if(user == null) {
            return "redirect:/login";
        }
        topic.setAuthor(user);
        Topic newTopic = topicService.createTopic(topic);
        return "redirect:/topics/topic/" + newTopic.getId();
    }

    @GetMapping("/update/{id}")
    public String updateTopicForm(@PathVariable Long id, Model model) {
        Topic topic = topicService.getTopicById(id);
        model.addAttribute("topic", topic);
        model.addAttribute("categories", TopicCategory.values());
        return "topics/update_topic";
    }

    @PostMapping("/update/{id}")
    public String updateTopic(@PathVariable Long id, @ModelAttribute Topic topic) {
        var user = AuthUtils.getCurrentUser();
        if(user == null) {
            return "redirect:/login";
        }
        topic.setAuthor(user);
        topicService.updateTopic(id, topic);
        return "redirect:/topics/topic/" + id;
    }

    @GetMapping("/delete/{id}")
    public String deleteTopicForm(@PathVariable Long id,Model model) {
        Topic topic = topicService.getTopicById(id);
        model.addAttribute("topic", topic);
        return "topics/delete_topic";
    }

    @PostMapping("/delete/{id}")
    public String deleteTopic(@PathVariable Long id) {
        topicService.deleteTopic(id);
        return "redirect:/topics";
    }
}

