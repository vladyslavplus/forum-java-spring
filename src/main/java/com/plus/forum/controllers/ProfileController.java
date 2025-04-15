package com.plus.forum.controllers;

import com.plus.forum.dto.TopicDto;
import com.plus.forum.dto.UserProfileDto;
import com.plus.forum.repositories.User;
import com.plus.forum.services.UserService;
import com.plus.forum.util.AuthUtils;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
public class ProfileController {
    private final UserService userService;

    public ProfileController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/profile/settings")
    public String profile(Model model) {
        User currentUser = AuthUtils.getCurrentUser();

        if (currentUser == null) {
            return "redirect:/login";
        }

        model.addAttribute("user", currentUser);
        return "profile/profile-settings";
    }

    @GetMapping("/profile/{username}")
    public String profileByUsername(@PathVariable String username, Model model) {
        Optional<User> optionalUser = userService.findByUsername(username);
        if (optionalUser.isEmpty()) {
            return "redirect:/login?error=user-not-found";
        }

        User user = optionalUser.get();

        List<TopicDto> topicDtos = user.getTopics().stream()
                .map(topic -> new TopicDto(topic.getId(), topic.getTitle(), topic.getCreatedAt()))
                .toList();

        UserProfileDto userDto = new UserProfileDto(user.getUsername(), user.getAvatar(), topicDtos);

        model.addAttribute("user", userDto);
        return "profile/profile";
    }
}
