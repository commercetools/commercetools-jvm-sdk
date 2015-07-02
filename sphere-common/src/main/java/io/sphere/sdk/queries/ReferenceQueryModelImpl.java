package io.sphere.sdk.queries;

import java.util.List;
import java.util.Optional;
import io.sphere.sdk.models.Referenceable;

import static java.util.stream.Collectors.toList;

public class ReferenceQueryModelImpl<T, R> extends QueryModelImpl<T> implements ReferenceQueryModel<T,R> {
    public ReferenceQueryModelImpl(Optional<? extends QueryModel<T>> parent, String pathSegment) {
        super(parent, pathSegment);
    }

    @Override
    public QueryPredicate<T> is(final Referenceable<R> reference) {
        final String id = reference.toReference().getId();
        return ComparisonQueryPredicate.ofIsEqualTo(idSegment(), id);
    }

    @Override
    public QueryPredicate<T> isInIds(final List<String> ids) {
        return new IsInQueryPredicate<>(idSegment(), ids);
    }

    @Override
    public QueryPredicate<T> isIn(final List<? extends Referenceable<R>> elements) {
        final List<String> ids = elements.stream().map(elem -> elem.toReference().getId()).collect(toList());
        return isInIds(ids);
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
