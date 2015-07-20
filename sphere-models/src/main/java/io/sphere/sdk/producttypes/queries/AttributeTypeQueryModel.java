package io.sphere.sdk.producttypes.queries;

import io.sphere.sdk.queries.QueryModel;
import io.sphere.sdk.queries.QueryModelImpl;
import io.sphere.sdk.queries.StringQueryModel;
import io.sphere.sdk.queries.StringQuerySortingModel;

import java.util.Optional;

public final class AttributeTypeQueryModel<T> extends QueryModelImpl<T> {
    AttributeTypeQueryModel(Optional<? extends QueryModel<T>> parent, Optional<String> pathSegment) {
        super(parent, pathSegment);
    }

    public StringQueryModel<T> name() {
        return new StringQuerySortingModel<>(Optional.of(this), "name");
    }

    public AttributeTypeQueryModel<T> type() {
        return new AttributeTypeQueryModel<>(Optional.of(this), Optional.of("elementType"));
    }
}
