package io.sphere.sdk.products.attributes;

import java.util.List;

/**
 * Data to display the attributes of a product in a table.
 *
 */
public interface AttributeTable {
    /**
     * The rows of the table.
     *
     * @return rows
     */
    List<AttributeTableRow> getRows();

    /**
     * Iterates through the rows and finds the longest length of the translated value length.
     *
     * @return the maximal translated attribute value length or 0 in case of 0 rows
     */
    int getMaxTranslatedValueLength();

    /**
     * Iterates through the rows and finds the longest length of the translated label length.
     *
     * @return the maximal translated attribute label length or 0 in case of 0 rows
     */
    int getMaxTranslatedLabelLength();
}
