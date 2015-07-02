package io.sphere.sdk.messages.queries;

import io.sphere.sdk.messages.Message;
import io.sphere.sdk.queries.*;

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

    public AnyReferenceQueryModel<Message> resource() {
        return anyReferenceModel("resource");
    }
}
