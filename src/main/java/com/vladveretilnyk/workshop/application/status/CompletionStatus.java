package com.vladveretilnyk.workshop.application.status;

import lombok.Getter;

public enum CompletionStatus {
    NOT_STARTED("Not started"), IN_PROGRESS("In progress"), COMPLETED("Completed");

    @Getter
    private final String status;

    CompletionStatus(String status) {
        this.status = status;
    }
}
