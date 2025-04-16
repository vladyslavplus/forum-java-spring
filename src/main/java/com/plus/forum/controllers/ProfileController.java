package com.plus.forum.controllers;

import com.plus.forum.dto.TopicDto;
import com.plus.forum.dto.UserProfileDto;
import com.plus.forum.repositories.User;
import com.plus.forum.services.UserService;
import com.plus.forum.util.AuthUtils;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
public class ProfileController {
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    public ProfileController(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
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

    @PostMapping("/profile/update-avatar")
    public String updateAvatar(@RequestParam("avatarFile") MultipartFile avatarFile,
                               RedirectAttributes redirectAttributes) {
        User currentUser = AuthUtils.getCurrentUser();

        if (currentUser == null) {
            return "redirect:/login";
        }

        try {
            userService.updateAvatar(currentUser.getId(), avatarFile);
            redirectAttributes.addFlashAttribute("message", "Avatar updated successfully");
        } catch (IOException e) {
            redirectAttributes.addFlashAttribute("error", "Failed to update avatar: " + e.getMessage());
        }

        return "redirect:/profile/settings";
    }

    @PostMapping("/profile/update-profile")
    public String updateProfile(@RequestParam("email") String email,
                                RedirectAttributes redirectAttributes) {
        User currentUser = AuthUtils.getCurrentUser();

        if (currentUser == null) {
            return "redirect:/login";
        }

        try {
            userService.updateEmail(currentUser.getId(), email);
            redirectAttributes.addFlashAttribute("message", "Profile updated successfully");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Failed to update profile: " + e.getMessage());
        }

        return "redirect:/profile/settings";
    }

    @PostMapping("/profile/update-password")
    public String updatePassword(@RequestParam("currentPassword") String currentPassword,
                                 @RequestParam("newPassword") String newPassword,
                                 @RequestParam("confirmPassword") String confirmPassword,
                                 RedirectAttributes redirectAttributes) {
        User currentUser = AuthUtils.getCurrentUser();

        if (currentUser == null) {
            return "redirect:/login";
        }

        if (!newPassword.equals(confirmPassword)) {
            redirectAttributes.addFlashAttribute("error", "New passwords do not match");
            return "redirect:/profile/settings";
        }

        if (!passwordEncoder.matches(currentPassword, currentUser.getPassword())) {
            redirectAttributes.addFlashAttribute("error", "Current password is incorrect");
            return "redirect:/profile/settings";
        }

        try {
            userService.updatePassword(currentUser.getId(), newPassword);
            redirectAttributes.addFlashAttribute("message", "Password updated successfully");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Failed to update password: " + e.getMessage());
        }

        return "redirect:/profile/settings";
    }
}
