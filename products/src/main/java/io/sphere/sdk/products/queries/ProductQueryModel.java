package io.sphere.sdk.products.queries;

import java.util.Optional;

import io.sphere.sdk.products.Product;
import io.sphere.sdk.producttypes.ProductType;
import io.sphere.sdk.queries.QueryModel;
import io.sphere.sdk.queries.QueryModelImpl;
import io.sphere.sdk.queries.ReferenceQueryModel;

public class ProductQueryModel extends QueryModelImpl<Product> {

    public static ProductQueryModel get() {
        return new ProductQueryModel(Optional.<QueryModel<Product>>empty(), Optional.<String>empty());
    }

    private ProductQueryModel(final Optional<? extends QueryModel<Product>> parent, final Optional<String> pathSegment) {
        super(parent, pathSegment);
    }

    public ProductCatalogDataQueryModel<Product> masterData() {
        return new ProductCatalogDataQueryModel<Product>(Optional.of(this), Optional.of("masterData"));
    }

    public ReferenceQueryModel<Product, ProductType> productType() {
        return new ReferenceQueryModel<>(Optional.of(this), "productType");
    }
}