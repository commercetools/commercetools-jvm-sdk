package io.sphere.sdk.messages;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.databind.JsonNode;

import java.time.ZonedDateTime;

public class MessageImpl extends GenericMessageImpl<Object> {

    @JsonCreator
    public MessageImpl(final String id, final long version, final ZonedDateTime createdAt, final ZonedDateTime lastModifiedAt, final JsonNode resource, final long sequenceNumber, final long resourceVersion, final String type) {
        super(id, version, createdAt, lastModifiedAt, resource, sequenceNumber, resourceVersion, type);
    }
}
