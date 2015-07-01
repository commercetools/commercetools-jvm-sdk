package io.sphere.sdk.messages;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.sphere.sdk.models.Reference;

@JsonDeserialize(as = GenericMessageImpl.class)
public interface GenericMessage<T> extends Message {
    Reference<T> getResource();
}
