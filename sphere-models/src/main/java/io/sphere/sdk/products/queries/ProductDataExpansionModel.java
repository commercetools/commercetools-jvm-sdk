package io.sphere.sdk.products.queries;

import io.sphere.sdk.categories.queries.CategoryExpansionModel;
import io.sphere.sdk.queries.ExpansionModel;

import java.util.Optional;

public class ProductDataExpansionModel<T> extends ExpansionModel<T> {
    ProductDataExpansionModel(final Optional<String> parentPath, final String path) {
        super(parentPath, Optional.of(path));
    }

    public CategoryExpansionModel<T> categories(final int index) {
        return new CategoryExpansionModel<>(pathExpressionOption(), "categories[" + index + "]");
    }

    public CategoryExpansionModel<T> categories() {
        return new CategoryExpansionModel<>(pathExpressionOption(), "categories[*]");
    }

    public ProductVariantExpansionModel<T> masterVariant() {
        return new ProductVariantExpansionModel<>(pathExpressionOption(), "masterVariant");
    }

    public ProductVariantExpansionModel<T> variants() {
        return new ProductVariantExpansionModel<>(pathExpressionOption(), "variants");
    }
}

