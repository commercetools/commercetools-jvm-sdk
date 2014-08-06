package io.sphere.sdk.products;

import java.util.Optional;
import io.sphere.sdk.queries.EmbeddedQueryModel;
import io.sphere.sdk.queries.QueryModel;

import static io.sphere.sdk.products.ProductProjectionType.CURRENT;

public final class ProductCatalogDataQueryModel extends EmbeddedQueryModel<Product> {

    private static final ProductCatalogDataQueryModel instance =
            new ProductCatalogDataQueryModel(Optional.empty(), Optional.<String>empty());

    //TODO is this kind of method really required?
    public static ProductCatalogDataQueryModel get() {
        return instance;
    }

    ProductCatalogDataQueryModel(Optional<? extends QueryModel<Product>> parent, Optional<String> pathSegment) {
        super(parent, pathSegment);
    }

    public ProductDataQueryModel forProjection(final ProductProjectionType type) {
        return type == CURRENT ? current() : staged();
    }

    public ProductDataQueryModel current() {
        return newProductDataQueryModel("current");
    }

    public ProductDataQueryModel staged() {
        return newProductDataQueryModel("staged");
    }

    private ProductDataQueryModel newProductDataQueryModel(String pathSegment) {
        return new ProductDataQueryModel(Optional.of(this), Optional.of(pathSegment));
    }

}