package com.vladveretilnyk.workshop;

import com.vladveretilnyk.workshop.config.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {

    @GetMapping("/login")
    public String getLoginPage(@RequestParam(name = "error", required = false) String error,
                               @RequestParam(name = "logout", required = false) String logout,
                               Model model) {
        model.addAttribute("error", error != null);
        model.addAttribute("logout", logout != null);
        return Page.LOGIN_PAGE;
    }

}
