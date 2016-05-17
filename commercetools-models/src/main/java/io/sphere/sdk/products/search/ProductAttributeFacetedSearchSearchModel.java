package io.sphere.sdk.products.search;

import io.sphere.sdk.products.ProductProjection;
import io.sphere.sdk.search.model.*;

import javax.annotation.Nullable;

public final class ProductAttributeFacetedSearchSearchModel extends SearchModelImpl<ProductProjection> {

    ProductAttributeFacetedSearchSearchModel(@Nullable final SearchModel<ProductProjection> parent, @Nullable final String pathSegment) {
        super(parent, pathSegment);
    }

    public TermFacetedSearchSearchModel<ProductProjection> ofBoolean(final String attributeName) {
        return booleanSearchModel(attributeName).facetedAndFiltered();
    }

    public TermFacetedSearchSearchModel<ProductProjection> ofString(final String attributeName) {
        return stringSearchModel(attributeName).facetedAndFiltered();
    }

    public LocalizedStringFacetedSearchSearchModel<ProductProjection> ofLocalizedString(final String attributeName) {
        return localizedStringFacetedSearchSearchModel(attributeName);
    }

    public EnumFacetedSearchSearchModel<ProductProjection> ofEnum(final String attributeName) {
        return enumFacetedSearchSearchModel(attributeName);
    }

    public LocalizedEnumFacetedSearchSearchModel<ProductProjection> ofLocalizedEnum(final String attributeName) {
        return localizedEnumFacetedSearchSearchModel(attributeName);
    }

    public RangeTermFacetedSearchSearchModel<ProductProjection> ofNumber(final String attributeName) {
        return numberSearchModel(attributeName).facetedAndFiltered();
    }

    public MoneyFacetedSearchSearchModel<ProductProjection> ofMoney(final String attributeName) {
        return moneyFacetedSearchSearchModel(attributeName);
    }

    public RangeTermFacetedSearchSearchModel<ProductProjection> ofDate(final String attributeName) {
        return dateSearchModel(attributeName).facetedAndFiltered();
    }

    public RangeTermFacetedSearchSearchModel<ProductProjection> ofTime(final String attributeName) {
        return timeSearchModel(attributeName).facetedAndFiltered();
    }

    public RangeTermFacetedSearchSearchModel<ProductProjection> ofDateTime(final String attributeName) {
        return datetimeSearchModel(attributeName).facetedAndFiltered();
    }

    public ReferenceFacetedSearchSearchModel<ProductProjection> ofReference(final String attributeName) {
        return referenceFacetedSearchSearchModel(attributeName);
    }

    public TermFacetedSearchSearchModel<ProductProjection> ofBooleanSet(final String attributeName) {
        return ofBoolean(attributeName);
    }

    public TermFacetedSearchSearchModel<ProductProjection> ofStringSet(final String attributeName) {
        return ofString(attributeName);
    }

    public LocalizedStringFacetedSearchSearchModel<ProductProjection> ofLocalizedStringSet(final String attributeName) {
        return ofLocalizedString(attributeName);
    }

    public EnumFacetedSearchSearchModel<ProductProjection> ofEnumSet(final String attributeName) {
        return ofEnum(attributeName);
    }

    public LocalizedEnumFacetedSearchSearchModel<ProductProjection> ofLocalizedEnumSet(final String attributeName) {
        return ofLocalizedEnum(attributeName);
    }

    public RangeTermFacetedSearchSearchModel<ProductProjection> ofNumberSet(final String attributeName) {
        return ofNumber(attributeName);
    }

    public MoneyFacetedSearchSearchModel<ProductProjection> ofMoneySet(final String attributeName) {
        return ofMoney(attributeName);
    }

    public RangeTermFacetedSearchSearchModel<ProductProjection> ofDateSet(final String attributeName) {
        return ofDate(attributeName);
    }

    public RangeTermFacetedSearchSearchModel<ProductProjection> ofTimeSet(final String attributeName) {
        return ofTime(attributeName);
    }

    public RangeTermFacetedSearchSearchModel<ProductProjection> ofDateTimeSet(final String attributeName) {
        return ofDateTime(attributeName);
    }

    public ReferenceFacetedSearchSearchModel<ProductProjection> ofReferenceSet(final String attributeName) {
        return ofReference(attributeName);
    }
}
