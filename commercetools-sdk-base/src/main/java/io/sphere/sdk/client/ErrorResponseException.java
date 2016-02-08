package io.sphere.sdk.client;

import io.sphere.sdk.models.errors.ErrorResponse;
import io.sphere.sdk.models.errors.SphereError;

import java.util.Collections;
import java.util.List;

/**

 <h3 id="error-codes">Error Codes</h3>

 <h4 id="InvalidField">InvalidField</h4>
 <p>One possible cause could be, that you try to set the value of a product attribute and the type is not matching or you have an enum field and the key is wrong.</p>
Example: <code>{"statusCode":400,"message":"The value '2' is not valid for field 'fieldNameExample'. Allowed values are: \"red\",\"green\". ","errors":[{"code":"InvalidField","message":"The value '2' is not valid for field 'fieldNameExample'. Allowed values are: \"red\",\"green\". ","invalidValue":2,"allowedValues":["red","green"],"field":"fieldNameExample"}]}</code>

 */
public class ErrorResponseException extends BadRequestException implements ErrorResponse {
    private static final long serialVersionUID = 0L;

    private final Integer statusCode;
    private final List<? extends SphereError> errors;

    public ErrorResponseException(final ErrorResponse errorResponse) {
        this(errorResponse.getStatusCode(), errorResponse.getMessage(), errorResponse.getErrors());
    }

    ErrorResponseException(final Integer statusCode, final String message, final List<? extends SphereError> errors) {
        super(message);
        this.statusCode = statusCode;
        this.errors = errors == null ? Collections.<SphereError>emptyList() : errors;
    }

    @Override
    public Integer getStatusCode() {
        return statusCode;
    }

    @Override
    public List<? extends SphereError> getErrors() {
        return errors;
    }
}
