package io.sphere.sdk.errors;

import java.util.List;

public interface ErrorResponse {
    int getStatusCode();

    String getMessage();

    List<SphereError> getErrors();

    default boolean hasErrorCode(final String errorCode) {
        return getErrors().stream().anyMatch(sphereError -> sphereError.getCode().equals(errorCode));
    }
}
