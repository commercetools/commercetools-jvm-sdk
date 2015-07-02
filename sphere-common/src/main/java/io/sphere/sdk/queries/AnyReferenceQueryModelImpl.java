package io.sphere.sdk.queries;

import io.sphere.sdk.models.Referenceable;

import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

public class AnyReferenceQueryModelImpl<T> extends QueryModelImpl<T> implements AnyReferenceQueryModel<T> {
    public AnyReferenceQueryModelImpl(Optional<? extends QueryModel<T>> parent, String pathSegment) {
        super(parent, pathSegment);
    }

    @Override
    public <R> QueryPredicate<T> is(final Referenceable<R> reference) {
        final String id = reference.toReference().getId();
        return ComparisonQueryPredicate.ofIsEqualTo(idSegment(), id);
    }

    @Override
    public <R> QueryPredicate<T> isIn(final List<? extends Referenceable<R>> elements) {
        final List<String> ids = elements.stream().map(elem -> elem.toReference().getId()).collect(toList());
        return isInIds(ids);
    }

    @Override
    public QueryPredicate<T> isInIds(final List<String> ids) {
        return new IsInQueryPredicate<>(idSegment(), ids);
    }

    @Override
    public StringQueryModel<T> id() {
        return new StringQuerySortingModel<>(Optional.of(this), "id");
    }

    @Override
    public StringQueryModel<T> typeId() {
        return new StringQuerySortingModel<>(Optional.of(this), "typeId");
    }

    private QueryModelImpl<T> idSegment() {
        return new QueryModelImpl<>(Optional.of(this), "id");
    }
}
