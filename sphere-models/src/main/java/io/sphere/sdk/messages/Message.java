package io.sphere.sdk.messages;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.sphere.sdk.models.DefaultModel;
import io.sphere.sdk.models.Reference;

@JsonDeserialize(as = MessageImpl.class)
public interface Message extends DefaultModel<Message> {
    static String typeId(){
        return "message";
    }

    static TypeReference<Message> typeReference(){
        return new TypeReference<Message>() {
            @Override
            public String toString() {
                return "TypeReference<Message>";
            }
        };
    }

    Reference<? extends Object> getResource();

    long getResourceVersion();

    long getSequenceNumber();

    String getType();

    @Override
    default Reference<Message> toReference() {
        return Reference.of(Message.typeId(), getId());
    }
}
