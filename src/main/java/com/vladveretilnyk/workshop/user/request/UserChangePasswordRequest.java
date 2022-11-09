package com.vladveretilnyk.workshop.user.request;

import com.vladveretilnyk.workshop.password.annotation.PasswordMatches;
import lombok.Data;

@Data
@PasswordMatches
public class UserChangePasswordRequest {

    private Long id;

    private String oldPassword;
    private String newPassword;
    private String newPasswordMatching;

}
