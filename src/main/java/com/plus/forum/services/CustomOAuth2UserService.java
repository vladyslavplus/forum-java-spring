package com.plus.forum.services;

import com.plus.forum.repositories.User;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private final UserService userService;

    public CustomOAuth2UserService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) {
        OAuth2User oAuth2User = new DefaultOAuth2UserService().loadUser(userRequest);

        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        String email = oAuth2User.getAttribute("email");
        String username = oAuth2User.getAttribute("login"); // GitHub
        if (username == null) {
            username = oAuth2User.getAttribute("email"); // Google
        }

        String avatarUrl = null;
        if("google".equals(registrationId)) {
            avatarUrl = oAuth2User.getAttribute("picture");
        }
        else if("github".equals(registrationId)) {
            avatarUrl = oAuth2User.getAttribute("avatar_url");
            if (email == null) {
                email = oAuth2User.getAttribute("login") + "@github.com";
            }
        }

        User user = userService.findByEmail(email).orElse(null);
        if (user == null) {
            user = userService.createOAuthUser(username, email, avatarUrl);
        }

        userService.updateAvatar(user, avatarUrl);

        return new DefaultOAuth2User(
                Collections.singleton(new SimpleGrantedAuthority("ROLE_USER")),
                oAuth2User.getAttributes(),
                registrationId.equals("google") ? "email" : "login"
        );

    }
}
