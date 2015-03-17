package io.sphere.sdk.http;

import java.nio.charset.StandardCharsets;
import java.util.Optional;

import static io.sphere.sdk.http.HttpResponseImpl.responseCodeStartsWith;

public interface HttpResponse {
    int getStatusCode();

    HttpHeaders getHeaders();

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
        return of(status, responseBody, Optional.empty(), HttpHeaders.of());
    }

    public static HttpResponse of(final int status, final String responseBody, final HttpHeaders headers) {
        return of(status, responseBody, Optional.empty(), headers);
    }

    public static HttpResponse of(final int status) {
        return of(status, Optional.empty(), Optional.empty(), Optional.empty() );
    }

    public static HttpResponse of(final int status, final HttpHeaders headers) {
        return of(status, Optional.empty(), Optional.empty(), Optional.of(headers));
    }

    public static HttpResponse of(final int status, final String responseBody, final HttpRequest associatedRequest) {
        return of(status, responseBody, Optional.of(associatedRequest), HttpHeaders.of());
    }

    public static HttpResponse of(final int status, final String responseBody, final HttpRequest associatedRequest, final HttpHeaders headers) {
        return of(status, responseBody, Optional.of(associatedRequest), headers);
    }

    public static HttpResponse of(final int status, final String responseBody, final Optional<HttpRequest> associatedRequest) {
        return of(status, Optional.of(responseBody.getBytes(StandardCharsets.UTF_8)), associatedRequest, Optional.empty());
    }

    public static HttpResponse of(final int status, final String responseBody, final Optional<HttpRequest> associatedRequest, final HttpHeaders headers) {
        return of(status, Optional.of(responseBody.getBytes(StandardCharsets.UTF_8)), associatedRequest, Optional.of(headers));
    }

    public static HttpResponse of(final int status, final Optional<byte[]> body, final Optional<HttpRequest> associatedRequest) {
        return of(status, body, associatedRequest, Optional.<HttpHeaders>empty());
    }

    public static HttpResponse of(final int status, final Optional<byte[]> body, final Optional<HttpRequest> associatedRequest, final HttpHeaders headers) {
        return of(status, body, associatedRequest, Optional.of(headers));
    }

    public static HttpResponse of(final int status, final Optional<byte[]> body, final Optional<HttpRequest> associatedRequest, final Optional<HttpHeaders> headers) {
        return new HttpResponseImpl(status, body, associatedRequest, headers.orElseGet(() -> HttpHeaders.of()));
    }

    default HttpResponse withoutRequest() {
        return HttpResponse.of(getStatusCode(), getResponseBody(), Optional.<HttpRequest>empty(), Optional.of(getHeaders()));
    }
}
