package io.sphere.sdk.producttypes.queries;

import java.util.Optional;

import io.sphere.sdk.producttypes.ProductType;
import io.sphere.sdk.queries.*;

public final class AttributeTypeQueryModel<T> extends QueryModelImpl<T> {
    private static final AttributeTypeQueryModel<ProductType> instance =
            new AttributeTypeQueryModel<>(Optional.empty(), Optional.<String>empty());

    static AttributeTypeQueryModel<ProductType> get() {
        return instance;
    }

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
