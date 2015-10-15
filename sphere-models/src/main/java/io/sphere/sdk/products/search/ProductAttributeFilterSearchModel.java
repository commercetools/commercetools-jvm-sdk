package io.sphere.sdk.products.search;

import io.sphere.sdk.products.ProductProjection;
import io.sphere.sdk.search.model.*;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZonedDateTime;

public class ProductAttributeFilterSearchModel extends SearchModelImpl<ProductProjection> {

    ProductAttributeFilterSearchModel(@Nullable final SearchModel<ProductProjection> parent, @Nullable final String pathSegment) {
        super(parent, pathSegment);
    }

    public TermFilterSearchModel<ProductProjection, Boolean> ofBoolean(final String attributeName) {
        return new BooleanSearchModel<>(this, attributeName).filtered();
    }

    public TermFilterSearchModel<ProductProjection, String> ofString(final String attributeName) {
        return new StringSearchModel<>(this, attributeName).filtered();
    }

    public LocalizedStringFilterSearchModel<ProductProjection> ofLocalizedString(final String attributeName) {
        return new LocalizedStringFilterSearchModel<>(this, attributeName);
    }

    public EnumFilterSearchModel<ProductProjection> ofEnum(final String attributeName) {
        return new EnumFilterSearchModel<>(this, attributeName);
    }

    public LocalizedEnumFilterSearchModel<ProductProjection> ofLocalizableEnum(final String attributeName) {
        return new LocalizedEnumFilterSearchModel<>(this, attributeName);
    }

    public RangeFilterSearchModel<ProductProjection, BigDecimal> ofNumber(final String attributeName) {
        return new NumberSearchModel<>(this, attributeName).filtered();
    }

    public MoneyFilterSearchModel<ProductProjection> ofMoney(final String attributeName) {
        return new MoneyFilterSearchModel<>(this, attributeName);
    }

    public RangeFilterSearchModel<ProductProjection, LocalDate> ofDate(final String attributeName) {
        return new DateSearchModel<>(this, attributeName).filtered();
    }

    public RangeFilterSearchModel<ProductProjection, LocalTime> ofTime(final String attributeName) {
        return new TimeSearchModel<>(this, attributeName).filtered();
    }

    public RangeFilterSearchModel<ProductProjection, ZonedDateTime> ofDateTime(final String attributeName) {
        return new DateTimeSearchModel<>(this, attributeName).filtered();
    }

    public ReferenceFilterSearchModel<ProductProjection> ofReference(final String attributeName) {
        return new ReferenceFilterSearchModel<>(this, attributeName);
    }

    public TermFilterSearchModel<ProductProjection, Boolean> ofBooleanSet(final String attributeName) {
        return ofBoolean(attributeName);
    }

    public TermFilterSearchModel<ProductProjection, String> ofStringSet(final String attributeName) {
        return ofString(attributeName);
    }

    public LocalizedStringFilterSearchModel<ProductProjection> ofLocalizedStringSet(final String attributeName) {
        return ofLocalizedString(attributeName);
    }

    public EnumFilterSearchModel<ProductProjection> ofEnumSet(final String attributeName) {
        return ofEnum(attributeName);
    }

    public LocalizedEnumFilterSearchModel<ProductProjection> ofLocalizableEnumSet(final String attributeName) {
        return ofLocalizableEnum(attributeName);
    }

    public RangeFilterSearchModel<ProductProjection, BigDecimal> ofNumberSet(final String attributeName) {
        return ofNumber(attributeName);
    }

    public MoneyFilterSearchModel<ProductProjection> ofMoneySet(final String attributeName) {
        return ofMoney(attributeName);
    }

    public RangeFilterSearchModel<ProductProjection, LocalDate> ofDateSet(final String attributeName) {
        return ofDate(attributeName);
    }

    public RangeFilterSearchModel<ProductProjection, LocalTime> ofTimeSet(final String attributeName) {
        return ofTime(attributeName);
    }

    public RangeFilterSearchModel<ProductProjection, ZonedDateTime> ofDateTimeSet(final String attributeName) {
        return ofDateTime(attributeName);
    }

    public ReferenceFilterSearchModel<ProductProjection> ofReferenceSet(final String attributeName) {
        return ofReference(attributeName);
    }
}
