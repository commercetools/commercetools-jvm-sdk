package io.sphere.sdk.products.expansion;

import io.sphere.sdk.expansion.ExpansionModel;
import io.sphere.sdk.expansion.ExpansionPath;
import io.sphere.sdk.expansion.ExpansionPathsHolder;
import io.sphere.sdk.models.LocalizedString;

import javax.annotation.Nullable;

public final class ProductAttributeExpansionModel<T> extends ExpansionModel<T> {
    ProductAttributeExpansionModel(@Nullable final String parentPath, @Nullable final String path) {
        super(parentPath, path);
    }

    /**
     * {@link ExpansionPath} for flat attribute values like {@link String} and {@link LocalizedString}.
     * @return expansion path
     */
    public ExpansionPathsHolder<T> value() {
        return expansionPath("value");
    }

    /**
     * {@link ExpansionPath} for set (collection) attribute values.
     * @return expansion path
     */
    public ExpansionPathsHolder<T> valueSet() {
        return expansionPath("value[*]");
    }
}
