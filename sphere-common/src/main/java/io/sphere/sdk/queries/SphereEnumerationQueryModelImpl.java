package io.sphere.sdk.queries;

import io.sphere.sdk.models.SphereEnumeration;

import javax.annotation.Nullable;

final class SphereEnumerationQueryModelImpl<T, E extends SphereEnumeration> extends QueryModelImpl<T> implements SphereEnumerationQueryModel<T,E> {
    public SphereEnumerationQueryModelImpl(@Nullable final QueryModel<T> parent, @Nullable final String pathSegment) {
        super(parent, pathSegment);
    }

    @Override
    public QueryPredicate<T> is(final E enumValue) {
        return isPredicate(enumValue.toSphereName());
    }
}
