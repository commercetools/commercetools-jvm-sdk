package io.sphere.sdk.client;

import com.fasterxml.jackson.databind.JsonNode;
import io.sphere.sdk.http.HttpResponse;
import io.sphere.sdk.models.Base;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import javax.annotation.Nullable;

/**
 * A wrapper for a {@link SphereRequest} to receive the Java object as well as the {@link JsonNode} representation.
 *
 * {@include.example io.sphere.sdk.client.JavaAndJsonSphereRequestIntegrationTest#execute()}
 *
 * @param <T> the Java class of the originals {@link SphereRequest}s result.
 */
public final class JavaAndJsonSphereRequest<T> extends Base implements SphereRequest<Pair<T, JsonNode>> {
    private final SphereRequest<T> delegate;

    private JavaAndJsonSphereRequest(final SphereRequest<T> delegate) {
        this.delegate = delegate;
    }

    public static <T> JavaAndJsonSphereRequest<T> of(final SphereRequest<T> delegate) {
        return new JavaAndJsonSphereRequest<>(delegate);
    }

    @Nullable
    @Override
    public Pair<T, JsonNode> deserialize(final HttpResponse httpResponse) {
        final JsonNode jsonNode = JsonNodeSphereRequest.of(delegate).deserialize(httpResponse);
        final T javaObject = delegate.deserialize(httpResponse);
        return ImmutablePair.of(javaObject, jsonNode);
    }

    @Override
    public HttpRequestIntent httpRequestIntent() {
        return delegate.httpRequestIntent();
    }

    @Override
    public boolean canDeserialize(final HttpResponse httpResponse) {
        return delegate.canDeserialize(httpResponse);
    }
}
