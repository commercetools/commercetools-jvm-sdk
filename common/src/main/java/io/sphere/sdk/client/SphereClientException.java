package io.sphere.sdk.client;


import com.google.common.base.Optional;
import io.sphere.sdk.meta.BuildInfo;
import io.sphere.sdk.requests.HttpRequest;
import io.sphere.sdk.requests.HttpResponse;
import io.sphere.sdk.utils.JsonUtils;

import java.util.Date;

/** Exception thrown by the Sphere Java client. */
public class SphereClientException extends RuntimeException {
    private static final long serialVersionUID = 0L;
    private Optional<String> sphereRequest = Optional.absent();
    private Optional<String> underlyingHttpRequest = Optional.absent();
    private Optional<String> underlyingHttpResponse = Optional.absent();
    private Optional<String> projectKey = Optional.absent();

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
        this.sphereRequest =  Optional.fromNullable(sphereRequest);
    }

    public Optional<String> getUnderlyingHttpRequest() {
        return underlyingHttpRequest;
    }

    public void setUnderlyingHttpRequest(final String underlyingHttpRequest) {
        this.underlyingHttpRequest = Optional.fromNullable(underlyingHttpRequest);
    }

    public Optional<String> getUnderlyingHttpResponse() {
        return underlyingHttpResponse;
    }

    public void setUnderlyingHttpResponse(final String underlyingHttpResponse) {
        this.underlyingHttpResponse =  Optional.fromNullable(underlyingHttpResponse);
    }

    public Optional<String> getProjectKey() {
        return projectKey;
    }

    public void setProjectKey(final String projectKey) {
        this.projectKey = Optional.fromNullable(projectKey);
    }

    @Override
    public String getMessage() {
        StringBuilder builder = new StringBuilder("===== BEGIN EXCEPTION OUTPUT =====").append("\n");
        final String httpRequest = underlyingHttpRequest.or("<unknown>");
        return builder.append("\n").
                append("date: ").append(new Date()).append("\n").
                append("SDK version: ").append(BuildInfo.version()).append("\n").
                append("Java runtime: ").append(System.getProperty("java.version")).append("\n").
                append("project key: ").append(projectKey.or("<unknown>")).append("\n").
                append("sphere request: ").append(sphereRequest.or("<unknown>")).append("\n").
                append("underlying http request: ").append(httpRequest).append("\n").
                append("underlying http response: ").append(underlyingHttpResponse.or("<unknown>")).append("\n").
                append("detailMessage: ").append(super.getMessage()).append("\n").
                append("===== END EXCEPTION OUTPUT =====").toString();
    }

    public void setUnderlyingHttpRequest(final HttpRequest httpRequest) {
        final String body = httpRequest.getBody().transform(s -> JsonUtils.prettyPrintJsonStringSecureWithFallback(s)).or("<no body>");
        final String requestAsString = new StringBuilder(httpRequest.getHttpMethod().toString()).append(" ").append(httpRequest.getPath()).append("\n").append(body).toString();
        setUnderlyingHttpRequest(requestAsString);
    }

    public void setUnderlyingHttpResponse(final HttpResponse httpResponse) {
        final String s = "status=" + httpResponse.getStatusCode() + " " + JsonUtils.prettyPrintJsonStringSecureWithFallback(httpResponse.getResponseBody());
        setUnderlyingHttpResponse(s);
    }
}
