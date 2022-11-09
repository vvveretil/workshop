package com.vladveretilnyk.workshop.application.request;

import lombok.Data;

import java.time.LocalDate;

@Data
public class ApplicationCreateRequest {


    private String title;
    private String description;

    private LocalDate creationDate = LocalDate.now();

    private String completionStatus = "Not started";
    private String paymentStatus = "Waiting for payment";

    private double price;

    private Long authorId;

}
