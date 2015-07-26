package io.sphere.sdk.http;

import javax.annotation.Nullable;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static java.lang.String.format;

class HttpResponseImpl extends Base implements HttpResponse {
    private final int statusCode;
    private final HttpHeaders headers;
    @Nullable
    private final byte[] responseBody;
    @Nullable
    private final HttpRequest associatedRequest;
    @Nullable
    private final String bodyAsStringForDebugging;

    HttpResponseImpl(final int statusCode, @Nullable final byte[] responseBody, @Nullable final HttpRequest associatedRequest, final HttpHeaders headers) {
        this.statusCode = statusCode;
        this.responseBody = responseBody;
        this.associatedRequest = associatedRequest;
        this.headers = headers;
        Optional<String> bodyAsString = Optional.empty();
        try {
            bodyAsString = statusCode >= 400 ? Optional.ofNullable(responseBody).map(b -> new String(b, StandardCharsets.UTF_8)) : Optional.<String>empty();
        } catch (final Exception e) {
            bodyAsString = Optional.empty();
        }
        this.bodyAsStringForDebugging = bodyAsString.orElse(null);
    }

    @Override
    public int getStatusCode() {
        return statusCode;
    }

    @Override
    public HttpHeaders getHeaders() {
        return headers;
    }

    @Nullable
    @Override
    public synchronized byte[] getResponseBody() {
        return responseBody;
    }

    @Nullable
    @Override
    public HttpRequest getAssociatedRequest() {
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
