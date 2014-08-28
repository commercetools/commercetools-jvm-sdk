package io.sphere.sdk.models;

import io.sphere.sdk.models.LocalizedString;

public interface AttributeAccessor<M, T> {
    String getName();

    AttributeMapper<T> getMapper();

    public static <M, T> AttributeAccessor<M, T> of(final String name, final AttributeMapper<T> mapper) {
        return new AttributeAccessorImpl<>(name, mapper);
    }

    public static <M> AttributeAccessor<M, LocalizedString> ofLocalizedString(final String name) {
        return of(name, AttributeMapper.ofLocalizedString());
    }

    public static <M> AttributeAccessor<M, Money> ofMoney(final String name) {
        return of(name, null/* TODO */);
    }

    public static <M> AttributeAccessor<M, String> ofString(final String name) {
        return of(name, null/* TODO */);
    }
}
