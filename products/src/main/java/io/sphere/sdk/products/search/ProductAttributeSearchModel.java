package io.sphere.sdk.products.search;

import io.sphere.sdk.search.*;

import java.util.Optional;

public class ProductAttributeSearchModel<T> extends SearchModelImpl<T> {

    ProductAttributeSearchModel(final Optional<? extends SearchModel<T>> parent, final String pathSegment) {
        super(parent, pathSegment);
    }

    public BooleanSearchModel<T> ofBoolean(final String attributeName) {
        return new BooleanSearchModel<>(Optional.of(this), attributeName);
    }

    public StringSearchModel<T> ofText(final String attributeName) {
        return new StringSearchModel<>(Optional.of(this), attributeName);
    }

    public LocalizedStringsSearchModel<T> ofLocalizableText(final String attributeName) {
        return new LocalizedStringsSearchModel<>(Optional.of(this), attributeName);
    }

    public EnumSearchModel<T> ofEnum(final String attributeName) {
        return new EnumSearchModel<>(Optional.of(this), attributeName);
    }

    public LocalizedEnumSearchModel<T> ofLocalizableEnum(final String attributeName) {
        return new LocalizedEnumSearchModel<>(Optional.of(this), attributeName);
    }

    public NumberSearchModel<T> ofNumber(final String attributeName) {
        return new NumberSearchModel<>(Optional.of(this), attributeName);
    }

    public MoneySearchModel<T> ofMoney(final String attributeName) {
        return new MoneySearchModel<>(Optional.of(this), attributeName);
    }

    public DateSearchModel<T> ofDate(final String attributeName) {
        return new DateSearchModel<>(Optional.of(this), attributeName);
    }

    public TimeSearchModel<T> ofTime(final String attributeName) {
        return new TimeSearchModel<>(Optional.of(this), attributeName);
    }

    public DateTimeSearchModel<T> ofDateTime(final String attributeName) {
        return new DateTimeSearchModel<>(Optional.of(this), attributeName);
    }
}
