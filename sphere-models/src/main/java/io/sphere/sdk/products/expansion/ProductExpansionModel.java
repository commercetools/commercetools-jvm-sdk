package io.sphere.sdk.products.expansion;

import io.sphere.sdk.expansion.ExpansionModel;
import io.sphere.sdk.products.Product;
import io.sphere.sdk.producttypes.expansion.ProductTypeExpansionModel;
import io.sphere.sdk.taxcategories.expansion.TaxCategoryExpansionModel;

public final class ProductExpansionModel<T> extends ExpansionModel<T> {
    ProductExpansionModel() {
    }

    public ProductTypeExpansionModel<T> productType() {
        return ProductTypeExpansionModel.of(buildPathExpression(), "productType");
    }

    public TaxCategoryExpansionModel<T> taxCategory() {
        return TaxCategoryExpansionModel.of(buildPathExpression(), "taxCategory");
    }

    public ProductCatalogExpansionModel<T> masterData() {
        return new ProductCatalogExpansionModelImpl<>(null, "masterData");
    }

    public static ProductExpansionModel<Product> of() {
        return new ProductExpansionModel<>();
    }
}
