package com.vladveretilnyk.workshop.config;

public class Page {

    public static final String LOGIN_PAGE = "login";
    public static final String REGISTRATION_PAGE = "registration";

    public static final String PROFILE_PAGE = "profile/profile";
    public static final String EDIT_PROFILE_PAGE = "profile/edit-profile";

    public static final String CHANGE_PASSWORD_PAGE = "profile/change-password";

    public static final String CREATE_APPLICATION_PAGE = "application/create-application";
    public static final String APPLICATIONS_PAGE = "application/applications";
    public static final String APPLICATION_PAGE = "application/application";
    public static final String EDIT_APPLICATION_PAGE = "application/edit-application";

    public static final String USERS_PAGE = "user/users";
    public static final String USER_PAGE = "user/user";

    public static final String REDIRECT_LOGIN_PAGE = "redirect:/" + LOGIN_PAGE;
    public static final String REDIRECT_PROFILE_PAGE = "redirect:/profile";
    public static final String REDIRECT_APPLICATIONS_PAGE = "redirect:/applications";
    public static final String REDIRECT_USERS_PAGE = "redirect:/users";

}
