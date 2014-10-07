package io.sphere.sdk.products.queries;

import io.sphere.sdk.categories.Category;
import io.sphere.sdk.products.ProductProjection;
import io.sphere.sdk.producttypes.ProductType;
import io.sphere.sdk.queries.*;

import java.util.Optional;

public class ProductProjectionQueryModel extends ProductDataQueryModelBase<ProductProjection> {

    static ProductProjectionQueryModel get() {
        return new ProductProjectionQueryModel(Optional.<QueryModel<ProductProjection>>empty(), Optional.<String>empty());
    }

    private ProductProjectionQueryModel(final Optional<? extends QueryModel<ProductProjection>> parent, final Optional<String> pathSegment) {
        super(parent, pathSegment);
    }

    public ReferenceQueryModel<ProductProjection, ProductType> productType() {
        return new ReferenceQueryModel<>(Optional.of(this), "productType");
    }

    public StringQuerySortingModel<ProductProjection> id() {
        return new StringQuerySortingModel<>(Optional.of(this), "id");
    }

    public ReferenceListQueryModel<ProductProjection, Category> categories() {
        return new ReferenceListQueryModel<>(Optional.of(this), "categories");
    }

    public BooleanQueryModel<ProductProjection> hasStagedChanges() {
        return new BooleanQueryModel<>(Optional.of(this), "hasStagedChanges");
    }
}