package io.sphere.sdk.products;

import io.sphere.sdk.queries.ExpansionModel;
import io.sphere.sdk.queries.ExpansionPath;

public class ProductExpansionModel extends ExpansionModel {
    public ProductExpansionModel() {
    }

    public ExpansionPath<Product> productType() {
        return newSubPath("productType");
    }

    public ExpansionPath<Product> taxCategory() {
        return newSubPath("taxCategory");
    }


    private ProductExpansionPath newSubPath(final String s) {
        return new ProductExpansionPath(path, s);
    }
}
