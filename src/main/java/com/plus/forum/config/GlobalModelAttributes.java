package com.plus.forum.config;

import com.plus.forum.util.AuthUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class GlobalModelAttributes {

    @ModelAttribute("authenticated")
    public boolean authenticated() {
        return AuthUtils.isAuthenticated();
    }
}
