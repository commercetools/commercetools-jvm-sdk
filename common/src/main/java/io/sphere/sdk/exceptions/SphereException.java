package io.sphere.sdk.exceptions;

import io.sphere.sdk.client.HttpRequestIntent;
import io.sphere.sdk.client.SphereRequest;
import io.sphere.sdk.meta.BuildInfo;

import java.util.Date;
import java.util.Optional;

/**
 * <span id="exception-summary">Base class for all exceptions related to the SDK.</span>
 *
 */
public class SphereException extends RuntimeException {
    static final long serialVersionUID = 0L;

    private Optional<String> sphereRequest = Optional.empty();
    private Optional<String> underlyingHttpRequest = Optional.empty();
    private Optional<String> underlyingHttpResponse = Optional.empty();
    private Optional<String> projectKey = Optional.empty();
    private Optional<String> httpThing = Optional.empty();

    public SphereException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public SphereException() {
    }

    public SphereException(final String message) {
        super(message);
    }

    public SphereException(final Throwable cause) {
        super(cause);
    }

    public Optional<String> getProjectKey() {
        return projectKey;
    }

    public Optional<String> getSphereRequest() {
        return sphereRequest;
    }

    public Optional<String> getUnderlyingHttpRequest() {
        return underlyingHttpRequest;
    }

    public Optional<String> getUnderlyingHttpResponse() {
        return underlyingHttpResponse;
    }

    public void setProjectKey(final Optional<String> projectKey) {
        this.projectKey = projectKey;
    }

    public void setUnderlyingHttpRequest(final Optional<String> underlyingHttpRequest) {
        this.underlyingHttpRequest = underlyingHttpRequest;
    }

    public void setUnderlyingHttpResponse(final Optional<String> underlyingHttpResponse) {
        this.underlyingHttpResponse = underlyingHttpResponse;
    }

    public void setProjectKey(final String projectKey) {
        this.projectKey = Optional.of(projectKey);
    }

    public void setSphereRequest(final SphereRequest<? extends Object> sphereRequest) {
        this.sphereRequest = Optional.of(sphereRequest.toString());
        final HttpRequestIntent intent = sphereRequest.httpRequestIntent();
        this.httpThing = Optional.of(intent.getHttpMethod() + " " + intent.getPath());
    }

    public void setUnderlyingHttpRequest(final String underlyingHttpRequest) {
        this.underlyingHttpRequest = Optional.of(underlyingHttpRequest);
    }

    public void setUnderlyingHttpResponse(final String underlyingHttpResponse) {
        this.underlyingHttpResponse = Optional.of(underlyingHttpResponse);
    }

    @Override
    public String getMessage() {
        StringBuilder builder = new StringBuilder("\n===== BEGIN EXCEPTION OUTPUT =====").append("\n");
        final String httpRequest = getUnderlyingHttpRequest().orElse("<unknown>");
        return builder
                .append("SDK: ").append(BuildInfo.version()).append("\n")
                .append("project: ").append(getProjectKey().orElse("<unknown>")).append("\n")
                .append(httpThing.map(x -> "intent: " + x + "\n").orElse(""))
                .append("Java: ").append(System.getProperty("java.version")).append("\n")
                .append("cwd: ").append(System.getProperty("user.dir")).append("\n")
                .append("date: ").append(new Date()).append("\n")
                .append("sphere request: ").append(getSphereRequest().orElse("<unknown>")).append("\n")
                .append("http request: ").append(httpRequest).append("\n")
                .append("http response: ").append(getUnderlyingHttpResponse().orElse("<unknown>")).append("\n")
                .append(Optional.ofNullable(super.getMessage()).map(s -> "detailMessage: " + s + "\n").orElse(""))
                .append("Javadoc: ").append("http://sphereio.github.io/sphere-jvm-sdk/javadoc/").append(BuildInfo.version()).append("/").append(this.getClass().getCanonicalName().replace('.', '/')).append(".html").append("\n")
                .append("===== END EXCEPTION OUTPUT =====").toString();
    }
}
