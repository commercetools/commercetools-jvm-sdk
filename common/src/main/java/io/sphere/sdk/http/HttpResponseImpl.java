package io.sphere.sdk.http;

import io.sphere.sdk.models.Base;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static java.lang.String.format;

class HttpResponseImpl extends Base implements HttpResponse {
    private final int statusCode;
    private final String responseBody;
    private final Optional<HttpRequest> associatedRequest;

    HttpResponseImpl(final int statusCode, final String responseBody, final Optional<HttpRequest> associatedRequest) {
        this.statusCode = statusCode;
        this.responseBody = responseBody;
        this.associatedRequest = associatedRequest;
    }

    @Override
    public int getStatusCode() {
        return statusCode;
    }

    @Override
    public String getResponseBody() {
        return responseBody;
    }

    @Override
    public Optional<HttpRequest> getAssociatedRequest() {
        return associatedRequest;
    }

    public static boolean responseCodeStartsWith(final HttpResponse httpResponse, final int firstNumberOfStatusCode) {
        final List<Integer> possibleValues = Arrays.asList(1, 2, 3, 4, 5);
        if (!possibleValues.contains(firstNumberOfStatusCode)) {
            final String message = format("Response code can only start with %s but it was %d.", possibleValues, firstNumberOfStatusCode);
            throw new IllegalArgumentException(message);
        }
        final String actualResponseCodeAsString = Objects.toString(httpResponse.getStatusCode());
        final String firstNumberAsString = Objects.toString(firstNumberOfStatusCode);
        return actualResponseCodeAsString.startsWith(firstNumberAsString);
    }
}
