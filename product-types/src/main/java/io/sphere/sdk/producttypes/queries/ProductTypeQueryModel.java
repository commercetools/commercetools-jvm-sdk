package io.sphere.sdk.producttypes.queries;

import java.util.Optional;

import io.sphere.sdk.producttypes.ProductType;
import io.sphere.sdk.queries.EmbeddedQueryModel;
import io.sphere.sdk.queries.QueryModel;
import io.sphere.sdk.queries.StringQuerySortingModel;

public final class ProductTypeQueryModel extends EmbeddedQueryModel<ProductType> {
    private static final ProductTypeQueryModel instance = new ProductTypeQueryModel(Optional.<QueryModel<ProductType>>empty(), Optional.<String>empty());

    public static ProductTypeQueryModel get() {
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
