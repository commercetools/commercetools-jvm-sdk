package io.sphere.sdk.products.attributes;

import javax.annotation.Nullable;

/**
 * Data for a key value pair of translated attribute label and value.
 */
public interface AttributeTableRow {
    /**
     * Returns the translated and formatted value of the attribute.
     *
     * @return value
     */
    @Nullable
    String getTranslatedValue();

    /**
     * Returns the translated and formatted value of the label of the attribute.
     *
     * @return label
     */
    @Nullable
    String getTranslatedLabel();
}
