package io.sphere.sdk.client;


import java.util.Optional;
import io.sphere.sdk.meta.BuildInfo;
import io.sphere.sdk.http.HttpRequest;
import io.sphere.sdk.http.HttpResponse;
import io.sphere.sdk.utils.JsonUtils;

import java.util.Date;

/** Exception thrown by the Sphere Java client. */
public class SphereClientException extends RuntimeException {
    private static final long serialVersionUID = 0L;
    private Optional<String> sphereRequest = Optional.empty();
    private Optional<String> underlyingHttpRequest = Optional.empty();
    private Optional<String> underlyingHttpResponse = Optional.empty();
    private Optional<String> projectKey = Optional.empty();

    protected SphereClientException() {}

    public SphereClientException(final String message) {
        super(message);
    }

    public SphereClientException(final String message, Throwable cause) {
        super(message + ": " + cause.getMessage(), cause);
    }

    public SphereClientException(final Throwable cause) {
        this(cause.getMessage(), cause);
    }

    public Optional<String> getSphereRequest() {
        return sphereRequest;
    }

    public void setSphereRequest(final String sphereRequest) {
        this.sphereRequest =  Optional.ofNullable(sphereRequest);
    }

    public Optional<String> getUnderlyingHttpRequest() {
        return underlyingHttpRequest;
    }

    public void setUnderlyingHttpRequest(final String underlyingHttpRequest) {
        this.underlyingHttpRequest = Optional.ofNullable(underlyingHttpRequest);
    }

    public Optional<String> getUnderlyingHttpResponse() {
        return underlyingHttpResponse;
    }

    public void setUnderlyingHttpResponse(final String underlyingHttpResponse) {
        this.underlyingHttpResponse =  Optional.ofNullable(underlyingHttpResponse);
    }

    public Optional<String> getProjectKey() {
        return projectKey;
    }

    public void setProjectKey(final String projectKey) {
        this.projectKey = Optional.ofNullable(projectKey);
    }

    @Override
    public String getMessage() {
        StringBuilder builder = new StringBuilder("===== BEGIN EXCEPTION OUTPUT =====").append("\n");
        final String httpRequest = underlyingHttpRequest.orElse("<unknown>");
        return builder.
                append("date: ").append(new Date()).append("\n").
                append("SDK version: ").append(BuildInfo.version()).append("\n").
                append("Java runtime: ").append(System.getProperty("java.version")).append("\n").
                append("cwd: ").append(System.getProperty("user.dir")).append("\n").
                append("project key: ").append(projectKey.orElse("<unknown>")).append("\n").
                append("sphere request: ").append(sphereRequest.orElse("<unknown>")).append("\n").
                append("underlying http request: ").append(httpRequest).append("\n").
                append("underlying http response: ").append(underlyingHttpResponse.orElse("<unknown>")).append("\n").
                append("detailMessage: ").append(super.getMessage()).append("\n").
                append("===== END EXCEPTION OUTPUT =====").toString();
    }

    public void setUnderlyingHttpRequest(final HttpRequest httpRequest) {
        final String body = httpRequest.getBody().map(s -> JsonUtils.prettyPrintJsonStringSecureWithFallback(s)).orElse("<no body>");
        final String requestAsString = new StringBuilder(httpRequest.getHttpMethod().toString()).append(" ").append(httpRequest.getPath()).append("\n").append(body).toString();
        setUnderlyingHttpRequest(requestAsString);
    }

    public void setUnderlyingHttpResponse(final HttpResponse httpResponse) {
        final String s = "status=" + httpResponse.getStatusCode() + " " + JsonUtils.prettyPrintJsonStringSecureWithFallback(httpResponse.getResponseBody());
        setUnderlyingHttpResponse(s);
    }
}
