package io.sphere.sdk.http;

import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.annotation.Nullable;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static java.lang.String.format;

class HttpResponseImpl extends Base implements HttpResponse {
    @Nullable
    private final Integer statusCode;
    private final HttpHeaders headers;
    @Nullable
    private final byte[] responseBody;
    @Nullable
    private final HttpRequest associatedRequest;

    HttpResponseImpl(@Nullable final Integer statusCode, @Nullable final byte[] responseBody, @Nullable final HttpRequest associatedRequest, final HttpHeaders headers) {
        this.statusCode = statusCode;
        this.responseBody = responseBody;
        this.associatedRequest = associatedRequest;
        this.headers = headers;
    }

    @Nullable
    @Override
    public Integer getStatusCode() {
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

    @Override
    public String toString() {
        String textInterpretedBody = "";
        try {
            textInterpretedBody = Optional.ofNullable(responseBody).map(b -> StringHttpRequestBody.tryToFilter(new String(b, StandardCharsets.UTF_8))).orElse("empty body");
        } catch (final Exception e) {
            textInterpretedBody = "not parseable: " + e;
        }

        return new ToStringBuilder(this)
                .append("statusCode", statusCode)
                .append("headers", headers)
                .append("associatedRequest", associatedRequest)
                .append("textInterpretedBody", textInterpretedBody)
                .toString();
    }

}
