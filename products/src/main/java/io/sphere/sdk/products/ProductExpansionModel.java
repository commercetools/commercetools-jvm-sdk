package io.sphere.sdk.products;

import io.sphere.sdk.queries.ExpansionModel;
import io.sphere.sdk.queries.ExpansionPath;

public class ProductExpansionModel extends ExpansionModel {
    public ProductExpansionModel() {
    }

    public ExpansionPath<Product> productType() {
        return new ProductExpansionPath(path, "productType");
    }
}
