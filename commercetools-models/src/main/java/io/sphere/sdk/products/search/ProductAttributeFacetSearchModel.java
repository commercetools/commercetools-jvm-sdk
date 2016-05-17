package io.sphere.sdk.products.search;

import io.sphere.sdk.products.ProductProjection;
import io.sphere.sdk.search.model.*;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZonedDateTime;

public final class ProductAttributeFacetSearchModel extends SearchModelImpl<ProductProjection> {

    ProductAttributeFacetSearchModel(@Nullable final SearchModel<ProductProjection> parent, @Nullable final String pathSegment) {
        super(parent, pathSegment);
    }

    public TermFacetSearchModel<ProductProjection, Boolean> ofBoolean(final String attributeName) {
        return booleanSearchModel(attributeName).faceted();
    }

    public TermFacetSearchModel<ProductProjection, String> ofString(final String attributeName) {
        return stringSearchModel(attributeName).faceted();
    }

    public LocalizedStringFacetSearchModel<ProductProjection> ofLocalizedString(final String attributeName) {
        return localizedStringFacetSearchModel(attributeName);
    }

    public EnumFacetSearchModel<ProductProjection> ofEnum(final String attributeName) {
        return enumFacetSearchModel(attributeName);
    }

    public LocalizedEnumFacetSearchModel<ProductProjection> ofLocalizedEnum(final String attributeName) {
        return localizedEnumFacetSearchModel(attributeName);
    }

    public RangeTermFacetSearchModel<ProductProjection, BigDecimal> ofNumber(final String attributeName) {
        return numberSearchModel(attributeName).faceted();
    }

    public MoneyFacetSearchModel<ProductProjection> ofMoney(final String attributeName) {
        return moneyFacetSearchModel(attributeName);
    }

    public RangeTermFacetSearchModel<ProductProjection, LocalDate> ofDate(final String attributeName) {
        return dateSearchModel(attributeName).faceted();
    }

    public RangeTermFacetSearchModel<ProductProjection, LocalTime> ofTime(final String attributeName) {
        return timeSearchModel(attributeName).faceted();
    }

    public RangeTermFacetSearchModel<ProductProjection, ZonedDateTime> ofDateTime(final String attributeName) {
        return datetimeSearchModel(attributeName).faceted();
    }

    public ReferenceFacetSearchModel<ProductProjection> ofReference(final String attributeName) {
        return referenceFacetSearchModel(attributeName);
    }

    public TermFacetSearchModel<ProductProjection, Boolean> ofBooleanSet(final String attributeName) {
        return ofBoolean(attributeName);
    }

    public TermFacetSearchModel<ProductProjection, String> ofStringSet(final String attributeName) {
        return ofString(attributeName);
    }

    public LocalizedStringFacetSearchModel<ProductProjection> ofLocalizedStringSet(final String attributeName) {
        return ofLocalizedString(attributeName);
    }

    public EnumFacetSearchModel<ProductProjection> ofEnumSet(final String attributeName) {
        return ofEnum(attributeName);
    }

    public LocalizedEnumFacetSearchModel<ProductProjection> ofLocalizedEnumSet(final String attributeName) {
        return ofLocalizedEnum(attributeName);
    }

    public RangeTermFacetSearchModel<ProductProjection, BigDecimal> ofNumberSet(final String attributeName) {
        return ofNumber(attributeName);
    }

    public MoneyFacetSearchModel<ProductProjection> ofMoneySet(final String attributeName) {
        return ofMoney(attributeName);
    }

    public RangeTermFacetSearchModel<ProductProjection, LocalDate> ofDateSet(final String attributeName) {
        return ofDate(attributeName);
    }

    public RangeTermFacetSearchModel<ProductProjection, LocalTime> ofTimeSet(final String attributeName) {
        return ofTime(attributeName);
    }

    public RangeTermFacetSearchModel<ProductProjection, ZonedDateTime> ofDateTimeSet(final String attributeName) {
        return ofDateTime(attributeName);
    }

    public ReferenceFacetSearchModel<ProductProjection> ofReferenceSet(final String attributeName) {
        return ofReference(attributeName);
    }
}
