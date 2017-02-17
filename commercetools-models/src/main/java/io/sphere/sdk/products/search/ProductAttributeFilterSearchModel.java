package io.sphere.sdk.products.search;

import io.sphere.sdk.products.ProductProjection;
import io.sphere.sdk.search.model.*;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZonedDateTime;

public final class ProductAttributeFilterSearchModel extends SearchModelImpl<ProductProjection> {

    ProductAttributeFilterSearchModel(@Nullable final SearchModel<ProductProjection> parent, @Nullable final String pathSegment) {
        super(parent, pathSegment);
    }

    public TermFilterExistsAndMissingSearchModel<ProductProjection, Boolean> ofBoolean(final String attributeName) {
        return booleanSearchModel(attributeName).filtered();
    }

    public TermFilterExistsAndMissingSearchModel<ProductProjection, String> ofString(final String attributeName) {
        return stringSearchModel(attributeName).filtered();
    }

    public LocalizedStringFilterSearchModel<ProductProjection> ofLocalizedString(final String attributeName) {
        return localizedStringFilterSearchModel(attributeName);
    }

    public EnumFilterSearchModel<ProductProjection> ofEnum(final String attributeName) {
        return enumFilterSearchModel(attributeName);
    }

    public LocalizedEnumFilterSearchModel<ProductProjection> ofLocalizedEnum(final String attributeName) {
        return localizedEnumFilterSearchModel(attributeName);
    }

    public RangeTermFilterSearchModel<ProductProjection, BigDecimal> ofNumber(final String attributeName) {
        return numberSearchModel(attributeName).filtered();
    }

    public MoneyFilterSearchModel<ProductProjection> ofMoney(final String attributeName) {
        return moneyFilterSearchModel(attributeName);
    }

    public RangeTermFilterSearchModel<ProductProjection, LocalDate> ofDate(final String attributeName) {
        return dateSearchModel(attributeName).filtered();
    }

    public RangeTermFilterSearchModel<ProductProjection, LocalTime> ofTime(final String attributeName) {
        return timeSearchModel(attributeName).filtered();
    }

    public RangeTermFilterSearchModel<ProductProjection, ZonedDateTime> ofDateTime(final String attributeName) {
        return datetimeSearchModel(attributeName).filtered();
    }

    public ReferenceFilterSearchModel<ProductProjection> ofReference(final String attributeName) {
        return referenceFilterSearchModel(attributeName);
    }

    public TermFilterExistsAndMissingSearchModel<ProductProjection, Boolean> ofBooleanSet(final String attributeName) {
        return ofBoolean(attributeName);
    }

    public TermFilterExistsAndMissingSearchModel<ProductProjection, String> ofStringSet(final String attributeName) {
        return ofString(attributeName);
    }

    public LocalizedStringFilterSearchModel<ProductProjection> ofLocalizedStringSet(final String attributeName) {
        return ofLocalizedString(attributeName);
    }

    public EnumFilterSearchModel<ProductProjection> ofEnumSet(final String attributeName) {
        return ofEnum(attributeName);
    }

    public LocalizedEnumFilterSearchModel<ProductProjection> ofLocalizedEnumSet(final String attributeName) {
        return ofLocalizedEnum(attributeName);
    }

    public RangeTermFilterSearchModel<ProductProjection, BigDecimal> ofNumberSet(final String attributeName) {
        return ofNumber(attributeName);
    }

    public MoneyFilterSearchModel<ProductProjection> ofMoneySet(final String attributeName) {
        return ofMoney(attributeName);
    }

    public RangeTermFilterSearchModel<ProductProjection, LocalDate> ofDateSet(final String attributeName) {
        return ofDate(attributeName);
    }

    public RangeTermFilterSearchModel<ProductProjection, LocalTime> ofTimeSet(final String attributeName) {
        return ofTime(attributeName);
    }

    public RangeTermFilterSearchModel<ProductProjection, ZonedDateTime> ofDateTimeSet(final String attributeName) {
        return ofDateTime(attributeName);
    }

    public ReferenceFilterSearchModel<ProductProjection> ofReferenceSet(final String attributeName) {
        return ofReference(attributeName);
    }
}
