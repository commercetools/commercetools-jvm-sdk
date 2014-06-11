package io.sphere.sdk.client;


import com.google.common.base.Function;
import com.google.common.base.Optional;
import io.sphere.sdk.meta.BuildInfo;

import javax.annotation.Nullable;

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
        final String clientInfo = String.format("SDK version: %s, Java runtime: %s, project key: %s",
                BuildInfo.version(), System.getProperty("java.version"), projectKey.or("<unknown>"));
        return super.getMessage() + "\n" + clientInfo + sphereRequest.transform(new Function<String, String>() {
            @Nullable
            @Override
            public String apply(@Nullable String input) {
                return "\nsphere request: " + input;
            }
        }).or("") + underlyingHttpRequest.transform(new Function<String, String>() {
            @Nullable
            @Override
            public String apply(@Nullable String input) {
                return "\nunderlying http request: " + input;
            }
        }).or("") + underlyingHttpResponse.transform(new Function<String, String>() {
            @Nullable
            @Override
            public String apply(@Nullable String input) {
                return "\nunderlying http response: " + input;
            }
        }).or("");
    }
}
