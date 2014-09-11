package io.sphere.sdk.queries;

import java.util.Optional;
import io.sphere.sdk.models.Referenceable;

public class ReferenceQueryModel<T, R> extends QueryModelImpl<T> {
    public ReferenceQueryModel(Optional<? extends QueryModel<T>> parent, String pathSegment) {
        super(parent, pathSegment);
    }


    public Predicate<T> is(Referenceable<R> reference) {
        final QueryModelImpl<T> idSegment = new QueryModelImpl<>(Optional.of(this), "id");
        return new EqPredicate<>(idSegment, reference.toReference().getId());
    }
}
