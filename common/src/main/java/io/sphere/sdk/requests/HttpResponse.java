package io.sphere.sdk.requests;

import io.sphere.sdk.models.Base;

import java.util.Optional;

public class HttpResponse extends Base {
    private final int statusCode;
    private final String responseBody;
    private final Optional<HttpRequest> associatedRequest;

    HttpResponse(final int statusCode, final String responseBody, final Optional<HttpRequest> associatedRequest) {
        this.statusCode = statusCode;
        this.responseBody = responseBody;
        this.associatedRequest = associatedRequest;
    }

    public static HttpResponse of(final int status, final String responseBody) {
        return new HttpResponse(status, responseBody, Optional.empty());
    }

    public static HttpResponse of(final int status, final String responseBody, final HttpRequest associatedRequest) {
        return new HttpResponse(status, responseBody, Optional.of(associatedRequest));
    }

    public int getStatusCode() {
        return statusCode;
    }

    public String getResponseBody() {
        return responseBody;
    }

    public Optional<HttpRequest> getAssociatedRequest() {
        return associatedRequest;
    }

    public HttpResponse withoutRequest() {
        return of(getStatusCode(), getResponseBody());
    }
}
