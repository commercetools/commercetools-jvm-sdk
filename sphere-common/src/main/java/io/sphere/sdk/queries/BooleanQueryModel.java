package io.sphere.sdk.queries;

public interface BooleanQueryModel<T> extends EqualityQueryModel<T, Boolean> {
    @Override
    QueryPredicate<T> is(Boolean value);
}
