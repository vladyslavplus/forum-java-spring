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
    public String profile(OAuth2AuthenticationToken token, Model model) {
        Map<String, Object> attributes = token.getPrincipal().getAttributes();
        String registrationId = token.getAuthorizedClientRegistrationId();

        String email = null;

        if ("google".equals(registrationId)) {
            email = (String) attributes.get("email");
        } else if ("github".equals(registrationId)) {
            email = (String) attributes.get("email");
            if (email == null) {
                String githubLogin = (String) attributes.get("login");
                email = githubLogin + "@github.com";
            }
        }

        if (email == null) {
            return "redirect:/login?error=email-not-found";
        }

        Optional<User> user = userService.findByEmail(email);
        user.ifPresent(value -> model.addAttribute("user", value));

        if (user.isEmpty()) {
            return "redirect:/login";
        }

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
