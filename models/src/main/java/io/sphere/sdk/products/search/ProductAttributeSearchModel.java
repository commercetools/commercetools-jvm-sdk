package io.sphere.sdk.products.search;

import io.sphere.sdk.products.ProductProjection;
import io.sphere.sdk.search.*;

import java.util.Optional;

public class ProductAttributeSearchModel extends SearchModelImpl<ProductProjection> {

    ProductAttributeSearchModel(final Optional<? extends SearchModel<ProductProjection>> parent, final String pathSegment) {
        super(parent, pathSegment);
    }

    public BooleanSearchModel<ProductProjection, VariantSearchSortDirection> ofBoolean(final String attributeName) {
        return new BooleanSearchModel<>(Optional.of(this), attributeName);
    }

    public StringSearchModel<ProductProjection, VariantSearchSortDirection> ofText(final String attributeName) {
        return new StringSearchModel<>(Optional.of(this), attributeName);
    }

    public LocalizedStringsSearchModel<ProductProjection, VariantSearchSortDirection> ofLocalizableText(final String attributeName) {
        return new LocalizedStringsSearchModel<>(Optional.of(this), attributeName);
    }

    public EnumSearchModel<ProductProjection, VariantSearchSortDirection> ofEnum(final String attributeName) {
        return new EnumSearchModel<>(Optional.of(this), attributeName);
    }

    public LocalizedEnumSearchModel<ProductProjection, VariantSearchSortDirection> ofLocalizableEnum(final String attributeName) {
        return new LocalizedEnumSearchModel<>(Optional.of(this), attributeName);
    }

    public NumberSearchModel<ProductProjection, VariantSearchSortDirection> ofNumber(final String attributeName) {
        return new NumberSearchModel<>(Optional.of(this), attributeName);
    }

    public MoneySearchModel<ProductProjection, VariantSearchSortDirection> ofMoney(final String attributeName) {
        return new MoneySearchModel<>(Optional.of(this), attributeName);
    }

    public DateSearchModel<ProductProjection, VariantSearchSortDirection> ofDate(final String attributeName) {
        return new DateSearchModel<>(Optional.of(this), attributeName);
    }

    public TimeSearchModel<ProductProjection, VariantSearchSortDirection> ofTime(final String attributeName) {
        return new TimeSearchModel<>(Optional.of(this), attributeName);
    }

    public DateTimeSearchModel<ProductProjection, VariantSearchSortDirection> ofDateTime(final String attributeName) {
        return new DateTimeSearchModel<>(Optional.of(this), attributeName);
    }

    public <R> ReferenceSearchModel<ProductProjection, R> ofReference(final String attributeName) {
        return new ReferenceSearchModel<>(Optional.of(this), attributeName);
    }
}
