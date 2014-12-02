package io.sphere.sdk.products.queries;

import java.util.Optional;

import io.sphere.sdk.products.ProductProjectionType;
import io.sphere.sdk.queries.*;

import static io.sphere.sdk.products.ProductProjectionType.CURRENT;

public class ProductCatalogDataQueryModel<M> extends QueryModelImpl<M> {

    //TODO is this kind of method really required?
    public static PartialProductCatalogDataQueryModel get() {
        return new PartialProductCatalogDataQueryModel(Optional.empty(), Optional.<String>empty());
    }

    ProductCatalogDataQueryModel(Optional<? extends QueryModel<M>> parent, Optional<String> pathSegment) {
        super(parent, pathSegment);
    }

    public ProductDataQueryModel<M> forProjection(final ProductProjectionType type) {
        return type == CURRENT ? current() : staged();
    }

    public ProductDataQueryModel<M> current() {
        return newProductDataQueryModel("current");
    }

    public ProductDataQueryModel<M> staged() {
        return newProductDataQueryModel("staged");
    }

    private ProductDataQueryModel<M> newProductDataQueryModel(String pathSegment) {
        return new ProductDataQueryModel<>(Optional.<ProductCatalogDataQueryModel<M>>of(this), Optional.of(pathSegment));
    }

    public Predicate<M> where(final Predicate<PartialProductCatalogDataQueryModel> embeddedPredicate) {
        return new EmbeddedPredicate<>(this, embeddedPredicate);
    }
}