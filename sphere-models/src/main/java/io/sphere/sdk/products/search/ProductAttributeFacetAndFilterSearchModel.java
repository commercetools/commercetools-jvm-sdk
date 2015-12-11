package io.sphere.sdk.products.search;

import io.sphere.sdk.products.ProductProjection;
import io.sphere.sdk.search.model.*;

import javax.annotation.Nullable;

public class ProductAttributeFacetAndFilterSearchModel extends SearchModelImpl<ProductProjection> {

    ProductAttributeFacetAndFilterSearchModel(@Nullable final SearchModel<ProductProjection> parent, @Nullable final String pathSegment) {
        super(parent, pathSegment);
    }

    public TermFacetAndFilterSearchModel<ProductProjection> ofBoolean(final String attributeName) {
        return booleanSearchModel(attributeName).facetedAndFiltered();
    }

    public TermFacetAndFilterSearchModel<ProductProjection> ofString(final String attributeName) {
        return stringSearchModel(attributeName).facetedAndFiltered();
    }

    public LocalizedStringFacetAndFilterSearchModel<ProductProjection> ofLocalizedString(final String attributeName) {
        return localizedStringFacetAndFilterSearchModel(attributeName);
    }

    public EnumFacetAndFilterSearchModel<ProductProjection> ofEnum(final String attributeName) {
        return enumFacetAndFilterSearchModel(attributeName);
    }

    public LocalizedEnumFacetAndFilterSearchModel<ProductProjection> ofLocalizedEnum(final String attributeName) {
        return localizedEnumFacetAndFilterSearchModel(attributeName);
    }

    public RangeTermFacetAndFilterSearchModel<ProductProjection> ofNumber(final String attributeName) {
        return numberSearchModel(attributeName).facetedAndFiltered();
    }

    public MoneyFacetAndFilterSearchModel<ProductProjection> ofMoney(final String attributeName) {
        return moneyFacetAndFilterSearchModel(attributeName);
    }

    public RangeTermFacetAndFilterSearchModel<ProductProjection> ofDate(final String attributeName) {
        return dateSearchModel(attributeName).facetedAndFiltered();
    }

    public RangeTermFacetAndFilterSearchModel<ProductProjection> ofTime(final String attributeName) {
        return timeSearchModel(attributeName).facetedAndFiltered();
    }

    public RangeTermFacetAndFilterSearchModel<ProductProjection> ofDateTime(final String attributeName) {
        return datetimeSearchModel(attributeName).facetedAndFiltered();
    }

    public ReferenceFacetAndFilterSearchModel<ProductProjection> ofReference(final String attributeName) {
        return referenceFacetAndFilterSearchModel(attributeName);
    }

    public TermFacetAndFilterSearchModel<ProductProjection> ofBooleanSet(final String attributeName) {
        return ofBoolean(attributeName);
    }

    public TermFacetAndFilterSearchModel<ProductProjection> ofStringSet(final String attributeName) {
        return ofString(attributeName);
    }

    public LocalizedStringFacetAndFilterSearchModel<ProductProjection> ofLocalizedStringSet(final String attributeName) {
        return ofLocalizedString(attributeName);
    }

    public EnumFacetAndFilterSearchModel<ProductProjection> ofEnumSet(final String attributeName) {
        return ofEnum(attributeName);
    }

    public LocalizedEnumFacetAndFilterSearchModel<ProductProjection> ofLocalizedEnumSet(final String attributeName) {
        return ofLocalizedEnum(attributeName);
    }

    public RangeTermFacetAndFilterSearchModel<ProductProjection> ofNumberSet(final String attributeName) {
        return ofNumber(attributeName);
    }

    public MoneyFacetAndFilterSearchModel<ProductProjection> ofMoneySet(final String attributeName) {
        return ofMoney(attributeName);
    }

    public RangeTermFacetAndFilterSearchModel<ProductProjection> ofDateSet(final String attributeName) {
        return ofDate(attributeName);
    }

    public RangeTermFacetAndFilterSearchModel<ProductProjection> ofTimeSet(final String attributeName) {
        return ofTime(attributeName);
    }

    public RangeTermFacetAndFilterSearchModel<ProductProjection> ofDateTimeSet(final String attributeName) {
        return ofDateTime(attributeName);
    }

    public ReferenceFacetAndFilterSearchModel<ProductProjection> ofReferenceSet(final String attributeName) {
        return ofReference(attributeName);
    }

    /**
     * @deprecated use {@link #ofLocalizedEnum(String)} instead
     */
    @Deprecated
    public LocalizedEnumFacetAndFilterSearchModel<ProductProjection> ofLocalizableEnum(final String attributeName) {
        return localizedEnumFacetAndFilterSearchModel(attributeName);
    }

    /**
     * @deprecated use {@link #ofLocalizedEnumSet(String)} instead
     */
    @Deprecated
    public LocalizedEnumFacetAndFilterSearchModel<ProductProjection> ofLocalizableEnumSet(final String attributeName) {
        return ofLocalizableEnum(attributeName);
    }
}
