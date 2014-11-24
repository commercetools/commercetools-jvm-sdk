package io.sphere.sdk.products.queries;

import java.util.Optional;

import io.sphere.sdk.products.Product;
import io.sphere.sdk.producttypes.ProductType;
import io.sphere.sdk.queries.*;

public class ProductQueryModel extends DefaultModelQueryModelImpl<Product> {

    static ProductQueryModel get() {
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