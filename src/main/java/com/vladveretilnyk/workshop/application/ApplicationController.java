package com.vladveretilnyk.workshop.application;

import com.vladveretilnyk.workshop.application.exception.ApplicationNotFoundException;
import com.vladveretilnyk.workshop.application.request.ApplicationCreateRequest;
import com.vladveretilnyk.workshop.application.request.ApplicationUpdateInfoRequest;
import com.vladveretilnyk.workshop.config.Page;
import com.vladveretilnyk.workshop.user.User;
import com.vladveretilnyk.workshop.user.exception.UserNotFountException;
import lombok.AllArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

import javax.websocket.server.PathParam;

import static com.vladveretilnyk.workshop.config.Page.APPLICATIONS_PAGE;
import static com.vladveretilnyk.workshop.config.Page.APPLICATION_PAGE;
import static com.vladveretilnyk.workshop.config.Page.CREATE_APPLICATION_PAGE;
import static com.vladveretilnyk.workshop.config.Page.EDIT_APPLICATION_PAGE;
import static com.vladveretilnyk.workshop.config.Page.REDIRECT_APPLICATIONS_PAGE;
import static java.lang.String.format;

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
        model.addAttribute("applications", applicationService.findAllByUser(user));
        return APPLICATIONS_PAGE;
    }

    @GetMapping("/applications/{id}")
    public String getApplicationPage(@PathVariable(name = "id") Long applicationId, Model model) throws ApplicationNotFoundException {
        model.addAttribute("appl", applicationService.findById(applicationId));
        return APPLICATION_PAGE;
    }

    @DeleteMapping("/applications/{id}")
    public String deleteApplication(@PathVariable(name = "id") Long applicationId) throws ApplicationNotFoundException {
        applicationService.deleteById(applicationId);
        return REDIRECT_APPLICATIONS_PAGE;
    }

    @GetMapping("/edit-application")
    public String getEditApplicationPage(@PathParam("applicationId") Long applicationId, Model model) throws ApplicationNotFoundException {
        model.addAttribute("appl", applicationService.findById(applicationId));
        model.addAttribute("applicationUpdateInfoRequest", new ApplicationUpdateInfoRequest());
        return EDIT_APPLICATION_PAGE;
    }

    @PutMapping("/applications/{id}")
    public String updateApplication(@PathVariable(name = "id") Long applicationId, @ModelAttribute ApplicationUpdateInfoRequest applicationUpdateInfoRequest) throws ApplicationNotFoundException {
        applicationService.updateById(applicationId, applicationUpdateInfoRequest);
        return REDIRECT_APPLICATIONS_PAGE + format("/%d", applicationId);
    }
}
