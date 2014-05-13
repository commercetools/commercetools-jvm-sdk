package io.sphere.sdk.client;

import com.fasterxml.jackson.core.type.TypeReference;

public interface Requestable<T> {
    HttpRequest httpRequest();
    TypeReference<T> typeReference();
}
