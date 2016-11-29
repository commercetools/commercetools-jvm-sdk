package io.sphere.sdk.products.attributes;

import io.sphere.sdk.models.LocalizedEnumValue;
import io.sphere.sdk.models.Reference;
import io.sphere.sdk.products.Product;
import io.sphere.sdk.products.ProductCatalogData;
import io.sphere.sdk.products.ProductData;
import io.sphere.sdk.producttypes.ProductType;

import java.time.LocalDate;
import java.util.*;

import static java.util.stream.Collectors.joining;

/**
 * Example class for a project specific product attribute formatter.
 */
public final class ProjectNameProductAttributeFormatter extends DefaultProductAttributeFormatter {
    public ProjectNameProductAttributeFormatter(final Collection<ProductType> productTypes, final List<Locale> locales) {
        super(productTypes, locales);
    }

    /**
     * Formats a product reference attribute by using the localized product name instead of the ID.
     *
     * @param productReference product
     * @param attribute attribute containing the product reference
     * @param productType the product type belonging to the product of the attribute
     * @return localized name of the product or null
     */
    @Override
    protected String convertProductReference(final Reference<Product> productReference, final Attribute attribute, final ProductType productType) {
        return Optional.ofNullable(productReference.getObj())
        .map(Product::getMasterData)
        .map(ProductCatalogData::getStaged)
        .map(ProductData::getName)
        .map(name -> name.get(getLocales()))
        .orElse(null);
    }

    /**
     * Formats a product reference set attribute by using the localized product name instead of the ID.
     *
     * @param referenceSet products
     * @param attribute attribute containing the product reference
     * @param productType the product type belonging to the product of the attribute
     * @return translated name of the product or null
     */
    @Override
    protected String convertProductReferenceSet(final Set<Reference<Product>> referenceSet, final Attribute attribute, final ProductType productType) {
        return referenceSet.stream()
                .map(ref -> convertProductReference(ref, attribute, productType))
                .collect(joining(", "));
    }

    /**
     * Formats a {@link LocalizedEnumValue} set product attribute by translating the label and coma separate the results which are sorted in the reverse order.
     * @param localizedEnumValueSet the attribute value to format
     * @param attribute the attribute to format
     * @param productType the product type belonging to the product of the attribute
     * @return formatted attribute value
     */
    @Override
    protected String convertLocalizedEnumValueSet(final Set<LocalizedEnumValue> localizedEnumValueSet, final Attribute attribute, final ProductType productType) {
        return localizedEnumValueSet.stream()
                .map(value -> convertLocalizedEnumValue(value, attribute, productType))
                .sorted(Comparator.reverseOrder())
                .collect(joining(", "));
    }

    /**
     * Format a data attribute by using just {@link Object#toString()}.
     * @param dateValue the attribute value to format
     * @param attribute the attribute to format
     * @param productType the product type belonging to the product of the attribute
     * @return formatted date
     */
    @Override
    protected String convertDate(final LocalDate dateValue, final Attribute attribute, final ProductType productType) {
        return dateValue.toString();
    }
}
