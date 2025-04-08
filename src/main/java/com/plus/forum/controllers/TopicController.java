package com.plus.forum.controllers;

import com.plus.forum.repositories.Topic;
import com.plus.forum.services.CommentService;
import com.plus.forum.services.TopicService;
import com.plus.forum.services.UserService;
import com.plus.forum.util.AuthUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/topics")
public class TopicController {
    private final TopicService topicService;
    private final CommentService commentService;

    public TopicController(TopicService topicService, CommentService commentService) {
        this.topicService = topicService;
        this.commentService = commentService;
    }

    @GetMapping
    public String getAllTopics(Model model) {
        List<Topic> topics = topicService.getAllTopics();
        model.addAttribute("topics", topics);
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

