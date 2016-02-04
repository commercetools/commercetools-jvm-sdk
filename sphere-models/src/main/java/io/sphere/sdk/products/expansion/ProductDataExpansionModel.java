package io.sphere.sdk.products.expansion;

import io.sphere.sdk.categories.expansion.CategoryExpansionModel;
import io.sphere.sdk.expansion.ExpansionModel;

import javax.annotation.Nullable;
import java.util.List;

public final class ProductDataExpansionModel<T> extends ExpansionModel<T> {
    ProductDataExpansionModel(final List<String> parentPath, @Nullable final String path) {
        super(parentPath, path);
    }

    public CategoryExpansionModel<T> categories(final Integer index) {
        return CategoryExpansionModel.of(pathExpression(), "categories[" + index + "]");
    }

    public CategoryExpansionModel<T> categories() {
        return CategoryExpansionModel.of(pathExpression(), "categories[*]");
    }

    public ProductVariantExpansionModel<T> masterVariant() {
        return new ProductVariantExpansionModelImpl<>(pathExpression(), "masterVariant");
    }

    public ProductVariantExpansionModel<T> variants() {
        return new ProductVariantExpansionModelImpl<>(pathExpression(), "variants[*]");
    }
}

