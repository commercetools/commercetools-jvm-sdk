package io.sphere.sdk.exceptions;

import java.util.List;

public class SphereErrorResponseBadRequestException extends BadRequestException implements ErrorResponse {
    private static final long serialVersionUID = 0L;

    private final ErrorResponse errorResponse;

    protected SphereErrorResponseBadRequestException(final ErrorResponse errorResponse) {
        this.errorResponse = errorResponse;
    }

    @Override
    public List<SphereError> getErrors() {
        return errorResponse.getErrors();
    }

    @Override
    public String getMessage() {
        return errorResponse.getMessage();
    }
}
