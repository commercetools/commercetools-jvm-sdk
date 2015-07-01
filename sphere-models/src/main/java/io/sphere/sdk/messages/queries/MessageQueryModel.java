package io.sphere.sdk.messages.queries;

import io.sphere.sdk.messages.Message;
import io.sphere.sdk.queries.DefaultModelQueryModelImpl;
import io.sphere.sdk.queries.QueryModel;
import io.sphere.sdk.queries.QueryModelImpl;
import io.sphere.sdk.queries.ReferenceQueryModel;

import java.util.Optional;

public class MessageQueryModel extends DefaultModelQueryModelImpl<Message> {

    public MessageQueryModel(final Optional<? extends QueryModel<Message>> parent, final Optional<String> pathSegment) {
        super(parent, pathSegment);
    }

    public static MessageQueryModel of() {
        return new MessageQueryModel(Optional.<QueryModelImpl<Message>>empty(), Optional.<String>empty());
    }

    public MessageTypeQueryModel type() {
        return new MessageTypeQueryModelImpl(Optional.of(this), "type");
    }

    public ReferenceQueryModel<Message, Object> resource() {
        return new ReferenceQueryModel<>(Optional.of(this), "resource");
    }
}
