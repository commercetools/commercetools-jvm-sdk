package io.sphere.sdk.messages;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.sphere.sdk.models.Resource;
import io.sphere.sdk.models.Reference;

@JsonDeserialize(as = MessageImpl.class)
public interface Message extends Resource<Message> {
    static String referenceTypeId() {
        return "message";
    }

    /**
     *
     * @deprecated use {@link #referenceTypeId()} instead
     * @return referenceTypeId
     */
    @Deprecated
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

    Reference<?> getResource();

    Long getResourceVersion();

    Long getSequenceNumber();

    String getType();

    /**
     * Gets the top level fields not mapped by the current message class.
     * @return json
     */
    JsonNode getPayload();

    @Override
    default Reference<Message> toReference() {
        return Reference.of(Message.referenceTypeId(), getId());
    }

    <T extends Message> T as(final Class<T> messageClass);


    static Reference<Message> referenceOfId(final String id) {
        return Reference.of(referenceTypeId(), id);
    }
}
