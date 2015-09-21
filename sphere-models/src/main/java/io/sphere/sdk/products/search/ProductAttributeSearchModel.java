package io.sphere.sdk.products.search;

import io.sphere.sdk.products.ProductProjection;
import io.sphere.sdk.search.*;

import javax.annotation.Nullable;

public class ProductAttributeSearchModel extends SearchModelImpl<ProductProjection> {

    public ProductAttributeSearchModel(@Nullable final SearchModel<ProductProjection> parent, @Nullable final String pathSegment) {
        super(parent, pathSegment);
    }

    public BooleanSearchModel<ProductProjection, DirectionlessMultiValueSearchSortModel<ProductProjection>> ofBoolean(final String attributeName) {
        return new BooleanSearchModel<>(this, attributeName, new DirectionlessMultiValueSearchSortBuilder<>());
    }

    public StringSearchModel<ProductProjection, DirectionlessMultiValueSearchSortModel<ProductProjection>> ofString(final String attributeName) {
        return new StringSearchModel<>(this, attributeName, new DirectionlessMultiValueSearchSortBuilder<>());
    }

    public LocalizedStringSearchModel<ProductProjection, DirectionlessMultiValueSearchSortModel<ProductProjection>> ofLocalizedString(final String attributeName) {
        return new LocalizedStringSearchModel<>(this, attributeName, new DirectionlessMultiValueSearchSortBuilder<>());
    }

    public EnumSearchModel<ProductProjection, DirectionlessMultiValueSearchSortModel<ProductProjection>> ofEnum(final String attributeName) {
        return new EnumSearchModel<>(this, attributeName, new DirectionlessMultiValueSearchSortBuilder<>());
    }

    public LocalizedEnumSearchModel<ProductProjection, DirectionlessMultiValueSearchSortModel<ProductProjection>> ofLocalizableEnum(final String attributeName) {
        return new LocalizedEnumSearchModel<>(this, attributeName, new DirectionlessMultiValueSearchSortBuilder<>());
    }

    public NumberSearchModel<ProductProjection, DirectionlessMultiValueSearchSortModel<ProductProjection>> ofNumber(final String attributeName) {
        return new NumberSearchModel<>(this, attributeName, new DirectionlessMultiValueSearchSortBuilder<>());
    }

    public MoneySearchModel<ProductProjection, DirectionlessMultiValueSearchSortModel<ProductProjection>> ofMoney(final String attributeName) {
        return new MoneySearchModel<>(this, attributeName, new DirectionlessMultiValueSearchSortBuilder<>());
    }

    public DateSearchModel<ProductProjection, DirectionlessMultiValueSearchSortModel<ProductProjection>> ofDate(final String attributeName) {
        return new DateSearchModel<>(this, attributeName, new DirectionlessMultiValueSearchSortBuilder<>());
    }

    public TimeSearchModel<ProductProjection, DirectionlessMultiValueSearchSortModel<ProductProjection>> ofTime(final String attributeName) {
        return new TimeSearchModel<>(this, attributeName, new DirectionlessMultiValueSearchSortBuilder<>());
    }

    public DateTimeSearchModel<ProductProjection, DirectionlessMultiValueSearchSortModel<ProductProjection>> ofDateTime(final String attributeName) {
        return new DateTimeSearchModel<>(this, attributeName, new DirectionlessMultiValueSearchSortBuilder<>());
    }

    public ReferenceSearchModel<ProductProjection, DirectionlessMultiValueSearchSortModel<ProductProjection>> ofReference(final String attributeName) {
        return new ReferenceSearchModel<>(this, attributeName, new DirectionlessMultiValueSearchSortBuilder<>());
    }
}
