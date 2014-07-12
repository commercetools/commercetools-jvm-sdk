package io.sphere.sdk.products;

import com.google.common.base.Optional;
import io.sphere.sdk.queries.EmbeddedQueryModel;
import io.sphere.sdk.queries.QueryModel;

public final class ProductCatalogDataQueryModel<T> extends EmbeddedQueryModel<T, ProductQueryModel<Product>> {

    private static final ProductCatalogDataQueryModel<ProductCatalogDataQueryModel<Product>> instance =
            new ProductCatalogDataQueryModel<>(Optional.absent(), Optional.<String>absent());


    //TODO is this kind of method really required?
    public static ProductCatalogDataQueryModel<ProductCatalogDataQueryModel<Product>> get() {
        return instance;
    }

    ProductCatalogDataQueryModel(Optional<? extends QueryModel<T>> parent, Optional<String> pathSegment) {
        super(parent, pathSegment);
    }

    public ProductDataQueryModel<T> current() {
        return newProductDataQueryModel("current");
    }

    public ProductDataQueryModel<T> staged() {
        return newProductDataQueryModel("staged");
    }

    private ProductDataQueryModel<T> newProductDataQueryModel(String pathSegment) {
        return new ProductDataQueryModel<T>(Optional.of(this), Optional.of(pathSegment));
    }

}