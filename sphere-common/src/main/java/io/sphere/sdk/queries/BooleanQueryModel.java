package io.sphere.sdk.queries;

import javax.annotation.Nullable;
import java.util.Objects;

public class BooleanQueryModel<T> extends QueryModelImpl<T> implements EqualityQueryModel<T, Boolean> {

    public BooleanQueryModel(@Nullable final QueryModel<T> parent, @Nullable final String pathSegment) {
        super(parent, pathSegment);
    }

    @Override
    public QueryPredicate<T> is(final Boolean value) {
        return isPredicate(Objects.requireNonNull(value));
    }
}
