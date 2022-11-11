package com.vladveretilnyk.workshop.application.status;

import lombok.Getter;

public enum PaymentStatus {
    WAITING_FOR_PAYMENT("Waiting for payment"), PAID("Paid");

    @Getter
    private final String status;

    PaymentStatus(String status) {
        this.status = status;
    }
}
