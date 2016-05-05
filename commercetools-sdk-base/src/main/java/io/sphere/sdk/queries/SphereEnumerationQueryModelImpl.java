package io.sphere.sdk.queries;

import io.sphere.sdk.models.SphereEnumeration;

import javax.annotation.Nullable;
import java.util.List;

import static io.sphere.sdk.queries.StringQuerySortingModel.normalize;
import static io.sphere.sdk.utils.SphereInternalUtils.toStream;
import static java.util.stream.Collectors.toList;

final class SphereEnumerationQueryModelImpl<T, E extends SphereEnumeration> extends QueryModelImpl<T> implements SphereEnumerationOptionalQueryModel<T,E>, SphereEnumerationCollectionQueryModel<T, E> {
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

    @Override
    public QueryPredicate<T> isNotPresent() {
        return isNotPresentPredicate();
    }

    @Override
    public QueryPredicate<T> isPresent() {
        return isPresentPredicate();
    }

    private List<String> toSphereNameList(final Iterable<E> args) {
        return toStream(args)
                .map(e -> normalize(e.toSphereName()))
                .collect(toList());
    }

    @Override
    public QueryPredicate<T> isEmpty() {
        return isEmptyCollectionQueryPredicate();
    }

    @Override
    public QueryPredicate<T> isNotEmpty() {
        return isNotEmptyCollectionQueryPredicate();
    }

    @Override
    public QueryPredicate<T> containsAll(final Iterable<E> items) {
        final List<String> strings = transformSphereEnumeration(items);
        return new ContainsAllPredicate<>(this, strings);
    }

    @Override
    public QueryPredicate<T> containsAny(final Iterable<E> items) {
        final List<String> strings = transformSphereEnumeration(items);
        return new ContainsAnyPredicate<>(this, strings);
    }
}
