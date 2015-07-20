package io.sphere.sdk.producttypes.queries;

import io.sphere.sdk.queries.QueryModel;
import io.sphere.sdk.queries.QueryModelImpl;
import io.sphere.sdk.queries.StringQueryModel;

import java.util.Optional;

public final class AttributeDefinitionQueryModel<T> extends QueryModelImpl<T> {

    AttributeDefinitionQueryModel(Optional<? extends QueryModel<T>> parent, Optional<String> pathSegment) {
        super(parent, pathSegment);
    }

    public StringQueryModel<T> name() {
        return stringModel("name");
    }

    public AttributeTypeQueryModel<T> type() {
        return new AttributeTypeQueryModel<>(Optional.of(this), Optional.of("type"));
    }
}
