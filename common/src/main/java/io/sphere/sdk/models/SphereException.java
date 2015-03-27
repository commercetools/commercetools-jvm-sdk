package io.sphere.sdk.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.sphere.sdk.client.SphereRequest;
import io.sphere.sdk.http.HttpResponse;
import io.sphere.sdk.meta.BuildInfo;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

/**
 * <span id="exception-summary">Base class for all exceptions related to the SDK.</span>
 *
 */
public class SphereException extends RuntimeException {
    static final long serialVersionUID = 0L;

    private Optional<SphereRequest<?>> sphereRequest = Optional.empty();
    @JsonIgnore
    protected Optional<HttpResponse> httpResponse = Optional.empty();
    private Optional<String> projectKey = Optional.empty();
    private Optional<String> httpThing = Optional.empty();
    private List<String> additionalNotes = new LinkedList<>();

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

    public final Optional<String> getProjectKey() {
        return projectKey;
    }

    public final Optional<SphereRequest<?>> getSphereRequest() {
        return sphereRequest;
    }

    public final Optional<HttpResponse> getHttpResponse() {
        return httpResponse;
    }

    public void setProjectKey(final Optional<String> projectKey) {
        this.projectKey = projectKey;
    }

    public void setProjectKey(final String projectKey) {
        this.projectKey = Optional.of(projectKey);
    }

    public void setSphereRequest(final SphereRequest<?> sphereRequest) {
        this.sphereRequest = Optional.of(sphereRequest);
    }

    public void addNote(final String note) {
        additionalNotes.add(note);
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
                .append("additional notes: ").append(additionalNotes).append("\n")
                .append("Javadoc: ").append("http://sphereio.github.io/sphere-jvm-sdk/javadoc/").append(BuildInfo.version()).append("/").append(this.getClass().getCanonicalName().replace('.', '/')).append(".html").append("\n")
                .append("===== END EXCEPTION OUTPUT =====").toString();
    }

    public void setUnderlyingHttpResponse(final HttpResponse httpResponse) {
        this.httpResponse = Optional.of(httpResponse.withoutRequest());
    }

    public List<String> getAdditionalNotes() {
        return additionalNotes;
    }
}
