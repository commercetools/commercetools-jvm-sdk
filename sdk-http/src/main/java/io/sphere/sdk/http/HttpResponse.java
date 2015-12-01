package io.sphere.sdk.http;

import javax.annotation.Nullable;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

import static io.sphere.sdk.http.HttpResponseImpl.responseCodeStartsWith;

public interface HttpResponse {
    @Nullable
    Integer getStatusCode();

    HttpHeaders getHeaders();

    @Nullable
    byte[] getResponseBody();

    @Nullable
    HttpRequest getAssociatedRequest();

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

    static HttpResponse of(@Nullable final Integer status, final String responseBody) {
        return of(status, responseBody, null, HttpHeaders.of());
    }

    static HttpResponse of(@Nullable final Integer status, final String responseBody, final HttpHeaders headers) {
        return of(status, responseBody, null, headers);
    }

    static HttpResponse of(@Nullable final Integer status) {
        return of(status, (byte[]) null, null, null);
    }

    static HttpResponse of(@Nullable final Integer status, final HttpHeaders headers) {
        return of(status, (byte[]) null, null, headers);
    }

    static HttpResponse of(@Nullable final Integer status, final String responseBody, final HttpRequest associatedRequest) {
        return of(status, responseBody, associatedRequest, HttpHeaders.of());
    }

    static HttpResponse of(@Nullable final Integer status, final String responseBody, final HttpRequest associatedRequest, final HttpHeaders headers) {
        return of(status, responseBody.getBytes(StandardCharsets.UTF_8), associatedRequest, headers);
    }

    static HttpResponse of(@Nullable final Integer status, final byte[] body, final HttpRequest associatedRequest) {
        return of(status, body, associatedRequest, HttpHeaders.empty());
    }

    static HttpResponse of(@Nullable final Integer status, final byte[] body, @Nullable final HttpRequest associatedRequest, @Nullable final HttpHeaders headers) {
        return new HttpResponseImpl(status, body, associatedRequest, Optional.ofNullable(headers).orElseGet(() -> HttpHeaders.of()));
    }

    default HttpResponse withoutRequest() {
        return HttpResponse.of(getStatusCode(), getResponseBody(), null, getHeaders());
    }
}
