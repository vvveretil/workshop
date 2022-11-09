package com.vladveretilnyk.workshop.user.request;

import lombok.Data;

@Data
public class UserUpdateInfoRequest {

    private Long id;

    private String username;
    private String firstName;
    private String lastName;
    private String phoneNumber;

}
