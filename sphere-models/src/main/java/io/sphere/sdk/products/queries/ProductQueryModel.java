package io.sphere.sdk.products.queries;

import java.util.Optional;

import io.sphere.sdk.products.Product;
import io.sphere.sdk.producttypes.ProductType;
import io.sphere.sdk.queries.*;

public class ProductQueryModel<T> extends DefaultModelQueryModelImpl<T> {

    public static ProductQueryModel<Product> of() {
        return new ProductQueryModel<>(Optional.<QueryModel<Product>>empty(), Optional.<String>empty());
    }

    private ProductQueryModel(final Optional<? extends QueryModel<T>> parent, final Optional<String> pathSegment) {
        super(parent, pathSegment);
    }

    public ProductCatalogDataQueryModel<T> masterData() {
        return new ProductCatalogDataQueryModel<>(Optional.of(this), Optional.of("masterData"));
    }

    public ReferenceQueryModel<T, ProductType> productType() {
        return new ReferenceQueryModel<>(Optional.of(this), "productType");
    }
}