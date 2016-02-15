package io.sphere.sdk.messages.queries;

import io.sphere.sdk.expansion.ExpansionPath;
import io.sphere.sdk.messages.Message;
import io.sphere.sdk.messages.expansion.MessageExpansionModel;
import io.sphere.sdk.models.Identifiable;
import io.sphere.sdk.queries.MetaModelGetDsl;

import java.util.List;
import java.util.function.Function;

/**
 * {@include.example io.sphere.sdk.messages.queries.MessageByIdGetIntegrationTest#execution()}
 *
 * If you need to receive one specific message class, like {@link io.sphere.sdk.orders.messages.DeliveryAddedMessage},
 * use {@link MessageQuery} with a predicate by id.
 */
public interface MessageByIdGet extends MetaModelGetDsl<Message, Message, MessageByIdGet, MessageExpansionModel<Message>> {
    static MessageByIdGet of(final Identifiable<Message> message) {
        return of(message.getId());
    }

    static MessageByIdGet of(final String id) {
        return new MessageByIdGetImpl(id);
    }

    @Override
    List<ExpansionPath<Message>> expansionPaths();

    @Override
    MessageByIdGet plusExpansionPaths(final ExpansionPath<Message> expansionPath);

    @Override
    MessageByIdGet withExpansionPaths(final ExpansionPath<Message> expansionPath);

    @Override
    MessageByIdGet withExpansionPaths(final List<ExpansionPath<Message>> expansionPaths);
}
