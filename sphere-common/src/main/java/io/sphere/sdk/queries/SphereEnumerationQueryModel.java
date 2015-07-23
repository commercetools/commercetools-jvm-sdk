package io.sphere.sdk.queries;

import io.sphere.sdk.models.SphereEnumeration;

public class SphereEnumerationQueryModel<T, E extends SphereEnumeration> extends QueryModelImpl<T> implements EqualityQueryModel<T, E> {
    public SphereEnumerationQueryModel(final QueryModel<T> parent, final String pathSegment) {
        super(parent, pathSegment);
    }

    @Override
    public QueryPredicate<T> is(final E enumValue) {
        return isPredicate(enumValue.toSphereName());
    }
}
