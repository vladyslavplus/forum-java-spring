package com.plus.forum.util;

import com.plus.forum.repositories.CustomOAuth2User;
import com.plus.forum.repositories.User;
import com.plus.forum.repositories.UserRepository;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;

import java.util.Map;

public class AuthUtils {
    private static UserRepository userRepository;

    public static void init(UserRepository repository) {
        userRepository = repository;
    }

    public static User getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth == null || !auth.isAuthenticated() || auth instanceof AnonymousAuthenticationToken) {
            return null;
        }

        Object principal = auth.getPrincipal();

        if (auth instanceof OAuth2AuthenticationToken oauthToken) {
            Map<String, Object> attributes = oauthToken.getPrincipal().getAttributes();
            String registrationId = oauthToken.getAuthorizedClientRegistrationId();

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

            if (email != null) {
                return userRepository.findByEmail(email).orElse(null);
            }

            return null;
        }

        if (principal instanceof UserDetails userDetails) {
            String emailOrUsername = userDetails.getUsername();
            return userRepository.findByEmail(emailOrUsername)
                    .or(() -> userRepository.findByUsername(emailOrUsername))
                    .orElse(null);
        }

        return null;
    }

    public static boolean isAuthenticated() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication != null && authentication.isAuthenticated() &&
                !(authentication instanceof AnonymousAuthenticationToken);
    }

    public static boolean isOAuthUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return auth instanceof OAuth2AuthenticationToken;
    }

}
