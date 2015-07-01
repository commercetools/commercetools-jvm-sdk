package io.sphere.sdk.messages;

import com.fasterxml.jackson.core.type.TypeReference;
import io.sphere.sdk.messages.queries.MessageQueryModel;
import io.sphere.sdk.queries.PagedQueryResult;
import io.sphere.sdk.queries.QueryPredicate;

import java.util.Optional;
import java.util.function.Function;

public class MessageDerivatHint<T> {
    private final TypeReference<PagedQueryResult<T>> queryResultTypeReference;
    private final TypeReference<T> elementTypeReference;
    private final Function<Optional<QueryPredicate<Message>>, QueryPredicate<Message>> filterPredicateFunction;

    private MessageDerivatHint(final TypeReference<PagedQueryResult<T>> resultTypeReference, final TypeReference<T> elementTypeReference, final Function<Optional<QueryPredicate<Message>>, QueryPredicate<Message>> filterPredicateFunction) {
        this.queryResultTypeReference = resultTypeReference;
        this.filterPredicateFunction = filterPredicateFunction;
        this.elementTypeReference = elementTypeReference;
    }

    public TypeReference<PagedQueryResult<T>> queryResultTypeReference() {
        return queryResultTypeReference;
    }

    public QueryPredicate<Message> filterPredicate(final Optional<QueryPredicate<Message>> predicateOption) {
        return filterPredicateFunction.apply(predicateOption);
    }

    public TypeReference<T> elementTypeReference() {
        return elementTypeReference;
    }

    public static <T> MessageDerivatHint<T> ofSingleMessageType(final String type, final TypeReference<PagedQueryResult<T>> queryResultTypeReference, final TypeReference<T> elementTypeReference) {
        return new MessageDerivatHint<>(queryResultTypeReference, elementTypeReference, predicateOption -> {
            final QueryPredicate<Message> additionalPredicate = MessageQueryModel.of().type().is(type);
            return predicateOption.map(p -> p.and(additionalPredicate)).orElse(additionalPredicate);
        });
    }

    public static <T> MessageDerivatHint<T> ofResourceType(final String resourceId, final TypeReference<PagedQueryResult<T>> queryResultTypeReference, final TypeReference<T> elementTypeReference) {
        return new MessageDerivatHint<>(queryResultTypeReference, elementTypeReference, predicateOption -> {
            final QueryPredicate<Message> additionalPredicate = MessageQueryModel.of().resource().typeId().is(resourceId);
            return predicateOption.map(p -> p.and(additionalPredicate)).orElse(additionalPredicate);
        });
    }
}
