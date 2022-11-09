package com.vladveretilnyk.workshop.user;

import com.vladveretilnyk.workshop.password.PasswordService;
import com.vladveretilnyk.workshop.password.exception.InvalidPasswordException;
import com.vladveretilnyk.workshop.user.exception.UserNotFountException;
import com.vladveretilnyk.workshop.user.request.UserChangePasswordRequest;
import com.vladveretilnyk.workshop.user.request.UserUpdateInfoRequest;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import static java.lang.String.format;

@Service
@AllArgsConstructor
public class UserService {

    private UserRepository userRepository;
    private PasswordService passwordService;
    private ModelMapper modelMapper;

    public User findByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(format("user %s not found!", username)));
    }

    public User findById(Long id) throws UserNotFountException {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFountException(format("user with id %d not found!", id)));
    }

    public void save(User user) {
        userRepository.save(user);
    }

    public void update(UserUpdateInfoRequest userUpdateInfoRequest) throws UserNotFountException {
        User user = extractUser(userUpdateInfoRequest);

        save(user);
        updateAuthorizedUser(user);
    }

    private User extractUser(UserUpdateInfoRequest userUpdateInfoRequest) throws UserNotFountException {
        User user = findById(userUpdateInfoRequest.getId());
        modelMapper.map(userUpdateInfoRequest, user);

        return user;
    }

    private void updateAuthorizedUser(User user) {
        Authentication authentication = new UsernamePasswordAuthenticationToken(user, user.getPassword(), user.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    public void changePassword(UserChangePasswordRequest userChangePasswordRequest) throws UserNotFountException, InvalidPasswordException {
        User user = findById(userChangePasswordRequest.getId());

        if (!passwordService.isEncryptedMatches(userChangePasswordRequest.getOldPassword(), user.getPassword())) {
            throw new InvalidPasswordException("Password invalid!");
        }

        String encryptedNewPassword = passwordService.encrypt(userChangePasswordRequest.getNewPassword());
        user.setPassword(encryptedNewPassword);

        save(user);
        updateAuthorizedUser(user);
    }

}
