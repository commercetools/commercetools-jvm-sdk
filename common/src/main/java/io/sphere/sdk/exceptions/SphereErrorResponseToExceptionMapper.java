package io.sphere.sdk.exceptions;

public class SphereErrorResponseToExceptionMapper {
    private SphereErrorResponseToExceptionMapper() {
    }

    public static SphereErrorResponseToExceptionMapper of() {
        return new SphereErrorResponseToExceptionMapper();
    }

    public SphereException toException(final ErrorResponse errorResponse) {
        final SphereException result;
        if (errorResponse.hasErrorCode("InvalidJsonInput")) {
            result = new InvalidJsonInputException(errorResponse);
        } else if(errorResponse.getStatusCode() == 400) {
            result = new SphereErrorResponseBadRequestException(errorResponse);
        } else {
            result = new ClientErrorException(errorResponse.getStatusCode());
        }
        return result;
    }
}
