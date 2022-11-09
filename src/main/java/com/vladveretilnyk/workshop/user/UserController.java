package com.vladveretilnyk.workshop.user;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import static com.vladveretilnyk.workshop.config.Page.PROFILE_PAGE;

@Controller
@AllArgsConstructor
public class UserController {

    @GetMapping("/profile")
    public String getProfilePage() {
        return PROFILE_PAGE;
    }

}
