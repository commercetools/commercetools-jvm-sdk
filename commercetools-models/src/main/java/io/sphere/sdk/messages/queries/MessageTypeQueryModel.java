package io.sphere.sdk.messages.queries;

import io.sphere.sdk.messages.Message;
import io.sphere.sdk.messages.MessageDerivateHint;
import io.sphere.sdk.queries.QueryPredicate;
import io.sphere.sdk.queries.StringQueryModel;

public interface MessageTypeQueryModel extends StringQueryModel<Message> {
    QueryPredicate<Message> is(MessageDerivateHint<?> hint);
}
