package io.sphere.sdk.products.search;

import io.sphere.sdk.products.ProductProjection;
import io.sphere.sdk.search.model.*;

import javax.annotation.Nullable;

public class ProductAttributeSortSearchModel extends SearchModelImpl<ProductProjection> {

    ProductAttributeSortSearchModel(@Nullable final SearchModel<ProductProjection> parent, @Nullable final String pathSegment) {
        super(parent, pathSegment);
    }

    public MultiValueSortSearchModel<ProductProjection> ofBoolean(final String attributeName) {
        return sortModel(attributeName);
    }

    public MultiValueSortSearchModel<ProductProjection> ofString(final String attributeName) {
        return sortModel(attributeName);
    }

    public LocalizedStringSortSearchModel<ProductProjection, MultiValueSortSearchModel<ProductProjection>> ofLocalizedString(final String attributeName) {
        return new LocalizedStringSortSearchModel<>(this, attributeName, sortModelBuilder());
    }

    public EnumSortSearchModel<ProductProjection, MultiValueSortSearchModel<ProductProjection>> ofEnum(final String attributeName) {
        return new EnumSortSearchModel<>(this, attributeName, sortModelBuilder());
    }

    public LocalizedEnumSortSearchModel<ProductProjection, MultiValueSortSearchModel<ProductProjection>> ofLocalizableEnum(final String attributeName) {
        return new LocalizedEnumSortSearchModel<>(this, attributeName, sortModelBuilder());
    }

    public MultiValueSortSearchModel<ProductProjection> ofNumber(final String attributeName) {
        return sortModel(attributeName);
    }

    public MoneySortSearchModel<ProductProjection, MultiValueSortSearchModel<ProductProjection>> ofMoney(final String attributeName) {
        return new MoneySortSearchModel<>(this, attributeName, sortModelBuilder());
    }

    public MultiValueSortSearchModel<ProductProjection> ofDate(final String attributeName) {
        return sortModel(attributeName);
    }

    public MultiValueSortSearchModel<ProductProjection> ofTime(final String attributeName) {
        return sortModel(attributeName);
    }

    public MultiValueSortSearchModel<ProductProjection> ofDateTime(final String attributeName) {
        return sortModel(attributeName);
    }

    public MultiValueSortSearchModel<ProductProjection> ofBooleanSet(final String attributeName) {
        return ofBoolean(attributeName);
    }

    public MultiValueSortSearchModel<ProductProjection> ofStringSet(final String attributeName) {
        return ofString(attributeName);
    }

    public LocalizedStringSortSearchModel<ProductProjection, MultiValueSortSearchModel<ProductProjection>> ofLocalizedStringSet(final String attributeName) {
        return ofLocalizedString(attributeName);
    }

    public EnumSortSearchModel<ProductProjection, MultiValueSortSearchModel<ProductProjection>> ofEnumSet(final String attributeName) {
        return ofEnum(attributeName);
    }

    public LocalizedEnumSortSearchModel<ProductProjection, MultiValueSortSearchModel<ProductProjection>> ofLocalizableEnumSet(final String attributeName) {
        return ofLocalizableEnum(attributeName);
    }

    public MultiValueSortSearchModel<ProductProjection> ofNumberSet(final String attributeName) {
        return ofNumber(attributeName);
    }

    public MoneySortSearchModel<ProductProjection, MultiValueSortSearchModel<ProductProjection>> ofMoneySet(final String attributeName) {
        return ofMoney(attributeName);
    }

    public MultiValueSortSearchModel<ProductProjection> ofDateSet(final String attributeName) {
        return ofDate(attributeName);
    }

    public MultiValueSortSearchModel<ProductProjection> ofTimeSet(final String attributeName) {
        return ofTime(attributeName);
    }

    public MultiValueSortSearchModel<ProductProjection> ofDateTimeSet(final String attributeName) {
        return ofDateTime(attributeName);
    }

    private MultiValueSortSearchModel<ProductProjection> sortModel(final String attributeName) {
        return new SortableSearchModel<>(this, attributeName, sortModelBuilder()).sorted();
    }

    private MultiValueSortSearchModelBuilder<ProductProjection> sortModelBuilder() {
        return new MultiValueSortSearchModelBuilder<>();
    }
}
