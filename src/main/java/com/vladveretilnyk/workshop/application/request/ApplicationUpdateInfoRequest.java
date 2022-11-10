package com.vladveretilnyk.workshop.application.request;

import lombok.Data;

@Data
public class ApplicationUpdateInfoRequest {

    private String title;
    private String description;

}
