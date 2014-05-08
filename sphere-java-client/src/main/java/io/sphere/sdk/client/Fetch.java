package io.sphere.sdk.client;

import com.fasterxml.jackson.core.type.TypeReference;

public class Fetch<T> implements Requestable<T> {
    private final TypeReference<T> typeReference;

    public Fetch(final TypeReference<T> typeReference) {
        this.typeReference = typeReference;
    }

    @Override
    public HttpRequest httpRequest() {
        return null;
    }

    @Override
    public TypeReference<T> typeReference() {
        return typeReference;
    }
}
