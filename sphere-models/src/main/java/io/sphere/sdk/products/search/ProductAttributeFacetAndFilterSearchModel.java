package io.sphere.sdk.products.search;

import io.sphere.sdk.products.ProductProjection;
import io.sphere.sdk.search.model.*;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZonedDateTime;

public class ProductAttributeFacetAndFilterSearchModel extends SearchModelImpl<ProductProjection> {

    ProductAttributeFacetAndFilterSearchModel(@Nullable final SearchModel<ProductProjection> parent, @Nullable final String pathSegment) {
        super(parent, pathSegment);
    }

    public TermFacetAndFilterSearchModel<ProductProjection, Boolean> ofBoolean(final String attributeName) {
        return booleanSearchModel(attributeName).facetedAndFiltered();
    }

    public TermFacetAndFilterSearchModel<ProductProjection, String> ofString(final String attributeName) {
        return stringSearchModel(attributeName).facetedAndFiltered();
    }

    public LocalizedStringFacetAndFilterSearchModel<ProductProjection> ofLocalizedString(final String attributeName) {
        return localizedStringFacetAndFilterSearchModel(attributeName);
    }

    public EnumFacetAndFilterSearchModel<ProductProjection> ofEnum(final String attributeName) {
        return enumFacetAndFilterSearchModel(attributeName);
    }

    public LocalizedEnumFacetAndFilterSearchModel<ProductProjection> ofLocalizableEnum(final String attributeName) {
        return localizedEnumFacetAndFilterSearchModel(attributeName);
    }

    public RangeTermFacetAndFilterSearchModel<ProductProjection, BigDecimal> ofNumber(final String attributeName) {
        return numberSearchModel(attributeName).facetedAndFiltered();
    }

    public MoneyFacetAndFilterSearchModel<ProductProjection> ofMoney(final String attributeName) {
        return moneyFacetAndFilterSearchModel(attributeName);
    }

    public RangeTermFacetAndFilterSearchModel<ProductProjection, LocalDate> ofDate(final String attributeName) {
        return dateSearchModel(attributeName).facetedAndFiltered();
    }

    public RangeTermFacetAndFilterSearchModel<ProductProjection, LocalTime> ofTime(final String attributeName) {
        return timeSearchModel(attributeName).facetedAndFiltered();
    }

    public RangeTermFacetAndFilterSearchModel<ProductProjection, ZonedDateTime> ofDateTime(final String attributeName) {
        return datetimeSearchModel(attributeName).facetedAndFiltered();
    }

    public ReferenceFacetAndFilterSearchModel<ProductProjection> ofReference(final String attributeName) {
        return referenceFacetAndFilterSearchModel(attributeName);
    }

    public TermFacetAndFilterSearchModel<ProductProjection, Boolean> ofBooleanSet(final String attributeName) {
        return ofBoolean(attributeName);
    }

    public TermFacetAndFilterSearchModel<ProductProjection, String> ofStringSet(final String attributeName) {
        return ofString(attributeName);
    }

    public LocalizedStringFacetAndFilterSearchModel<ProductProjection> ofLocalizedStringSet(final String attributeName) {
        return ofLocalizedString(attributeName);
    }

    public EnumFacetAndFilterSearchModel<ProductProjection> ofEnumSet(final String attributeName) {
        return ofEnum(attributeName);
    }

    public LocalizedEnumFacetAndFilterSearchModel<ProductProjection> ofLocalizableEnumSet(final String attributeName) {
        return ofLocalizableEnum(attributeName);
    }

    public RangeTermFacetAndFilterSearchModel<ProductProjection, BigDecimal> ofNumberSet(final String attributeName) {
        return ofNumber(attributeName);
    }

    public MoneyFacetAndFilterSearchModel<ProductProjection> ofMoneySet(final String attributeName) {
        return ofMoney(attributeName);
    }

    public RangeTermFacetAndFilterSearchModel<ProductProjection, LocalDate> ofDateSet(final String attributeName) {
        return ofDate(attributeName);
    }

    public RangeTermFacetAndFilterSearchModel<ProductProjection, LocalTime> ofTimeSet(final String attributeName) {
        return ofTime(attributeName);
    }

    public RangeTermFacetAndFilterSearchModel<ProductProjection, ZonedDateTime> ofDateTimeSet(final String attributeName) {
        return ofDateTime(attributeName);
    }

    public ReferenceFacetAndFilterSearchModel<ProductProjection> ofReferenceSet(final String attributeName) {
        return ofReference(attributeName);
    }
}
