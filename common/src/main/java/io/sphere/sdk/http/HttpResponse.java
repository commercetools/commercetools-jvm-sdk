package io.sphere.sdk.http;

import java.util.Optional;

import static io.sphere.sdk.http.HttpResponseImpl.responseCodeStartsWith;

public interface HttpResponse {
    int getStatusCode();

    String getResponseBody();

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

    default HttpResponse withoutRequest() {
        return HttpResponse.of(getStatusCode(), getResponseBody());
    }

    public static HttpResponse of(final int status, final String responseBody) {
        return new HttpResponseImpl(status, responseBody, Optional.empty());
    }

    public static HttpResponse of(final int status, final String responseBody, final HttpRequest associatedRequest) {
        return new HttpResponseImpl(status, responseBody, Optional.of(associatedRequest));
    }
}
