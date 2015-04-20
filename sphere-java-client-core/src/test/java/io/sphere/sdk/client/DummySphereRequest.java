package io.sphere.sdk.client;

import io.sphere.sdk.http.HttpMethod;
import io.sphere.sdk.http.HttpResponse;
import io.sphere.sdk.models.Base;

public class DummySphereRequest extends Base implements SphereRequest<String> {
    public static final String DEFAULT_RESPONSE_OBJECT = "da";
    private final String marker;

    private DummySphereRequest(final String marker) {
        this.marker = marker;
    }

    @Override
    public String deserialize(final HttpResponse httpResponse) {
        return DEFAULT_RESPONSE_OBJECT;
    }

    @Override
    public HttpRequestIntent httpRequestIntent() {
        return HttpRequestIntent.of(HttpMethod.GET, "/foo");
    }

    public static DummySphereRequest of(final String marker) {
        return new DummySphereRequest(marker);
    }

    public static DummySphereRequest of() {
        return of("");
    }

    public String getMarker() {
        return marker;
    }
}
