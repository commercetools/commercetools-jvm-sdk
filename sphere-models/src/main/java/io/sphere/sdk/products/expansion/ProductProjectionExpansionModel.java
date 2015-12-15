package io.sphere.sdk.products.expansion;

import io.sphere.sdk.categories.expansion.CategoryExpansionModel;
import io.sphere.sdk.expansion.ExpansionModel;
import io.sphere.sdk.expansion.ExpansionPathsHolder;
import io.sphere.sdk.products.ProductProjection;

import static java.util.Arrays.asList;

public final class ProductProjectionExpansionModel<T> extends ExpansionModel<T> {

    private static final String VARIANTS = "variants[*]";
    private static final String MASTER_VARIANT = "masterVariant";

    private ProductProjectionExpansionModel() {
    }

    public ExpansionPathsHolder<T> productType() {
        return expansionPath("productType");
    }

    public ExpansionPathsHolder<T> taxCategory() {
        return expansionPath("taxCategory");
    }

    public CategoryExpansionModel<ProductProjection> categories(final int index) {
        return new CategoryExpansionModel<>(pathExpression(), "categories[" + index + "]");
    }

    public CategoryExpansionModel<ProductProjection> categories() {
        return new CategoryExpansionModel<>(pathExpression(), "categories[*]");
    }

    public ProductVariantExpansionModel<T> masterVariant() {
        return new ProductVariantExpansionModel<>(pathExpression(), MASTER_VARIANT);
    }

    public ProductVariantExpansionModel<T> variants() {
        return new ProductVariantExpansionModel<>(pathExpression(), VARIANTS);
    }

    public ProductVariantExpansionModel<ProductProjection> allVariants() {
        return new ProductVariantExpansionModel<>(pathExpression(), asList(MASTER_VARIANT, VARIANTS));
    }

    public static ProductProjectionExpansionModel<ProductProjection> of() {
        return new ProductProjectionExpansionModel<>();
    }
}
