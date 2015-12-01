package io.sphere.sdk.queries;

import io.sphere.sdk.models.SphereEnumeration;
import io.sphere.sdk.utils.IterableUtils;

import javax.annotation.Nullable;

import java.util.List;

import static io.sphere.sdk.queries.StringQuerySortingModel.normalize;
import static java.util.stream.Collectors.toList;

final class SphereEnumerationQueryModelImpl<T, E extends SphereEnumeration> extends QueryModelImpl<T> implements SphereEnumerationQueryModel<T,E> {
    public SphereEnumerationQueryModelImpl(@Nullable final QueryModel<T> parent, @Nullable final String pathSegment) {
        super(parent, pathSegment);
    }

    @Override
    public QueryPredicate<T> is(final E enumValue) {
        return isPredicate(enumValue.toSphereName());
    }

    @Override
    public QueryPredicate<T> isIn(final Iterable<E> args) {
        return isInPredicate(toSphereNameList(args));
    }

    @Override
    public QueryPredicate<T> isNotIn(final Iterable<E> args) {
        return isNotInPredicate(toSphereNameList(args));
    }

    @Override
    public QueryPredicate<T> isNot(final E element) {
        return isNotPredicate(element.toSphereName());
    }

    private List<String> toSphereNameList(final Iterable<E> args) {
        return IterableUtils.toStream(args)
                .map(e -> normalize(e.toSphereName()))
                .collect(toList());
    }
}
