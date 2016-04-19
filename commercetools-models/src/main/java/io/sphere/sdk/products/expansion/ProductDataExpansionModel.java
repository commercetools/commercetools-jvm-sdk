package io.sphere.sdk.products.expansion;

import io.sphere.sdk.categories.expansion.CategoryExpansionModel;

public interface ProductDataExpansionModel<T> {
    CategoryExpansionModel<T> categories(Integer index);

    CategoryExpansionModel<T> categories();

    ProductVariantExpansionModel<T> masterVariant();

    ProductVariantExpansionModel<T> variants();

    ProductVariantExpansionModel<T> allVariants();
}
