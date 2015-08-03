package io.sphere.sdk.messages.queries;

import io.sphere.sdk.messages.Message;
import io.sphere.sdk.messages.MessageDerivatHint;
import io.sphere.sdk.queries.QueryPredicate;
import io.sphere.sdk.queries.StringQueryModel;

public interface MessageTypeQueryModel extends StringQueryModel<Message> {
    QueryPredicate<Message> is(MessageDerivatHint<?> hint);
}
