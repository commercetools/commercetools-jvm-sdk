package io.sphere.sdk.producttypes.queries;

import java.util.Optional;

import io.sphere.sdk.producttypes.ProductType;
import io.sphere.sdk.queries.*;

public final class AttributeDefinitionQueryModel<T> extends QueryModelImpl<T> {

    static AttributeDefinitionQueryModel<ProductType> get() {
        return new AttributeDefinitionQueryModel<>(Optional.empty(), Optional.<String>empty());
    }

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
