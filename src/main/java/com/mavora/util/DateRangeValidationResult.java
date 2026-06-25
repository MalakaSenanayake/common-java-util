package com.mavora.util;

public final class DateRangeValidationResult {

    private final DateRangeFailureKind failureKind;

    private DateRangeValidationResult(DateRangeFailureKind failureKind) {
        this.failureKind = failureKind;
    }

    public static DateRangeValidationResult valid() {
        return new DateRangeValidationResult(null);
    }

    public static DateRangeValidationResult invalid(DateRangeFailureKind failureKind) {
        return new DateRangeValidationResult(failureKind);
    }

    public boolean isValid() {
        return failureKind == null;
    }

    public DateRangeFailureKind getFailureKind() {
        return failureKind;
    }
}
