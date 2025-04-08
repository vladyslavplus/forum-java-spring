package com.plus.forum.util;

import com.plus.forum.repositories.User;
import com.plus.forum.repositories.UserRepository;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class AuthUtils {
    private static UserRepository userRepository;

    public static void init(UserRepository repository) {
        userRepository = repository;
    }

    public static User getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth == null || !auth.isAuthenticated() ||
                auth instanceof AnonymousAuthenticationToken) {
            return null;
        }

        String username = auth.getName();
        return userRepository.findByUsername(username).orElse(null);
    }

    public static boolean isAuthenticated() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication != null && authentication.isAuthenticated() &&
                !(authentication instanceof AnonymousAuthenticationToken);
    }
}
