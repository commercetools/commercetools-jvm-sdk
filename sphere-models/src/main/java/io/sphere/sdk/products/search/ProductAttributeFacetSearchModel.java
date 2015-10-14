package io.sphere.sdk.products.search;

import io.sphere.sdk.products.ProductProjection;
import io.sphere.sdk.search.model.*;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZonedDateTime;

public class ProductAttributeFacetSearchModel extends SearchModelImpl<ProductProjection> {

    ProductAttributeFacetSearchModel(@Nullable final SearchModel<ProductProjection> parent, @Nullable final String pathSegment) {
        super(parent, pathSegment);
    }

    public TermFacetSearchModel<ProductProjection, Boolean> ofBoolean(final String attributeName) {
        return new BooleanSearchModel<>(this, attributeName).faceted();
    }

    public TermFacetSearchModel<ProductProjection, String> ofString(final String attributeName) {
        return new StringSearchModel<>(this, attributeName).faceted();
    }

    public LocalizedStringFacetSearchModel<ProductProjection> ofLocalizedString(final String attributeName) {
        return new LocalizedStringFacetSearchModel<>(this, attributeName);
    }

    public EnumFacetSearchModel<ProductProjection> ofEnum(final String attributeName) {
        return new EnumFacetSearchModel<>(this, attributeName);
    }

    public LocalizedEnumFacetSearchModel<ProductProjection> ofLocalizableEnum(final String attributeName) {
        return new LocalizedEnumFacetSearchModel<>(this, attributeName);
    }

    public RangeFacetSearchModel<ProductProjection, BigDecimal> ofNumber(final String attributeName) {
        return new NumberSearchModel<>(this, attributeName).faceted();
    }

    public MoneyFacetSearchModel<ProductProjection> ofMoney(final String attributeName) {
        return new MoneyFacetSearchModel<>(this, attributeName);
    }

    public RangeFacetSearchModel<ProductProjection, LocalDate> ofDate(final String attributeName) {
        return new DateSearchModel<>(this, attributeName).faceted();
    }

    public RangeFacetSearchModel<ProductProjection, LocalTime> ofTime(final String attributeName) {
        return new TimeSearchModel<>(this, attributeName).faceted();
    }

    public RangeFacetSearchModel<ProductProjection, ZonedDateTime> ofDateTime(final String attributeName) {
        return new DateTimeSearchModel<>(this, attributeName).faceted();
    }

    public ReferenceFacetSearchModel<ProductProjection> ofReference(final String attributeName) {
        return new ReferenceFacetSearchModel<>(this, attributeName);
    }
}
