package com.vladveretilnyk.workshop.user;

import com.vladveretilnyk.workshop.user.request.UserUpdateInfoRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PutMapping;

import javax.validation.Valid;

import static com.vladveretilnyk.workshop.config.Page.EDIT_PROFILE_PAGE;
import static com.vladveretilnyk.workshop.config.Page.PROFILE_PAGE;
import static com.vladveretilnyk.workshop.config.Page.REDIRECT_PROFILE_PAGE;

@Controller
@AllArgsConstructor
public class UserController {

    private UserService userService;

    @GetMapping("/profile")
    public String getProfilePage() {
        return PROFILE_PAGE;
    }

    @GetMapping("/edit-profile")
    public String getEditProfilePage(Model model) {
        model.addAttribute("userUpdateInfoRequest", new UserUpdateInfoRequest());
        return EDIT_PROFILE_PAGE;
    }

    @PutMapping("/edit-profile")
    public String updateUserInfo(@ModelAttribute @Valid UserUpdateInfoRequest userUpdateInfoRequest, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return EDIT_PROFILE_PAGE;
        }

        try {
            userService.update(userUpdateInfoRequest);
        } catch (Exception exception) {
            bindingResult.addError(
                    new FieldError("userUpdateInfoRequest", "username",
                            String.format("User %s already exists!", userUpdateInfoRequest.getUsername()))
            );
            return EDIT_PROFILE_PAGE;
        }

        return REDIRECT_PROFILE_PAGE;
    }

}
