package io.sphere.sdk.http;

import java.nio.charset.StandardCharsets;
import java.util.Optional;

import static io.sphere.sdk.http.HttpResponseImpl.responseCodeStartsWith;

public interface HttpResponse {
    int getStatusCode();

    Optional<byte[]> getResponseBody();

    Optional<HttpRequest> getAssociatedRequest();

    default boolean hasInformationalResponseCode() {
        return responseCodeStartsWith(this, 1);
    }

    default boolean hasSuccessResponseCode() {
        return responseCodeStartsWith(this, 2);
    }

    default boolean hasRedirectionResponseCode() {
        return responseCodeStartsWith(this, 3);
    }

    default boolean hasClientErrorResponseCode() {
        return responseCodeStartsWith(this, 4);
    }

    default boolean hasServerErrorResponseCode() {
        return responseCodeStartsWith(this, 5);
    }

    public static HttpResponse of(final int status, final String responseBody) {
        return of(status, responseBody, Optional.empty());
    }

    public static HttpResponse of(final int status, final String responseBody, final HttpRequest associatedRequest) {
        return of(status, responseBody, Optional.of(associatedRequest));
    }

    public static HttpResponse of(final int status, final String responseBody, final Optional<HttpRequest> associatedRequest) {
        return new HttpResponseImpl(status, Optional.of(responseBody.getBytes(StandardCharsets.UTF_8)), associatedRequest);
    }
}
