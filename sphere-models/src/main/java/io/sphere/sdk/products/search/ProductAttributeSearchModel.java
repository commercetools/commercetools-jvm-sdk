package io.sphere.sdk.products.search;

import io.sphere.sdk.categories.Category;
import io.sphere.sdk.channels.Channel;
import io.sphere.sdk.customobjects.CustomObject;
import io.sphere.sdk.products.Product;
import io.sphere.sdk.products.ProductProjection;
import io.sphere.sdk.producttypes.ProductType;
import io.sphere.sdk.reviews.Review;
import io.sphere.sdk.search.*;
import io.sphere.sdk.shippingmethods.ShippingMethod;
import io.sphere.sdk.states.State;
import io.sphere.sdk.zones.Zone;

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

    public ReferenceSearchModel<ProductProjection, Product> ofProductReference(final String attributeName) {
        return new ReferenceSearchModel<>(this, attributeName);
    }

    public ReferenceSearchModel<ProductProjection, ProductType> ofProductTypeReference(final String attributeName) {
        return new ReferenceSearchModel<>(this, attributeName);
    }

    public ReferenceSearchModel<ProductProjection, Channel> ofChannelReference(final String attributeName) {
        return new ReferenceSearchModel<>(this, attributeName);
    }

    public ReferenceSearchModel<ProductProjection, State> ofStateReference(final String attributeName) {
        return new ReferenceSearchModel<>(this, attributeName);
    }

    public ReferenceSearchModel<ProductProjection, Zone> ofZoneReference(final String attributeName) {
        return new ReferenceSearchModel<>(this, attributeName);
    }

    public ReferenceSearchModel<ProductProjection, ShippingMethod> ofShippingMethodReference(final String attributeName) {
        return new ReferenceSearchModel<>(this, attributeName);
    }

    public ReferenceSearchModel<ProductProjection, Category> ofCategoryReference(final String attributeName) {
        return new ReferenceSearchModel<>(this, attributeName);
    }

    public ReferenceSearchModel<ProductProjection, Review> ofReviewReference(final String attributeName) {
        return new ReferenceSearchModel<>(this, attributeName);
    }

    public <C> ReferenceSearchModel<ProductProjection, CustomObject<C>> ofCustomObjectReference(final String attributeName) {
        return new ReferenceSearchModel<>(this, attributeName);
    }
}
