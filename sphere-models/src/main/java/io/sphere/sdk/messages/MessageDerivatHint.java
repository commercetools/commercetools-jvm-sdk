package io.sphere.sdk.messages;

import com.fasterxml.jackson.core.type.TypeReference;
import io.sphere.sdk.queries.PagedQueryResult;

public class MessageDerivatHint<T> {
    private final TypeReference<PagedQueryResult<T>> queryResultTypeReference;
    private final TypeReference<T> elementTypeReference;
    private final String type;

    private MessageDerivatHint(final String type, final TypeReference<PagedQueryResult<T>> resultTypeReference, final TypeReference<T> elementTypeReference) {
        this.queryResultTypeReference = resultTypeReference;
        this.type = type;
        this.elementTypeReference = elementTypeReference;
    }

    public TypeReference<PagedQueryResult<T>> queryResultTypeReference() {
        return queryResultTypeReference;
    }

    public String type() {
        return type;
    }

    public TypeReference<T> elementTypeReference() {
        return elementTypeReference;
    }

    public static <T> MessageDerivatHint<T> of(final String type, final TypeReference<PagedQueryResult<T>> queryResultTypeReference, final TypeReference<T> elementTypeReference) {
        return new MessageDerivatHint<>(type, queryResultTypeReference, elementTypeReference);
    }
}
