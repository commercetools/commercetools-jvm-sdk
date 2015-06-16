package io.sphere.sdk.products.expansion;

import io.sphere.sdk.queries.ExpansionModel;
import io.sphere.sdk.queries.ExpansionPath;

import java.util.Optional;

public final class ProductAttributeExpansionModel<T> extends ExpansionModel<T> {
    ProductAttributeExpansionModel(final Optional<String> parentPath, final String path) {
        super(parentPath, Optional.of(path));
    }

    /**
     * {@link ExpansionPath} for flat attribute values like {@link String} and {@link io.sphere.sdk.models.LocalizedStrings}.
     * @return expansion path
     */
    public ExpansionPath<T> value() {
        return pathWithRoots("value");
    }

    /**
     * {@link ExpansionPath} for set (collection) attribute values.
     * @return expansion path
     */
    public ExpansionPath<T> valueSet() {
        return pathWithRoots("value[*]");
    }
}
