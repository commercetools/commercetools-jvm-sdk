package io.sphere.sdk.products;

import com.google.common.base.Optional;
import io.sphere.sdk.producttypes.ProductType;
import io.sphere.sdk.queries.EmbeddedQueryModel;
import io.sphere.sdk.queries.QueryModel;
import io.sphere.sdk.queries.ReferenceQueryModel;

public class ProductQueryModel<T> extends EmbeddedQueryModel<T, ProductQueryModel<Product>> {

    private static final ProductQueryModel<ProductQueryModel<Product>> instance = new ProductQueryModel<>(Optional.<QueryModel<ProductQueryModel<Product>>>absent(), Optional.<String>absent());

    public static ProductQueryModel<ProductQueryModel<Product>> get() {
        return instance;
    }

    private ProductQueryModel(final Optional<? extends QueryModel<T>> parent, final Optional<String> pathSegment) {
        super(parent, pathSegment);
    }

    public ProductCatalogDataQueryModel<T> masterData() {
        return new ProductCatalogDataQueryModel<T>(Optional.of(this), Optional.of("masterData"));
    }

    public ReferenceQueryModel<T, ProductType> productType() {
        return new ReferenceQueryModel<>(Optional.of(this), "productType");
    }
}