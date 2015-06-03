package io.sphere.sdk.producttypes.queries;

import java.util.Optional;

import io.sphere.sdk.producttypes.ProductType;
import io.sphere.sdk.queries.*;

public final class AttributeDefinitionQueryModel extends QueryModelImpl<ProductType> {

    private static final AttributeDefinitionQueryModel instance =
            new AttributeDefinitionQueryModel(Optional.empty(), Optional.<String>empty());

    static AttributeDefinitionQueryModel get() {
        return instance;
    }

    AttributeDefinitionQueryModel(Optional<? extends QueryModel<ProductType>> parent, Optional<String> pathSegment) {
        super(parent, pathSegment);
    }

    public StringQueryModel<ProductType> name() {
        return stringModel("name");
    }

    public AttributeTypeQueryModel<ProductType> type() {
        return new AttributeTypeQueryModel<>(Optional.of(this), Optional.of("type"));
    }
}
