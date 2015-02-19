package io.sphere.sdk.client;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import io.sphere.sdk.http.HttpMethod;
import io.sphere.sdk.http.HttpRequest;
import io.sphere.sdk.http.HttpResponse;
import io.sphere.sdk.utils.JsonUtils;

import java.util.Optional;
import java.util.function.Function;

public class JsonNodeSphereRequest implements SphereRequest<Optional<JsonNode>> {
    private final HttpMethod httpMethod;
    private final String path;
    private final Optional<JsonNode> body;

    protected JsonNodeSphereRequest(final HttpMethod httpMethod, final String path, final Optional<JsonNode> body) {
        this.body = body;
        this.httpMethod = httpMethod;
        this.path = path;
    }

    @Override
    public Function<HttpResponse, Optional<JsonNode>> resultMapper() {
        return httpResponse -> httpResponse.getResponseBody().map(body -> JsonUtils.readObject(new TypeReference<JsonNode>() {
        }, body));
    }

    @Override
    public HttpRequest httpRequestIntent() {
        return body
                .map(b -> HttpRequest.of(httpMethod, path, JsonUtils.toJson(b)).httpRequestIntent())
                .orElseGet(() -> HttpRequest.of(httpMethod, path));
    }

    public static JsonNodeSphereRequest of(final HttpMethod httpMethod, final String path, final JsonNode body) {
        return of(httpMethod, path, Optional.of(body));
    }

    public static JsonNodeSphereRequest of(final HttpMethod httpMethod, final String path) {
        return of(httpMethod, path, Optional.empty());
    }

    public static JsonNodeSphereRequest of(final HttpMethod httpMethod, final String path, final Optional<JsonNode> body) {
        return new JsonNodeSphereRequest(httpMethod, path, body);
    }
}
