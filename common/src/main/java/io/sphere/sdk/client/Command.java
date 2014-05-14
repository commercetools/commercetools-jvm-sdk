package io.sphere.sdk.client;

import com.fasterxml.jackson.core.type.TypeReference;

public interface Command<T, C> extends Requestable {

    TypeReference<T> typeReference();
}
