package io.sphere.sdk.client;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import io.sphere.sdk.http.HttpMethod;
import io.sphere.sdk.http.HttpResponse;
import io.sphere.sdk.json.SphereJsonUtils;

import java.util.Optional;

public class JsonNodeSphereRequest implements SphereRequest<Optional<JsonNode>> {
    private final HttpRequestIntent httpRequestIntent;

    protected JsonNodeSphereRequest(final HttpRequestIntent httpRequestIntent) {
        this.httpRequestIntent = httpRequestIntent;
    }

    @Override
    public Optional<JsonNode> deserialize(final HttpResponse httpResponse) {
        return httpResponse.getResponseBody().map(body -> SphereJsonUtils.readObject(new TypeReference<JsonNode>() {
        }, body));
    }

    @Override
    public HttpRequestIntent httpRequestIntent() {
        return httpRequestIntent;
    }

    public static JsonNodeSphereRequest of(final HttpRequestIntent httpRequestIntent) {
        return new JsonNodeSphereRequest(httpRequestIntent);
    }

    public static JsonNodeSphereRequest of(final HttpMethod httpMethod, final String path, final JsonNode body) {
        return of(httpMethod, path, Optional.of(body));
    }

    public static JsonNodeSphereRequest of(final HttpMethod httpMethod, final String path) {
        return of(httpMethod, path, Optional.empty());
    }

    public static <T> JsonNodeSphereRequest of(final SphereRequest<T> other) {
        return of(other.httpRequestIntent());
    }

    public static JsonNodeSphereRequest of(final HttpMethod httpMethod, final String path, final Optional<JsonNode> body) {
        final HttpRequestIntent httpRequestIntent = body
                .map(b -> HttpRequestIntent.of(httpMethod, path, SphereJsonUtils.toJson(b)))
                .orElseGet(() -> HttpRequestIntent.of(httpMethod, path));
        return of(httpRequestIntent);
    }
}
