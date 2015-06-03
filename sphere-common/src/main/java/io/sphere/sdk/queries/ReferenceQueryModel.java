package io.sphere.sdk.queries;

import java.util.List;
import java.util.Optional;
import io.sphere.sdk.models.Referenceable;

import static java.util.stream.Collectors.toList;

public class ReferenceQueryModel<T, R> extends QueryModelImpl<T> implements EqualityQueryModel<T, Referenceable<R>> {
    public ReferenceQueryModel(Optional<? extends QueryModel<T>> parent, String pathSegment) {
        super(parent, pathSegment);
    }

    @Override
    public QueryPredicate<T> is(final Referenceable<R> reference) {
        return EqQueryPredicate.of(idSegment(), reference.toReference().getId());
    }

    public QueryPredicate<T> isIn(final List<? extends Referenceable<R>> elements) {
        final List<String> ids = elements.stream().map(elem -> elem.toReference().getId()).collect(toList());
        return new IsInQueryPredicate<>(idSegment(), ids);
    }

    private QueryModelImpl<T> idSegment() {
        return new QueryModelImpl<>(Optional.of(this), "id");
    }
}
