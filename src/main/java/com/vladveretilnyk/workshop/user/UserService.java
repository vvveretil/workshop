package com.vladveretilnyk.workshop.user;

import com.vladveretilnyk.workshop.user.request.UserUpdateInfoRequest;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserService {

    private UserRepository userRepository;
    private ModelMapper modelMapper;

    public User findByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(String.format("user %s not found!", username)));
    }

    public void save(User user) {
        userRepository.save(user);
    }

    public void update(UserUpdateInfoRequest userUpdateInfoRequest) {
        User user = extractUser(userUpdateInfoRequest);

        save(user);
        updateAuthorizedUser(user);
    }

    private User extractUser(UserUpdateInfoRequest userUpdateInfoRequest) {
        User user = findByUsername(userUpdateInfoRequest.getCurrentUsername());
        modelMapper.map(userUpdateInfoRequest, user);

        return user;
    }

    private void updateAuthorizedUser(User user) {
        Authentication authentication = new UsernamePasswordAuthenticationToken(user, user.getPassword(), user.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

}
