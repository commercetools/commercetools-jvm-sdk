package io.sphere.sdk.client;

import com.fasterxml.jackson.core.type.TypeReference;

public class Fetch<T> implements Requestable<T> {
    @Override
    public HttpRequest httpRequest() {
        return null;
    }

    @Override
    public TypeReference<T> typeReference() {
        return null;
    }
}
