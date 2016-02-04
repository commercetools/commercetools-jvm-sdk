package io.sphere.sdk.products.expansion;

import io.sphere.sdk.categories.expansion.CategoryExpansionModel;
import io.sphere.sdk.expansion.ExpansionModel;

import javax.annotation.Nullable;
import java.util.List;

final class ProductDataExpansionModelImpl<T> extends ExpansionModel<T> implements ProductDataExpansionModel<T> {
    ProductDataExpansionModelImpl(final List<String> parentPath, @Nullable final String path) {
        super(parentPath, path);
    }

    @Override
    public CategoryExpansionModel<T> categories(final Integer index) {
        return CategoryExpansionModel.of(pathExpression(), "categories[" + index + "]");
    }

    @Override
    public CategoryExpansionModel<T> categories() {
        return CategoryExpansionModel.of(pathExpression(), "categories[*]");
    }

    @Override
    public ProductVariantExpansionModel<T> masterVariant() {
        return new ProductVariantExpansionModelImpl<>(pathExpression(), "masterVariant");
    }

    @Override
    public ProductVariantExpansionModel<T> variants() {
        return new ProductVariantExpansionModelImpl<>(pathExpression(), "variants[*]");
    }
}

