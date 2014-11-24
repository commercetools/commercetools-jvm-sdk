package io.sphere.sdk.queries;

import java.util.Optional;

public class DefaultModelQueryModelImpl<T> extends QueryModelImpl<T> {
    protected DefaultModelQueryModelImpl(final Optional<? extends QueryModel<T>> parent, final Optional<String> pathSegment) {
        super(parent, pathSegment);
    }

    public StringQuerySortingModel<T> id() {
        return new StringQuerySortingModel<>(Optional.of(this), "id");
    }

    public TimestampSortingModel<T> createdAt() {
        return new TimestampSortingModel<>(Optional.of(this), "createdAt");
    }

    public TimestampSortingModel<T> lastModifiedAt() {
        return new TimestampSortingModel<>(Optional.of(this), "lastModifiedAt");
    }
}
