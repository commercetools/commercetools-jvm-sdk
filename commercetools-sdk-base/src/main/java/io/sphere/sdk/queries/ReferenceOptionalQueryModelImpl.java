package io.sphere.sdk.queries;

import io.sphere.sdk.models.Referenceable;

import javax.annotation.Nullable;
import java.util.List;

final class ReferenceOptionalQueryModelImpl<T, R> extends ReferenceQueryModelImpl<T, R> implements ReferenceOptionalQueryModel<T,R> {
    public ReferenceOptionalQueryModelImpl(@Nullable final QueryModel<T> parent, @Nullable final String pathSegment) {
        super(parent, pathSegment);
    }

    @Override
    public QueryPredicate<T> is(final Referenceable<R> reference) {
        return super.is(reference);
    }

    @Override
    public QueryPredicate<T> isIn(final List<? extends Referenceable<R>> elements) {
        return super.isIn(elements);
    }

    @Override
    public QueryPredicate<T> isPresent() {
        return new OptionalQueryPredicate<>(this, true);
    }

    @Override
    public QueryPredicate<T> isNotPresent() {
        return new OptionalQueryPredicate<>(this, false);
    }
}
