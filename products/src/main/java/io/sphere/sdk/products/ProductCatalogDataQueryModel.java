package io.sphere.sdk.products;

import java.util.Optional;
import io.sphere.sdk.queries.EmbeddedQueryModel;
import io.sphere.sdk.queries.QueryModel;

import static io.sphere.sdk.products.ProductProjectionType.CURRENT;

public final class ProductCatalogDataQueryModel<T> extends EmbeddedQueryModel<T, ProductQueryModel<Product>> {

    private static final ProductCatalogDataQueryModel<ProductCatalogDataQueryModel<Product>> instance =
            new ProductCatalogDataQueryModel<>(Optional.empty(), Optional.<String>empty());


    //TODO is this kind of method really required?
    public static ProductCatalogDataQueryModel<ProductCatalogDataQueryModel<Product>> get() {
        return instance;
    }

    ProductCatalogDataQueryModel(Optional<? extends QueryModel<T>> parent, Optional<String> pathSegment) {
        super(parent, pathSegment);
    }

    public ProductDataQueryModel<T> forProjection(final ProductProjectionType type) {
        return type == CURRENT ? current() : staged();
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