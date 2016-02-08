package io.sphere.sdk.products.expansion;

import io.sphere.sdk.products.Product;
import io.sphere.sdk.producttypes.expansion.ProductTypeExpansionModel;
import io.sphere.sdk.taxcategories.expansion.TaxCategoryExpansionModel;

public interface ProductExpansionModel<T> {
    ProductTypeExpansionModel<T> productType();

    TaxCategoryExpansionModel<T> taxCategory();

    ProductCatalogExpansionModel<T> masterData();


    static ProductExpansionModel<Product> of() {
        return new ProductExpansionModelImpl<>();
    }
}
