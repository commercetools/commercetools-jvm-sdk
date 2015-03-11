package io.sphere.sdk.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.sphere.sdk.client.HttpRequestIntent;
import io.sphere.sdk.client.SphereRequest;
import io.sphere.sdk.http.HttpResponse;
import io.sphere.sdk.meta.BuildInfo;

import java.util.Date;
import java.util.Optional;

/**
 * <span id="exception-summary">Base class for all exceptions related to the SDK.</span>
 *
 */
public class SphereException extends RuntimeException {
    static final long serialVersionUID = 0L;

    private Optional<SphereRequest<? extends Object>> sphereRequest = Optional.empty();
    @JsonIgnore
    private Optional<HttpResponse> httpResponse = Optional.empty();
    private Optional<String> projectKey = Optional.empty();

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

    private Optional<SphereRequest<? extends Object>> getSphereRequest() {
        return sphereRequest;
    }

    public Optional<HttpResponse> getHttpResponse() {
        return httpResponse;
    }

    public void setProjectKey(final Optional<String> projectKey) {
        this.projectKey = projectKey;
    }

    public void setProjectKey(final String projectKey) {
        this.projectKey = Optional.of(projectKey);
    }

    public void setSphereRequest(final SphereRequest<? extends Object> sphereRequest) {
        this.sphereRequest = Optional.of(sphereRequest);
    }

    @Override
    public final String getMessage() {
        StringBuilder builder = new StringBuilder("\n===== BEGIN EXCEPTION OUTPUT =====").append("\n");
        final String httpRequest = getSphereRequest().map(x -> x.httpRequestIntent()).map(Object::toString).orElse("<unknown>");
        return builder
                .append("SDK: ").append(BuildInfo.version()).append("\n")
                .append("project: ").append(getProjectKey().orElse("<unknown>")).append("\n")
                .append(getSphereRequest().map(x -> x.httpRequestIntent()).map(x -> "" + x.getHttpMethod() + " " + x.getPath()).map(x -> "endpoint: " + x + "\n").orElse(""))
                .append("Java: ").append(System.getProperty("java.version")).append("\n")
                .append("cwd: ").append(System.getProperty("user.dir")).append("\n")
                .append("date: ").append(new Date()).append("\n")
                .append("sphere request: ").append(getSphereRequest().map(Object::toString).orElse("<unknown>")).append("\n")
                //duplicated in case SphereRequest does not implement a proper to String
                .append("http request: ").append(httpRequest).append("\n")
                .append("http response: ").append(getHttpResponse().map(Object::toString).orElse("<unknown>")).append("\n")
                .append(Optional.ofNullable(super.getMessage()).map(s -> "detailMessage: " + s + "\n").orElse(""))
                .append("Javadoc: ").append("http://sphereio.github.io/sphere-jvm-sdk/javadoc/").append(BuildInfo.version()).append("/").append(this.getClass().getCanonicalName().replace('.', '/')).append(".html").append("\n")
                .append("===== END EXCEPTION OUTPUT =====").toString();
    }

    public void setUnderlyingHttpResponse(final HttpResponse httpResponse) {
        this.httpResponse = Optional.of(httpResponse.withoutRequest());

    }
}
