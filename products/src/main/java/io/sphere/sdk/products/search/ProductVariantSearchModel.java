package io.sphere.sdk.products.search;

import io.sphere.sdk.products.ProductProjection;
import io.sphere.sdk.search.*;

import java.util.Optional;

public class ProductVariantSearchModel extends SearchModelImpl<ProductProjection> {

    ProductVariantSearchModel(final Optional<? extends SearchModel<ProductProjection>> parent, final Optional<String> pathSegment) {
        super(parent, pathSegment);
    }

    ProductVariantSearchModel(Optional<? extends SearchModel<ProductProjection>> parent, final String pathSegment) {
        this(parent, Optional.of(pathSegment));
    }

    public BooleanSearchModel<ProductProjection> booleanAttribute(String attributeName) {
        return attributes().ofBoolean(attributeName);
    }

    public StringSearchModel<ProductProjection> textAttribute(String attributeName) {
        return attributes().ofText(attributeName);
    }

    public LocalizedStringsSearchModel<ProductProjection> localizableTextAttribute(String attributeName) {
        return attributes().ofLocalizableText(attributeName);
    }

    public EnumSearchModel<ProductProjection> enumAttribute(String attributeName) {
        return attributes().ofEnum(attributeName);
    }

    public LocalizedEnumSearchModel<ProductProjection> localizableEnumAttribute(String attributeName) {
        return attributes().ofLocalizableEnum(attributeName);
    }

    public NumberSearchModel<ProductProjection> numberAttribute(String attributeName) {
        return attributes().ofNumber(attributeName);
    }

    public MoneySearchModel<ProductProjection> moneyAttribute(String attributeName) {
        return attributes().ofMoney(attributeName);
    }

    public DateSearchModel<ProductProjection> dateAttribute(String attributeName) {
        return attributes().ofDate(attributeName);
    }

    public TimeSearchModel<ProductProjection> timeAttribute(String attributeName) {
        return attributes().ofTime(attributeName);
    }

    public DateTimeSearchModel<ProductProjection> dateTimeAttribute(String attributeName) {
        return attributes().ofDateTime(attributeName);
    }

    public <R> ReferenceSearchModel<ProductProjection, R> referenceAttribute(String attributeName) {
        return attributes().ofReference(attributeName);
    }

    private ProductAttributeSearchModel attributes() {
        return new ProductAttributeSearchModel(Optional.of(this), "attributes");
    }

    public MoneySearchModel<ProductProjection> price() {
        return new MoneySearchModel<>(Optional.of(this), "price");
    }
}
