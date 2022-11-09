package com.vladveretilnyk.workshop.registration;

import com.vladveretilnyk.workshop.registration.request.RegistrationRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

import static com.vladveretilnyk.workshop.config.Page.REDIRECT_LOGIN_PAGE;
import static com.vladveretilnyk.workshop.config.Page.REGISTRATION_PAGE;

@Controller
@AllArgsConstructor
public class RegistrationController {

    private RegistrationService registrationService;

    @GetMapping("/registration")
    public String getRegistrationPage(Model model) {
        model.addAttribute("registrationRequest", new RegistrationRequest());
        return REGISTRATION_PAGE;
    }

    @PostMapping("/registration")
    public String registerUser(@ModelAttribute @Valid RegistrationRequest registrationRequest, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return REGISTRATION_PAGE;
        }

        try {
            registrationService.register(registrationRequest);
        } catch (Exception exception) {
            bindingResult.addError(new FieldError("registrationRequest", "username", "User already exists!"));
            return REGISTRATION_PAGE;
        }

        return REDIRECT_LOGIN_PAGE;
    }
}
