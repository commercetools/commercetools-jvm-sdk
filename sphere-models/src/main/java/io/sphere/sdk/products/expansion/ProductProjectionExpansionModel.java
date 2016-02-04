package io.sphere.sdk.products.expansion;

import io.sphere.sdk.categories.expansion.CategoryExpansionModel;
import io.sphere.sdk.products.ProductProjection;
import io.sphere.sdk.producttypes.expansion.ProductTypeExpansionModel;
import io.sphere.sdk.taxcategories.expansion.TaxCategoryExpansionModel;

public interface ProductProjectionExpansionModel<T> {
    ProductTypeExpansionModel<T> productType();

    TaxCategoryExpansionModel<T> taxCategory();

    CategoryExpansionModel<ProductProjection> categories(int index);

    CategoryExpansionModel<ProductProjection> categories();

    ProductVariantExpansionModel<T> masterVariant();

    ProductVariantExpansionModel<T> variants();

    ProductVariantExpansionModel<ProductProjection> allVariants();

    static ProductProjectionExpansionModel<ProductProjection> of() {
        return new ProductProjectionExpansionModelImpl<>();
    }
}
