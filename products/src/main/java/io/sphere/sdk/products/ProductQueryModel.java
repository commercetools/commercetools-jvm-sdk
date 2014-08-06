package io.sphere.sdk.products;

import java.util.Optional;
import io.sphere.sdk.producttypes.ProductType;
import io.sphere.sdk.queries.EmbeddedQueryModel;
import io.sphere.sdk.queries.QueryModel;
import io.sphere.sdk.queries.ReferenceQueryModel;

public class ProductQueryModel extends EmbeddedQueryModel<Product> {

    private static final ProductQueryModel instance = new ProductQueryModel(Optional.<QueryModel<Product>>empty(), Optional.<String>empty());

    public static ProductQueryModel get() {
        return instance;
    }

    private ProductQueryModel(final Optional<? extends QueryModel<Product>> parent, final Optional<String> pathSegment) {
        super(parent, pathSegment);
    }

    public ProductCatalogDataQueryModel masterData() {
        return new ProductCatalogDataQueryModel(Optional.of(this), Optional.of("masterData"));
    }

    public ReferenceQueryModel<Product, ProductType> productType() {
        return new ReferenceQueryModel<>(Optional.of(this), "productType");
    }
}