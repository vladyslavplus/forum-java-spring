package com.plus.forum.util;

import com.plus.forum.repositories.UserRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

@Component
public class AuthUtilsInitializer {
    private final UserRepository userRepository;

    public AuthUtilsInitializer(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostConstruct
    public void init() {
        AuthUtils.init(userRepository);
    }
}
