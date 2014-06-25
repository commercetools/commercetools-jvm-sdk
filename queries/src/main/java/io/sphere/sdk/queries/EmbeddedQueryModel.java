package io.sphere.sdk.queries;

import com.google.common.base.Optional;

public abstract class EmbeddedQueryModel<T, C> extends QueryModelImpl<T> {
    protected EmbeddedQueryModel(Optional<? extends QueryModel<T>> parent, Optional<String> pathSegment) {
        super(parent, pathSegment);
    }

    public Predicate<T> where(Predicate<C> embeddedPredicate) {
        return new EmbeddedPredicate<>(this, embeddedPredicate);
    }

    protected LocalizedStringQuerySortingModel<T> localizedStringQueryModel(final String pathSegment) {
        return new LocalizedStringQuerySortingModel<T>(Optional.of(this), Optional.of(pathSegment));
    }
}