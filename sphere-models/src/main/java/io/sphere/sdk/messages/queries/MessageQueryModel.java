package io.sphere.sdk.messages.queries;

import io.sphere.sdk.messages.Message;
import io.sphere.sdk.queries.DefaultModelQueryModelImpl;
import io.sphere.sdk.queries.QueryModel;
import io.sphere.sdk.queries.QueryModelImpl;
import io.sphere.sdk.queries.StringQueryModel;

import java.util.Optional;

public class MessageQueryModel extends DefaultModelQueryModelImpl<Message> {

    private MessageQueryModel(Optional<? extends QueryModel<Message>> parent, Optional<String> pathSegment) {
        super(parent, pathSegment);
    }

    public static MessageQueryModel of() {
        return new MessageQueryModel(Optional.<QueryModelImpl<Message>>empty(), Optional.<String>empty());
    }

    public StringQueryModel<Message> type() {
        return stringModel("type");
    }
}
