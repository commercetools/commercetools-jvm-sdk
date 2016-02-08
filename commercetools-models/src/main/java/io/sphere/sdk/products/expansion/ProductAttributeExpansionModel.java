package io.sphere.sdk.products.expansion;

import io.sphere.sdk.expansion.ExpansionPath;
import io.sphere.sdk.expansion.ExpansionPathContainer;
import io.sphere.sdk.models.LocalizedString;

public interface ProductAttributeExpansionModel<T> {
    /**
     * {@link ExpansionPath} for flat attribute values like {@link String} and {@link LocalizedString}.
     * @return expansion path
     */
    ExpansionPathContainer<T> value();

    /**
     * {@link ExpansionPath} for set (collection) attribute values.
     * @return expansion path
     */
    ExpansionPathContainer<T> valueSet();
}
