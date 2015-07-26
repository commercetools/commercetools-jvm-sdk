package io.sphere.sdk.messages.queries;

import io.sphere.sdk.messages.Message;
import io.sphere.sdk.queries.AnyReferenceQueryModel;
import io.sphere.sdk.queries.DefaultModelQueryModelImpl;
import io.sphere.sdk.queries.QueryModel;

public class MessageQueryModel extends DefaultModelQueryModelImpl<Message> {

    public MessageQueryModel(final QueryModel<Message> parent, final String pathSegment) {
        super(parent, pathSegment);
    }

    public static MessageQueryModel of() {
        return new MessageQueryModel(null, null);
    }

    public MessageTypeQueryModel type() {
        return new MessageTypeQueryModelImpl(this, "type");
    }

    public AnyReferenceQueryModel<Message> resource() {
        return anyReferenceModel("resource");
    }
}
