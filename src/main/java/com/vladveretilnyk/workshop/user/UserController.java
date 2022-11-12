package com.vladveretilnyk.workshop.user;

import com.vladveretilnyk.workshop.password.exception.InvalidPasswordException;
import com.vladveretilnyk.workshop.user.exception.UserNotFountException;
import com.vladveretilnyk.workshop.user.request.UserChangePasswordRequest;
import com.vladveretilnyk.workshop.user.request.UserUpdateInfoRequest;
import lombok.AllArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;

import java.util.Set;

import static com.vladveretilnyk.workshop.config.Page.CHANGE_PASSWORD_PAGE;
import static com.vladveretilnyk.workshop.config.Page.EDIT_PROFILE_PAGE;
import static com.vladveretilnyk.workshop.config.Page.PROFILE_PAGE;
import static com.vladveretilnyk.workshop.config.Page.REDIRECT_PROFILE_PAGE;
import static com.vladveretilnyk.workshop.config.Page.REDIRECT_USERS_PAGE;
import static com.vladveretilnyk.workshop.config.Page.USERS_PAGE;
import static com.vladveretilnyk.workshop.config.Page.USER_PAGE;

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
    public String updateUserInfo(@ModelAttribute @Valid UserUpdateInfoRequest userUpdateInfoRequest, BindingResult bindingResult) throws UserNotFountException {
        if (bindingResult.hasErrors()) {
            return EDIT_PROFILE_PAGE;
        }

        try {
            userService.update(userUpdateInfoRequest);
        } catch (DataIntegrityViolationException dataIntegrityViolationException) {
            bindingResult.addError(
                    new FieldError("userUpdateInfoRequest", "username",
                            String.format("User %s already exists!", userUpdateInfoRequest.getUsername()))
            );
            return EDIT_PROFILE_PAGE;
        }

        return REDIRECT_PROFILE_PAGE;
    }

    @GetMapping("/change-password")
    public String getChangePasswordPage(Model model) {
        model.addAttribute("userChangePasswordRequest", new UserChangePasswordRequest());
        return CHANGE_PASSWORD_PAGE;
    }

    @PutMapping("/change-password")
    public String changePassword(@ModelAttribute @Valid UserChangePasswordRequest userChangePasswordRequest, BindingResult bindingResult) throws UserNotFountException {
        if (bindingResult.hasErrors()) {
            return CHANGE_PASSWORD_PAGE;
        }

        try {
            userService.changePassword(userChangePasswordRequest);
        } catch (InvalidPasswordException invalidPasswordException) {
            bindingResult.addError(
                    new FieldError("userChangePasswordRequest", "oldPassword", "Wrong password")
            );
            return CHANGE_PASSWORD_PAGE;
        }

        return REDIRECT_PROFILE_PAGE;
    }

    @GetMapping("/users")
    public String getUsersPage(Model model) {
        model.addAttribute("users", userService.findAllByRoles(Set.of(UserRole.USER, UserRole.MASTER)));
        return USERS_PAGE;
    }

    @GetMapping("/users/{id}")
    public String getUserPage(@PathVariable(name = "id") Long userId, Model model) throws UserNotFountException {
        model.addAttribute("user", userService.findById(userId));
        return USER_PAGE;
    }

    @PostMapping("/blocked-users")
    public String blockUser(@ModelAttribute(name = "userId") Long userId) throws UserNotFountException {
        userService.blockById(userId);
        userService.unassignApplicationsForUser(userId);
        return REDIRECT_USERS_PAGE;
    }

    @DeleteMapping("/blocked-users/{id}")
    public String unBlockUser(@PathVariable(name = "id") Long userId) throws UserNotFountException {
        userService.unblockById(userId);
        return REDIRECT_USERS_PAGE;
    }

    @PostMapping("/blocked-masters")
    public String blockMaster(@ModelAttribute(name = "masterId") Long masterId) throws UserNotFountException {
        userService.blockById(masterId);
        userService.unassignApplicationsForMaster(masterId);
        return REDIRECT_USERS_PAGE;
    }

    @DeleteMapping("/blocked-masters/{id}")
    public String unblockMaster(@PathVariable(name = "id") Long masterId) throws UserNotFountException {
        userService.unblockById(masterId);
        return REDIRECT_USERS_PAGE;
    }

    @GetMapping("/assign-master-to-application")
    public String getChooseMasterPage(@RequestParam Long applicationId, Model model) {
        model.addAttribute("applicationId", applicationId);
        model.addAttribute("users", userService.findAllByRoles(Set.of(UserRole.MASTER)));
        return USERS_PAGE;
    }

}
