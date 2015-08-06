package io.sphere.sdk.products.expansion;

import io.sphere.sdk.categories.expansion.CategoryExpansionModel;
import io.sphere.sdk.products.ProductProjection;
import io.sphere.sdk.expansion.ExpansionModel;
import io.sphere.sdk.expansion.ExpansionPath;

public final class ProductProjectionExpansionModel<T> extends ExpansionModel<T> {

    private ProductProjectionExpansionModel() {
    }

    public ExpansionPath<T> productType() {
        return expansionPath("productType");
    }

    public ExpansionPath<T> taxCategory() {
        return expansionPath("taxCategory");
    }

    public CategoryExpansionModel<ProductProjection> categories(final int index) {
        return new CategoryExpansionModel<>(pathExpression(), "categories[" + index + "]");
    }

    public CategoryExpansionModel<ProductProjection> categories() {
        return new CategoryExpansionModel<>(pathExpression(), "categories[*]");
    }

    public ProductVariantExpansionModel<T> masterVariant() {
        return new ProductVariantExpansionModel<>(pathExpression(), "masterVariant");
    }

    public ProductVariantExpansionModel<T> variants() {
        return new ProductVariantExpansionModel<>(pathExpression(), "variants[*]");
    }

    public static ProductProjectionExpansionModel<ProductProjection> of() {
        return new ProductProjectionExpansionModel<>();
    }
}
