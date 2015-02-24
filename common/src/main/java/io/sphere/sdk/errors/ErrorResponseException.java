package io.sphere.sdk.errors;

import java.util.List;

public class ErrorResponseException extends BadRequestException implements ErrorResponse {
    private static final long serialVersionUID = 0L;

    private final ErrorResponse errorResponse;

    protected ErrorResponseException(final ErrorResponse errorResponse) {
        this.errorResponse = errorResponse;
    }

    @Override
    public List<SphereError> getErrors() {
        return errorResponse.getErrors();
    }

    @Override
    public String getMessage() {
        return errorResponse.toString();
    }
}
