package io.sphere.sdk.queries;

import io.sphere.sdk.models.SphereEnumeration;

import java.util.Optional;

public class SphereEnumerationQueryModel<T, E extends SphereEnumeration> extends QueryModelImpl<T> implements EqualityQueryModel<T, E> {
    public SphereEnumerationQueryModel(final Optional<? extends QueryModel<T>> parent, final Optional<String> pathSegment) {
        super(parent, pathSegment);
    }

    @Override
    public QueryPredicate<T> is(final E enumValue) {
        return EqQueryPredicate.of(this, enumValue.toSphereName());
    }
}
