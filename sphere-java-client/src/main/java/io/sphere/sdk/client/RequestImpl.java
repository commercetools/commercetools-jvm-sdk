package io.sphere.sdk.client;

import com.fasterxml.jackson.core.type.TypeReference;

class RequestImpl<T> implements Request<T> {

    @Override
    public HttpRequest httpRequest() {
        return null;
    }

    @Override
    public TypeReference<T> typeReference() {
        return null;
    }
}
