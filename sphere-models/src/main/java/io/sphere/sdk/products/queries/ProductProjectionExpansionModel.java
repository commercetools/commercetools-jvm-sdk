package io.sphere.sdk.products.queries;

import io.sphere.sdk.categories.queries.CategoryExpansionModel;
import io.sphere.sdk.products.ProductProjection;
import io.sphere.sdk.queries.ExpansionModel;
import io.sphere.sdk.queries.ExpansionPath;

public class ProductProjectionExpansionModel<T> extends ExpansionModel<T> {

    public ProductProjectionExpansionModel() {
    }

    public ExpansionPath<T> productType() {
        return pathWithRoots("productType");
    }

    public ExpansionPath<T> taxCategory() {
        return pathWithRoots("taxCategory");
    }

    public CategoryExpansionModel<ProductProjection> categories(final int index) {
        return new CategoryExpansionModel<>(pathExpressionOption(), "categories[" + index + "]");
    }

    public CategoryExpansionModel<ProductProjection> categories() {
        return new CategoryExpansionModel<>(pathExpressionOption(), "categories[*]");
    }
}
