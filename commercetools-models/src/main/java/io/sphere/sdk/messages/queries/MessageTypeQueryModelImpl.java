package io.sphere.sdk.messages.queries;

import io.sphere.sdk.messages.Message;
import io.sphere.sdk.messages.MessageDerivateHint;
import io.sphere.sdk.queries.*;

final class MessageTypeQueryModelImpl extends QueryModelImpl<Message> implements MessageTypeQueryModel {
    public MessageTypeQueryModelImpl(final QueryModel<Message> parent, final String pathSegment) {
        super(parent, pathSegment);
    }

    @Override
    public QueryPredicate<Message> is(final MessageDerivateHint<?> hint) {
        return hint.predicate();
    }

    @Override
    public QueryPredicate<Message> is(final String s) {
        return isPredicate(s);
    }

    @Override
    public QueryPredicate<Message> isNot(final String s) {
        return isNotPredicate(s);
    }

    @Override
    public QueryPredicate<Message> isIn(final Iterable<String> args) {
        return isInPredicate(args);
    }

    @Override
    public QueryPredicate<Message> isGreaterThan(final String value) {
        return isGreaterThanPredicate(value);
    }

    @Override
    public QueryPredicate<Message> isLessThan(final String value) {
        return isLessThanPredicate(value);
    }

    @Override
    public QueryPredicate<Message> isLessThanOrEqualTo(final String value) {
        return isLessThanOrEqualToPredicate(value);
    }

    @Override
    public QueryPredicate<Message> isGreaterThanOrEqualTo(final String value) {
        return isGreaterThanOrEqualToPredicate(value);
    }

    @Override
    public QueryPredicate<Message> isNotIn(final Iterable<String> args) {
        return isNotInPredicate(args);
    }

    @Override
    public QueryPredicate<Message> isPresent() {
        return isPresentPredicate();
    }

    @Override
    public QueryPredicate<Message> isNotPresent() {
        return isNotPresentPredicate();
    }
}
