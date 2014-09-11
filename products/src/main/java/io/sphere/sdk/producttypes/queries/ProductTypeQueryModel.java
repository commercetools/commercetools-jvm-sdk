package io.sphere.sdk.producttypes.queries;

import java.util.Optional;

import io.sphere.sdk.producttypes.ProductType;
import io.sphere.sdk.queries.QueryModel;
import io.sphere.sdk.queries.QueryModelImpl;
import io.sphere.sdk.queries.StringQuerySortingModel;

public final class ProductTypeQueryModel extends QueryModelImpl<ProductType> {
    private static final ProductTypeQueryModel instance = new ProductTypeQueryModel(Optional.<QueryModel<ProductType>>empty(), Optional.<String>empty());

    static ProductTypeQueryModel get() {
        return instance;
    }

    private ProductTypeQueryModel(Optional<? extends QueryModel<ProductType>> parent, Optional<String> pathSegment) {
        super(parent, pathSegment);
    }

    public StringQuerySortingModel<ProductType> name() {
        return new StringQuerySortingModel<>(Optional.of(this), "name");
    }

    public AttributeDefinitionQueryModel attributes() {
        return new AttributeDefinitionQueryModel(Optional.of(this), Optional.of("attributes"));
    }
}
