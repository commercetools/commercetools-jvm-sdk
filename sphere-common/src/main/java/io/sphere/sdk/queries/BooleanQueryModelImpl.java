package io.sphere.sdk.queries;

import javax.annotation.Nullable;
import java.util.Objects;

final class BooleanQueryModelImpl<T> extends QueryModelImpl<T> implements BooleanQueryModel<T> {

    public BooleanQueryModelImpl(@Nullable final QueryModel<T> parent, @Nullable final String pathSegment) {
        super(parent, pathSegment);
    }

    @Override
    public QueryPredicate<T> is(final Boolean value) {
        return isPredicate(Objects.requireNonNull(value));
    }
}
