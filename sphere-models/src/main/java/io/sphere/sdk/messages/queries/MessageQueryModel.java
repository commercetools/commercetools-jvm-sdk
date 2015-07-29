package io.sphere.sdk.messages.queries;

import io.sphere.sdk.messages.Message;
import io.sphere.sdk.queries.AnyReferenceQueryModel;
import io.sphere.sdk.queries.DefaultModelQueryModel;

public interface MessageQueryModel extends DefaultModelQueryModel<Message> {
    MessageTypeQueryModel type();

    AnyReferenceQueryModel<Message> resource();

    static MessageQueryModel of() {
        return MessageQueryModelImpl.of();
    }
}
