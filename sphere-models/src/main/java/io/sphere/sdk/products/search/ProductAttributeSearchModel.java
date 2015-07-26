package io.sphere.sdk.products.search;

import io.sphere.sdk.products.ProductProjection;
import io.sphere.sdk.search.*;

public class ProductAttributeSearchModel extends SearchModelImpl<ProductProjection> {

    ProductAttributeSearchModel(final SearchModel<ProductProjection> parent, final String pathSegment) {
        super(parent, pathSegment);
    }

    public BooleanSearchModel<ProductProjection, VariantSearchSortDirection> ofBoolean(final String attributeName) {
        return new BooleanSearchModel<>(this, attributeName);
    }

    public StringSearchModel<ProductProjection, VariantSearchSortDirection> ofText(final String attributeName) {
        return new StringSearchModel<>(this, attributeName);
    }

    public LocalizedStringsSearchModel<ProductProjection, VariantSearchSortDirection> ofLocalizableText(final String attributeName) {
        return new LocalizedStringsSearchModel<>(this, attributeName);
    }

    public EnumSearchModel<ProductProjection, VariantSearchSortDirection> ofEnum(final String attributeName) {
        return new EnumSearchModel<>(this, attributeName);
    }

    public LocalizedEnumSearchModel<ProductProjection, VariantSearchSortDirection> ofLocalizableEnum(final String attributeName) {
        return new LocalizedEnumSearchModel<>(this, attributeName);
    }

    public NumberSearchModel<ProductProjection, VariantSearchSortDirection> ofNumber(final String attributeName) {
        return new NumberSearchModel<>(this, attributeName);
    }

    public MoneySearchModel<ProductProjection, VariantSearchSortDirection> ofMoney(final String attributeName) {
        return new MoneySearchModel<>(this, attributeName);
    }

    public DateSearchModel<ProductProjection, VariantSearchSortDirection> ofDate(final String attributeName) {
        return new DateSearchModel<>(this, attributeName);
    }

    public TimeSearchModel<ProductProjection, VariantSearchSortDirection> ofTime(final String attributeName) {
        return new TimeSearchModel<>(this, attributeName);
    }

    public DateTimeSearchModel<ProductProjection, VariantSearchSortDirection> ofDateTime(final String attributeName) {
        return new DateTimeSearchModel<>(this, attributeName);
    }

    public <R> ReferenceSearchModel<ProductProjection, R> ofReference(final String attributeName) {
        return new ReferenceSearchModel<>(this, attributeName);
    }
}
