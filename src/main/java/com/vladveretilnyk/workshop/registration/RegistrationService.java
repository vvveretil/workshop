package com.vladveretilnyk.workshop.registration;

import com.vladveretilnyk.workshop.password.PasswordService;
import com.vladveretilnyk.workshop.registration.request.RegistrationRequest;
import com.vladveretilnyk.workshop.user.User;
import com.vladveretilnyk.workshop.user.UserRole;
import com.vladveretilnyk.workshop.user.UserService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@AllArgsConstructor
public class RegistrationService {

    private UserService userService;
    private PasswordService passwordService;
    private ModelMapper modelMapper;

    public void register(RegistrationRequest registrationRequest) {
        User user = modelMapper.map(registrationRequest, User.class);

        String encodedPassword = passwordService.encrypt(user.getPassword());

        user.setPassword(encodedPassword);
        user.setAuthorities(Set.of(UserRole.USER));

        userService.save(user);
    }

}
