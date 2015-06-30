package io.sphere.sdk.messages;

import com.fasterxml.jackson.annotation.JsonCreator;
import io.sphere.sdk.models.Reference;

import java.time.ZonedDateTime;

public class MessageImpl extends GenericMessageImpl<Object> {

    @JsonCreator
    public MessageImpl(final String id, final long version, final ZonedDateTime createdAt, final ZonedDateTime lastModifiedAt, final Reference<Object> resource, final long sequenceNumber, final long resourceVersion, final String type) {
        super(id, version, createdAt, lastModifiedAt, resource, sequenceNumber, resourceVersion, type);
    }
}
