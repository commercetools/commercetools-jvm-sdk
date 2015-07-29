package io.sphere.sdk.messages.queries;

import io.sphere.sdk.messages.Message;
import io.sphere.sdk.queries.AnyReferenceQueryModel;

public interface MessageQueryModel {
    MessageTypeQueryModel type();

    AnyReferenceQueryModel<Message> resource();

    static MessageQueryModel of() {
        return MessageQueryModelImpl.of();
    }
}
