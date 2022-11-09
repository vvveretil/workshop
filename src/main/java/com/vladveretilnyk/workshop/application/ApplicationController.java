package com.vladveretilnyk.workshop.application;

import com.vladveretilnyk.workshop.application.request.ApplicationCreateRequest;
import com.vladveretilnyk.workshop.config.Page;
import com.vladveretilnyk.workshop.user.User;
import com.vladveretilnyk.workshop.user.exception.UserNotFountException;
import lombok.AllArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import static com.vladveretilnyk.workshop.config.Page.APPLICATIONS_PAGE;
import static com.vladveretilnyk.workshop.config.Page.CREATE_APPLICATION_PAGE;
import static com.vladveretilnyk.workshop.config.Page.REDIRECT_APPLICATIONS_PAGE;

@Controller
@AllArgsConstructor
public class ApplicationController {

    private ApplicationService applicationService;

    @GetMapping("/create-application")
    public String getCreateApplicationPage(Model model) {
        model.addAttribute("applicationCreateRequest", new ApplicationCreateRequest());
        return CREATE_APPLICATION_PAGE;
    }

    @PostMapping("/applications")
    public String createApplication(@ModelAttribute ApplicationCreateRequest applicationCreateRequest) throws UserNotFountException {
        applicationService.create(applicationCreateRequest);
        return REDIRECT_APPLICATIONS_PAGE;
    }

    @GetMapping("/applications")
    public String getApplicationsPage(@AuthenticationPrincipal User user, Model model) {
        model.addAttribute("applications",applicationService.findAllByUser(user));
        return APPLICATIONS_PAGE;
    }

}
