package io.sphere.sdk.client;

import com.fasterxml.jackson.core.type.TypeReference;

public interface Command<I, R> extends Requestable {

    TypeReference<R> typeReference();
}
