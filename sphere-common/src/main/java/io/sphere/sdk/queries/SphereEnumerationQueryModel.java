package io.sphere.sdk.queries;

import io.sphere.sdk.models.SphereEnumeration;

public interface SphereEnumerationQueryModel<T, E extends SphereEnumeration> extends EqualityQueryModel<T, E> {
    @Override
    QueryPredicate<T> is(E enumValue);
}
