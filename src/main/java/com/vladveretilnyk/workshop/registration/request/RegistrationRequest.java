package com.vladveretilnyk.workshop.registration.request;

import lombok.Data;

@Data
public class RegistrationRequest {

    private String firstName;
    private String lastName;
    private String phoneNumber;

    private String username;

    private String password;
    private String matchingPassword;

}
