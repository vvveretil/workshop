package com.vladveretilnyk.workshop.password;

import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class PasswordService {

    private BCryptPasswordEncoder passwordEncoder;

    public String encrypt(String password) {
        return passwordEncoder.encode(password);
    }

    public boolean isMatches(String firstPassword, String secondPassword) {
        return (firstPassword != null && secondPassword != null) &&
                (firstPassword.equals(secondPassword));
    }

    public boolean isEncryptedMatches(String notEncrypted, String encryptedPassword) {
        return (notEncrypted != null && encryptedPassword != null) &&
                passwordEncoder.matches(notEncrypted, encryptedPassword);
    }

}