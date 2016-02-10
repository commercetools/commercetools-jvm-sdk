package io.sphere.sdk.client;

import com.fasterxml.jackson.core.type.TypeReference;
import io.sphere.sdk.models.Base;

/**
 * Internal class.
 * @param <T> resource type
 */
public final class JsonEndpoint<T> extends Base {
    private final TypeReference<T> typeReference;
    private final String endpoint;

    private JsonEndpoint(final TypeReference<T> typeReference, final String endpoint) {
        this.typeReference = typeReference;
        this.endpoint = endpoint;
    }

    public TypeReference<T> typeReference() {
        return typeReference;
    }

    public String endpoint() {
        return endpoint;
    }

    public static <T> JsonEndpoint<T> of(final TypeReference<T> typeReference, final String endpoint) {
        return new JsonEndpoint<>(typeReference, endpoint);
    }

    public <V> JsonEndpoint<V> withTypeReference(final TypeReference<V> newTypeReference) {
        return of(newTypeReference, endpoint);
    }
}
