package io.sphere.sdk.products.queries;

import io.sphere.sdk.products.ProductProjection;
import io.sphere.sdk.queries.ExpansionModel;
import io.sphere.sdk.queries.ExpansionPath;

public class ProductProjectionExpansionModel extends ExpansionModel {
    ProductProjectionExpansionModel() {
    }

    public ExpansionPath<ProductProjection> productType() {
        return newSubPath("productType");
    }

    public ExpansionPath<ProductProjection> taxCategory() {
        return newSubPath("taxCategory");
    }

    public ExpansionPath<ProductProjection> categories() {
        return newSubPath("categories[*]");
    }

    private ProductProjectionExpansionPath newSubPath(final String s) {
        return new ProductProjectionExpansionPath(path, s);
    }
}
