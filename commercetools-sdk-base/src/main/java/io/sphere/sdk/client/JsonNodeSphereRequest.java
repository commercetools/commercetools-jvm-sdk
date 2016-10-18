package io.sphere.sdk.client;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import io.sphere.sdk.http.HttpMethod;
import io.sphere.sdk.http.HttpResponse;
import io.sphere.sdk.json.SphereJsonUtils;
import io.sphere.sdk.json.TypeReferences;
import io.sphere.sdk.models.Base;

import javax.annotation.Nullable;
import java.util.Optional;

/**
 * A {@link SphereRequest} which returns {@link JsonNode} instances instead of deserialized Java domain model classes.
 * On errors like 409, 502 etc. the normal typed exceptions will be thrown.
 *
 * {@include.example io.sphere.sdk.client.JsonNodeSphereRequestIntegrationTest#execution()}
 * {@include.example io.sphere.sdk.client.JsonNodeSphereRequestIntegrationTest#execution2()}
 *
 */
public final class JsonNodeSphereRequest extends Base implements SphereRequest<JsonNode> {
    private final HttpRequestIntent httpRequestIntent;

    protected JsonNodeSphereRequest(final HttpRequestIntent httpRequestIntent) {
        this.httpRequestIntent = httpRequestIntent;
    }

    @Nullable
    @Override
    public JsonNode deserialize(final HttpResponse httpResponse) {
        return Optional.ofNullable(httpResponse.getResponseBody())
                .map(body -> SphereJsonUtils.readObject(body, TypeReferences.jsonNodeTypeReference()))
                .orElse(null);
    }

    @Override
    public HttpRequestIntent httpRequestIntent() {
        return httpRequestIntent;
    }

    public static JsonNodeSphereRequest of(final HttpRequestIntent httpRequestIntent) {
        return new JsonNodeSphereRequest(httpRequestIntent);
    }

    public static JsonNodeSphereRequest of(final HttpMethod httpMethod, final String path) {
        return of(httpMethod, path, null);
    }

    public static <T> JsonNodeSphereRequest of(final SphereRequest<T> other) {
        return of(other.httpRequestIntent());
    }

    public static JsonNodeSphereRequest of(final HttpMethod httpMethod, final String path, @Nullable final JsonNode body) {
        final HttpRequestIntent httpRequestIntent = Optional.ofNullable(body)
                .map(b -> HttpRequestIntent.of(httpMethod, path, SphereJsonUtils.toJsonString(b)))
                .orElseGet(() -> HttpRequestIntent.of(httpMethod, path));
        return of(httpRequestIntent);
    }
}
