package io.sphere.sdk.queries;

import java.util.List;
import java.util.Optional;
import io.sphere.sdk.models.Referenceable;

import static java.util.stream.Collectors.toList;

public class ReferenceQueryModel<T, R> extends QueryModelImpl<T> {
    public ReferenceQueryModel(Optional<? extends QueryModel<T>> parent, String pathSegment) {
        super(parent, pathSegment);
    }


    public Predicate<T> is(Referenceable<R> reference) {
        return new EqPredicate<>(idSegment(), reference.toReference().getId());
    }

    public Predicate<T> isAnyOf(final List<? extends Referenceable<R>> elements) {
        final List<String> ids = elements.stream().map(elem -> elem.toReference().getId()).collect(toList());
        return new IsInPredicate<>(idSegment(), ids);
    }

    private QueryModelImpl<T> idSegment() {
        return new QueryModelImpl<>(Optional.of(this), "id");
    }
}
