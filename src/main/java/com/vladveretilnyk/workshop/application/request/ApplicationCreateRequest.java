package com.vladveretilnyk.workshop.application.request;

import com.vladveretilnyk.workshop.application.status.CompletionStatus;
import com.vladveretilnyk.workshop.application.status.PaymentStatus;
import lombok.Data;

import java.time.LocalDate;

@Data
public class ApplicationCreateRequest {


    private String title;
    private String description;

    private LocalDate creationDate = LocalDate.now();

    private CompletionStatus completionStatus = CompletionStatus.NOT_STARTED;
    private PaymentStatus paymentStatus =PaymentStatus.WAITING_FOR_PAYMENT;

    private double price;

    private Long authorId;

}
