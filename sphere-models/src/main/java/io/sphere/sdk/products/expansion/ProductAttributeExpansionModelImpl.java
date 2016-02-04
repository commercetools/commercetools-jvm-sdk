package io.sphere.sdk.products.expansion;

import io.sphere.sdk.expansion.ExpansionModelImpl;
import io.sphere.sdk.expansion.ExpansionPath;
import io.sphere.sdk.expansion.ExpansionPathContainer;
import io.sphere.sdk.models.LocalizedString;

import javax.annotation.Nullable;
import java.util.List;

final class ProductAttributeExpansionModelImpl<T> extends ExpansionModelImpl<T> implements ProductAttributeExpansionModel<T> {
    ProductAttributeExpansionModelImpl(final List<String> parentPath, @Nullable final String path) {
        super(parentPath, path);
    }

    /**
     * {@link ExpansionPath} for flat attribute values like {@link String} and {@link LocalizedString}.
     * @return expansion path
     */
    @Override
    public ExpansionPathContainer<T> value() {
        return expansionPath("value");
    }

    /**
     * {@link ExpansionPath} for set (collection) attribute values.
     * @return expansion path
     */
    @Override
    public ExpansionPathContainer<T> valueSet() {
        return expansionPath("value[*]");
    }
}
