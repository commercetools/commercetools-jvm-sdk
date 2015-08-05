package io.sphere.sdk.messages.queries;

import io.sphere.sdk.messages.Message;
import io.sphere.sdk.queries.AnyReferenceQueryModel;
import io.sphere.sdk.queries.ResourceQueryModelImpl;
import io.sphere.sdk.queries.QueryModel;

final class MessageQueryModelImpl extends ResourceQueryModelImpl<Message> implements MessageQueryModel {

    public MessageQueryModelImpl(final QueryModel<Message> parent, final String pathSegment) {
        super(parent, pathSegment);
    }

    public static MessageQueryModel of() {
        return new MessageQueryModelImpl(null, null);
    }

    @Override
    public MessageTypeQueryModel type() {
        return new MessageTypeQueryModelImpl(this, "type");
    }

    @Override
    public AnyReferenceQueryModel<Message> resource() {
        return anyReferenceModel("resource");
    }
}
