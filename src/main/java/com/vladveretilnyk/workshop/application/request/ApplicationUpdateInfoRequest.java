package com.vladveretilnyk.workshop.application.request;

import com.vladveretilnyk.workshop.application.status.CompletionStatus;
import com.vladveretilnyk.workshop.application.status.PaymentStatus;
import lombok.Data;

@Data
public class ApplicationUpdateInfoRequest {

    private String title;
    private String description;

    private CompletionStatus completionStatus;
    private PaymentStatus paymentStatus;
}
