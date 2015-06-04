package io.sphere.sdk.products.expansion;

import io.sphere.sdk.categories.queries.CategoryExpansionModel;
import io.sphere.sdk.products.ProductProjection;
import io.sphere.sdk.queries.ExpansionModel;
import io.sphere.sdk.queries.ExpansionPath;

public final class ProductProjectionExpansionModel<T> extends ExpansionModel<T> {

    private ProductProjectionExpansionModel() {
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

    public ProductVariantExpansionModel<T> masterVariant() {
        return new ProductVariantExpansionModel<>(pathExpressionOption(), "masterVariant");
    }

    public ProductVariantExpansionModel<T> variants() {
        return new ProductVariantExpansionModel<>(pathExpressionOption(), "variants");
    }

    public static ProductProjectionExpansionModel<ProductProjection> of() {
        return new ProductProjectionExpansionModel<>();
    }
}
