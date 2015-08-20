package io.sphere.sdk.messages;

import com.fasterxml.jackson.core.type.TypeReference;
import io.sphere.sdk.messages.queries.MessageQuery;
import io.sphere.sdk.messages.queries.MessageQueryModel;
import io.sphere.sdk.queries.PagedQueryResult;
import io.sphere.sdk.queries.QueryPredicate;

import java.util.function.Supplier;

/**
 * TypeReference container used by query to get only messages of a certain type.
 * @param <T> the type of the message the type hint is about
 * @see MessageQuery#forMessageType(io.sphere.sdk.messages.MessageDerivateHint)
 */
public class MessageDerivateHint<T> {
    private final TypeReference<PagedQueryResult<T>> queryResultTypeReference;
    private final TypeReference<T> elementTypeReference;
    private final Supplier<QueryPredicate<Message>> predicateSupplier;

    private MessageDerivateHint(final TypeReference<PagedQueryResult<T>> resultTypeReference,
                                final TypeReference<T> elementTypeReference,
                                final Supplier<QueryPredicate<Message>> predicateSupplier) {
        this.queryResultTypeReference = resultTypeReference;
        this.predicateSupplier = predicateSupplier;
        this.elementTypeReference = elementTypeReference;
    }

    public TypeReference<PagedQueryResult<T>> queryResultTypeReference() {
        return queryResultTypeReference;
    }

    public TypeReference<T> elementTypeReference() {
        return elementTypeReference;
    }

    public QueryPredicate<Message> predicate() {
        return predicateSupplier.get();
    }

    public static <T> MessageDerivateHint<T> ofSingleMessageType(final String type, final TypeReference<PagedQueryResult<T>> queryResultTypeReference, final TypeReference<T> elementTypeReference) {
        return new MessageDerivateHint<>(queryResultTypeReference, elementTypeReference, () -> MessageQueryModel.of().type().is(type));
    }

    public static <T> MessageDerivateHint<T> ofResourceType(final String resourceId, final TypeReference<PagedQueryResult<T>> queryResultTypeReference, final TypeReference<T> elementTypeReference) {
        return new MessageDerivateHint<>(queryResultTypeReference, elementTypeReference, () -> MessageQueryModel.of().resource().typeId().is(resourceId));
    }
}
