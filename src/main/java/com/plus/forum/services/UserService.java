package com.plus.forum.services;

import com.plus.forum.repositories.User;
import com.plus.forum.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User findByUserName(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found: " + username));
    }

    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public User createOAuthUser(String username, String email, String avatarUrl) {
        System.out.println("Creating user: " + username + ", " + email + ", " + avatarUrl);
        User user = new User();
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(null);
        user.setRoles(Set.of("ROLE_USER"));
        user.setAvatar(avatarUrl);
        return userRepository.save(user);
    }

    public void updateAvatar(User user, String avatarUrl) {
        user.setAvatar(avatarUrl);
        userRepository.save(user);
    }


}
