package io.sphere.sdk.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.sphere.sdk.client.HttpRequestIntent;
import io.sphere.sdk.client.SphereRequest;
import io.sphere.sdk.http.HttpRequest;
import io.sphere.sdk.http.HttpResponse;
import io.sphere.sdk.http.StringHttpRequestBody;
import io.sphere.sdk.json.SphereJsonUtils;
import io.sphere.sdk.meta.BuildInfo;
import org.apache.commons.lang3.StringUtils;

import javax.annotation.Nullable;
import java.nio.charset.StandardCharsets;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

/**
 * <span id="exception-summary">Base class for exceptions related to the SDK.</span>
 *
 */
public class SphereException extends RuntimeException {
    static final long serialVersionUID = 0L;
    @Nullable
    private SphereRequest<?> sphereRequest;
    @JsonIgnore
    @Nullable
    protected HttpResponse httpResponse;
    @Nullable
    private String projectKey;
    @Nullable
    private HttpRequest httpRequest;
    @Nullable
    private HttpRequestIntent httpRequestIntent;
    private List<String> additionalNotes = new LinkedList<>();
    private final String dateAsString = DateTimeFormatter.ISO_INSTANT.format(ZonedDateTime.now().withZoneSameInstant(ZoneOffset.UTC));

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

    @Nullable
    public final String getProjectKey() {
        return projectKey;
    }

    @Nullable
    public final SphereRequest<?> getSphereRequest() {
        return sphereRequest;
    }

    @Nullable
    public final HttpResponse getHttpResponse() {
        return httpResponse;
    }

    public void setProjectKey(@Nullable final String projectKey) {
        this.projectKey = projectKey;
    }

    public void setSphereRequest(@Nullable final SphereRequest<?> sphereRequest) {
        this.sphereRequest = sphereRequest;
        setHttpRequestIntent(sphereRequest.httpRequestIntent());
    }

    private void setHttpRequestIntent(@Nullable final HttpRequestIntent httpRequestIntent) {
        this.httpRequestIntent = httpRequestIntent;
    }

    public void setHttpRequest(@Nullable final HttpRequest httpRequest) {
        this.httpRequest = httpRequest;
    }

    public void addNote(final String note) {
        additionalNotes.add(note);
    }

    @Override
    public final String getMessage() {
        StringBuilder builder = new StringBuilder();
        return builder
                .append(Optional.ofNullable(super.getMessage()).map(s -> "detailMessage: " + s + "\n").orElse(""))
                .append(httpSummary())
                .append(responseBodyFormatted())
                .append("http response: ").append(Optional.ofNullable(getHttpResponse()).map(Object::toString).orElse("<unknown>")).append("\n")
                .append("SDK: ").append(BuildInfo.version()).append("\n")
                .append("project: ").append(Optional.ofNullable(getProjectKey()).orElse("<unknown>")).append("\n")
                .append(Optional.ofNullable(getSphereRequest()).map(x -> x.httpRequestIntent()).map(x -> "" + x.getHttpMethod() + " " + x.getPath()).map(x -> "endpoint: " + x + "\n").orElse(""))
                .append("Java: ").append(System.getProperty("java.version")).append("\n")
                .append("cwd: ").append(System.getProperty("user.dir")).append("\n")
                .append("sphere request: ").append(Optional.ofNullable(getSphereRequest()).map(Object::toString).orElse("<unknown>")).append("\n")
                        //duplicated in case SphereRequest does not implement a proper to String
                .append(httpRequestLine())
                .append(requestBodyFormatted())
                .append("additional notes: ").append(additionalNotes).append("\n")
                .append("Javadoc: ").append("https://commercetools.github.io/commercetools-jvm-sdk/apidocs/").append(this.getClass().getCanonicalName().replace('.', '/')).append(".html").append("\n")
                .toString();
    }

    public final String httpSummary() {
        try {
            final StringBuilder builder = new StringBuilder();
            if (this.httpRequest != null || this.httpRequestIntent != null) {
                builder.append("summary: ");
                final String httpMethod = Optional.ofNullable(this.httpRequest)
                        .map(r -> r.getHttpMethod().toString())
                        .orElseGet(() -> this.httpRequestIntent.getHttpMethod().toString());

                final String project = StringUtils.defaultString(projectKey);

                final String path = Optional.ofNullable(this.httpRequest)
                        .map(r -> r.getUrl())
                        .orElseGet(() -> project + this.httpRequestIntent.getPath());

                final String responseCode = " with " + Optional.ofNullable(this.httpResponse)
                        .map(r -> r.getStatusCode())
                        .map(r -> r.toString())
                        .map(r -> "response code " + r)
                        .orElse("an unknown status code");


                final String correlationIdHeaderName = "X-Correlation-ID";
                final String correlationId = Optional.ofNullable(this.httpResponse)
                        .map(r -> r.getHeaders())
                        .flatMap(headers -> headers.findFlatHeader(correlationIdHeaderName))
                        .map(id -> " with " + correlationIdHeaderName + " `" + id + "`")
                        .orElse("");

                builder.append(httpMethod)
                        .append(" ")
                        .append(path)
                        .append(" failed ")
                        .append(responseCode)
                        .append(correlationId)
                        .append(" on ")
                        .append(dateAsString)
                        .append("\n");
            }
            return builder.toString();
        } catch (final Exception e) {
            return "";
        }
    }

    private String httpRequestLine() {
        if (httpRequest == null) {
            return "";
        } else {
            return "http request: " + httpRequest.toString() + "\n";
        }
    }

    private String responseBodyFormatted() {
        try {
            return Optional.ofNullable(getHttpResponse())
                    .map(r -> r.getResponseBody())
                    .map(b -> SphereJsonUtils.prettyPrint(new String(b, StandardCharsets.UTF_8)))
                    .map(s -> "http response formatted body: " + s + "\n")
                    .orElse("");
        } catch (final Exception e) {
            return "";
        }
    }

    private String requestBodyFormatted() {
        try {
            final Optional<String> stringBodyOfHttpRequest = stringBodyOfHttpRequest();
            final Optional<String> stringBodyOfHttpRequestIntentSupplier = Optional.ofNullable(httpRequestIntent)
                    .map(r -> r.getBody())
                    .filter(r -> r instanceof StringHttpRequestBody)
                    .map(r -> ((StringHttpRequestBody) r).getSecuredBody());
            return Optional.ofNullable(stringBodyOfHttpRequest.orElse(stringBodyOfHttpRequestIntentSupplier.orElse(null)))
                    .map(SphereJsonUtils::prettyPrint)
                    .map(s -> "http request formatted body: " + s + "\n")
                    .orElse("");
        } catch (final Exception e) {
            return "";
        }
    }

    private Optional<String> stringBodyOfHttpRequest() {
        return Optional.ofNullable(httpRequest)
                .map(r -> r.getBody())
                .filter(x -> x instanceof StringHttpRequestBody)
                .map(b -> ((StringHttpRequestBody) b).getSecuredBody());
    }


    private static String getVersionForJavadoc() {
        return getVersionForJavadoc(BuildInfo.version());
    }

    //package scope for testing
    static String getVersionForJavadoc(final String version) {
        final boolean releaseVersion = Pattern.compile("^(\\d+\\.\\d+\\.\\d+)(-M\\d+)?$").matcher(version).matches();
        return releaseVersion ? "v" + version : version;
    }

    public void setUnderlyingHttpResponse(final HttpResponse httpResponse) {
        this.httpResponse = httpResponse.withoutRequest();
    }

    public List<String> getAdditionalNotes() {
        return additionalNotes;
    }
}
