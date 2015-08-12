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

    public StringSearchModel<ProductProjection, VariantSearchSortDirection> ofString(final String attributeName) {
        return new StringSearchModel<>(this, attributeName);
    }

    public LocalizedStringSearchModel<ProductProjection, VariantSearchSortDirection> ofLocalizedString(final String attributeName) {
        return new LocalizedStringSearchModel<>(this, attributeName);
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

    public ReferenceSearchModel<ProductProjection, VariantSearchSortDirection> ofReference(final String attributeName) {
        return new ReferenceSearchModel<>(this, attributeName);
    }
}
