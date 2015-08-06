package io.sphere.sdk.messages.queries;

import io.sphere.sdk.messages.Message;
import io.sphere.sdk.queries.AnyReferenceQueryModel;
import io.sphere.sdk.queries.ResourceQueryModel;

public interface MessageQueryModel extends ResourceQueryModel<Message> {
    MessageTypeQueryModel type();

    AnyReferenceQueryModel<Message> resource();

    static MessageQueryModel of() {
        return MessageQueryModelImpl.of();
    }
}
