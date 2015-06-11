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
        final String id = reference.toReference().getId();
        return ComparisonQueryPredicate.ofIsEqualTo(idSegment(), id);
    }

    public QueryPredicate<T> isInIds(final List<String> ids) {
        return new IsInQueryPredicate<>(idSegment(), ids);
    }

    public QueryPredicate<T> isIn(final List<? extends Referenceable<R>> elements) {
        final List<String> ids = elements.stream().map(elem -> elem.toReference().getId()).collect(toList());
        return isInIds(ids);
    }

    private QueryModelImpl<T> idSegment() {
        return new QueryModelImpl<>(Optional.of(this), "id");
    }
}
