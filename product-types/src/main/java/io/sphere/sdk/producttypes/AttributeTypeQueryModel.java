package io.sphere.sdk.producttypes;

import java.util.Optional;

import io.sphere.sdk.queries.*;

public final class AttributeTypeQueryModel<T> extends EmbeddedQueryModel<ProductType> {
    private static final AttributeTypeQueryModel<ProductType> instance =
            new AttributeTypeQueryModel<>(Optional.empty(), Optional.<String>empty());

    public static AttributeTypeQueryModel<ProductType> get() {
        return instance;
    }

    AttributeTypeQueryModel(Optional<? extends QueryModel<ProductType>> parent, Optional<String> pathSegment) {
        super(parent, pathSegment);
    }

    public StringQueryModel<ProductType> name() {
        return new StringQuerySortingModel<>(Optional.of(this), "name");
    }

    public AttributeTypeQueryModel<ProductType> type() {
        return new AttributeTypeQueryModel<ProductType>(Optional.of(this), Optional.of("elementType"));
    }
}
