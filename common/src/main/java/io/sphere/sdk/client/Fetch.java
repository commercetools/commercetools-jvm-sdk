package io.sphere.sdk.client;

import com.fasterxml.jackson.core.type.TypeReference;

public abstract class Fetch<I, R> implements Requestable {
    private final TypeReference<R> typeReference;

    public Fetch(final TypeReference<R> typeReference) {
        this.typeReference = typeReference;
    }

    public TypeReference<R> typeReference() {
        return typeReference;
    }
}
