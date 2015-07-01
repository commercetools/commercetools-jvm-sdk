package io.sphere.sdk.messages.queries;

import io.sphere.sdk.messages.Message;
import io.sphere.sdk.messages.MessageDerivatHint;
import io.sphere.sdk.queries.QueryModel;
import io.sphere.sdk.queries.QueryPredicate;
import io.sphere.sdk.queries.StringQuerySortingModel;

import java.util.Arrays;
import java.util.Optional;

final class MessageTypeQueryModelImpl extends StringQuerySortingModel<Message> implements MessageTypeQueryModel {
    public MessageTypeQueryModelImpl(final Optional<? extends QueryModel<Message>> parent, final String pathSegment) {
        super(parent, pathSegment);
    }

    @Override
    public QueryPredicate<Message> is(final MessageDerivatHint<?> hint) {
        return hint.filterPredicate(Optional.empty());
    }

    @Override
    public QueryPredicate<Message> isIn(final MessageDerivatHint<?> hint, final MessageDerivatHint<?> ... moreHints) {
        final QueryPredicate<Message> seedPredicate = hint.filterPredicate(Optional.empty());
        return Arrays.stream(moreHints)
                .map(h -> h.filterPredicate(Optional.empty()))
                .reduce(seedPredicate, (left, right) -> left.or(right));
    }
}
