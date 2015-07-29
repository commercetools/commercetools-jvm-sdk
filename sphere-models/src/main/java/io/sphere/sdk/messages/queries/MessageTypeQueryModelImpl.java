package io.sphere.sdk.messages.queries;

import io.sphere.sdk.messages.Message;
import io.sphere.sdk.messages.MessageDerivatHint;
import io.sphere.sdk.queries.QueryModel;
import io.sphere.sdk.queries.QueryPredicate;
import io.sphere.sdk.queries.StringQuerySortingModelImpl;

import java.util.Arrays;

final class MessageTypeQueryModelImpl extends StringQuerySortingModelImpl<Message> implements MessageTypeQueryModel {
    public MessageTypeQueryModelImpl(final QueryModel<Message> parent, final String pathSegment) {
        super(parent, pathSegment);
    }

    @Override
    public QueryPredicate<Message> is(final MessageDerivatHint<?> hint) {
        return hint.predicate();
    }

    @Override
    public QueryPredicate<Message> isIn(final MessageDerivatHint<?> hint, final MessageDerivatHint<?> ... moreHints) {
        return Arrays.stream(moreHints)
                .map(h -> h.predicate())
                .reduce(hint.predicate(), (left, right) -> left.or(right));
    }
}
