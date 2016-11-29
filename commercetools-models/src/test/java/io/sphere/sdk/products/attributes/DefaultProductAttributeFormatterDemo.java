package io.sphere.sdk.products.attributes;

import io.sphere.sdk.models.Reference;
import io.sphere.sdk.products.ProductProjection;
import io.sphere.sdk.products.ProductVariant;
import io.sphere.sdk.producttypes.ProductType;

import java.util.Collection;
import java.util.List;
import java.util.Locale;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Example for a formatter class which should be project specific.
 */
public class DefaultProductAttributeFormatterDemo {

    /**
     * Creates a formatter for your project based on all fetched product types and a list of locales.
     * Not reusable for users with different locales.
     *
     * @param productTypes all the product types of the project
     * @param locales the locales which should be used to translate labels and values
     * @return formatter
     */
    public static DefaultProductAttributeFormatter createFormatter(final Collection<ProductType> productTypes, final List<Locale> locales) {
        return new ProjectNameProductAttributeFormatter(productTypes, locales);
    }

    /**
     * The source code provides an example how to format a table of product attributes
     * @param attrNamesToShow a list containing the attribute names (the name is used as key) which clafies which attributes are allowed to displayed and also give an order to display them
     * @param productProjection the product that is used to the attributes of its master variant
     * @param formatter the project specific formatter
     */
    public static void example(final List<String> attrNamesToShow, final ProductProjection productProjection, final DefaultProductAttributeFormatter formatter) {
        final ProductVariant variant = productProjection.getMasterVariant();
        final Reference<ProductType> productType = productProjection.getProductType();
        final AttributeTable attributesTable = formatter.createAttributesTable(variant, productType, attrNamesToShow);
        final int valueColumnWidth = attributesTable.getMaxTranslatedValueLength();
        final int keyColumnWidth = attributesTable.getMaxTranslatedLabelLength();

        final StringBuilder stringBuilder = new StringBuilder("\n");
        for (final AttributeTableRow entry : attributesTable.getRows()) {
            stringBuilder.append(String.format("%-" + keyColumnWidth + "s", entry.getTranslatedLabel()))
                    .append(" | ")
                    .append(String.format("%-" + valueColumnWidth + "s", entry.getTranslatedValue()))
                    .append("\n")
                    .append(org.apache.commons.lang3.StringUtils.repeat('-', keyColumnWidth + valueColumnWidth + 3))
                    .append("\n");
        }
        final String table = stringBuilder.toString();
        final String expected = "\n" +
                "color                    | green                                \n" +
                "----------------------------------------------------------------\n" +
                "size                     | S                                    \n" +
                "----------------------------------------------------------------\n" +
                "matching products        | referenceable product                \n" +
                "----------------------------------------------------------------\n" +
                "washing labels           | tumble drying, Wash at or below 30°C \n" +
                "----------------------------------------------------------------\n" +
                "recommended retail price | EUR300.00                            \n" +
                "----------------------------------------------------------------\n" +
                "available since          | 2015-02-02                           \n" +
                "----------------------------------------------------------------\n";
        assertThat(table).isEqualTo(expected);
    }
}
