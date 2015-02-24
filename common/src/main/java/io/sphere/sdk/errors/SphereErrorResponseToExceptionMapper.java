package io.sphere.sdk.errors;

public class SphereErrorResponseToExceptionMapper {
    private SphereErrorResponseToExceptionMapper() {
    }

    public static SphereErrorResponseToExceptionMapper of() {
        return new SphereErrorResponseToExceptionMapper();
    }

    public SphereException toException(final ErrorResponse errorResponse) {
        final SphereException result;
        if(errorResponse.getStatusCode() == 400) {
            result = new ErrorResponseException(errorResponse);
        } else {
            result = new ClientErrorException(errorResponse.getStatusCode());
        }
        return result;
    }
}
