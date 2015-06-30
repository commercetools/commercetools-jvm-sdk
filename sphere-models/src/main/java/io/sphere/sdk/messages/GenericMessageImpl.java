package io.sphere.sdk.messages;

import io.sphere.sdk.models.DefaultModelImpl;
import io.sphere.sdk.models.Reference;

import java.time.ZonedDateTime;

public abstract class GenericMessageImpl<R> extends DefaultModelImpl<Message> implements Message {
    protected final long sequenceNumber;
    protected final Reference<R> resource;
    protected final long resourceVersion;
    protected final String type;

    public GenericMessageImpl(final String id, final long version, final ZonedDateTime createdAt, final ZonedDateTime lastModifiedAt, final Reference<R> resource, final long sequenceNumber, final long resourceVersion, final String type) {
        super(id, version, createdAt, lastModifiedAt);
        this.resource = resource;
        this.sequenceNumber = sequenceNumber;
        this.resourceVersion = resourceVersion;
        this.type = type;
    }

    @Override
    public long getResourceVersion() {
        return resourceVersion;
    }

    @Override
    public long getSequenceNumber() {
        return sequenceNumber;
    }

    @Override
    public String getType() {
        return type;
    }

    @Override
    public Reference<Message> toReference() {
        return Reference.of(Message.typeId(), getId(), this);
    }

    @Override
    public Reference<R> getResource() {
        return resource;
    }
}
