package io.sphere.sdk.messages.queries;

import io.sphere.sdk.expansion.ExpansionPath;
import io.sphere.sdk.messages.Message;
import io.sphere.sdk.messages.expansion.MessageExpansionModel;
import io.sphere.sdk.models.Identifiable;
import io.sphere.sdk.queries.MetaModelFetchDsl;

import java.util.List;
import java.util.function.Function;

/**
 * {@include.example io.sphere.sdk.messages.queries.MessageByIdFetchTest#execution()}
 *
 * If you need to receive one specific message class, like {@link io.sphere.sdk.orders.messages.DeliveryAddedMessage},
 * use {@link MessageQuery} with a predicate by id.
 */
public interface MessageByIdFetch extends MetaModelFetchDsl<Message, Message, MessageByIdFetch, MessageExpansionModel<Message>> {
    static MessageByIdFetch of(final Identifiable<Message> message) {
        return of(message.getId());
    }

    static MessageByIdFetch of(final String id) {
        return new MessageByIdFetchImpl(id);
    }

    @Override
    MessageByIdFetch plusExpansionPaths(final Function<MessageExpansionModel<Message>, ExpansionPath<Message>> m);

    @Override
    MessageByIdFetch withExpansionPaths(final Function<MessageExpansionModel<Message>, ExpansionPath<Message>> m);

    @Override
    List<ExpansionPath<Message>> expansionPaths();

    @Override
    MessageByIdFetch plusExpansionPaths(final ExpansionPath<Message> expansionPath);

    @Override
    MessageByIdFetch withExpansionPaths(final ExpansionPath<Message> expansionPath);

    @Override
    MessageByIdFetch withExpansionPaths(final List<ExpansionPath<Message>> expansionPaths);
}
