package io.sphere.sdk.models;

/**
 * <h2>Type-safe attribute access</h2>
 *
 * <h3>Style A: Declare attribute description by providing a static method</h3>
 * <p>This style is useful if you have a few product types.</p>
 *
 *
 * <p>The usage:</p>
 *
 * {@include.example io.sphere.sdk.products.attributeaccess.staticmethodstyle.Usage#demo()}
 * <p>The declaration:</p>
 *
 * {@include.example io.sphere.sdk.products.attributeaccess.staticmethodstyle.TShirt}
 *
 *
 *
 *
 *
 * <h3>Style B: Declare attribute description by providing a static field</h3>
 * <p>This style is useful if you have a few product types.</p>
 *
 * <p>The usage:</p>
 *
 * {@include.example io.sphere.sdk.products.attributeaccess.staticattributestyle.Usage#demo()}
 *
 *
 * <p>The declaration:</p>
 *
 * {@include.example io.sphere.sdk.products.attributeaccess.staticattributestyle.TShirt}
 *
 *
 *
 *
 * <h3>Style C: Declare attribute description by providing interfaces and static accessors</h3>
 * <p>This is useful if you have a hierarchy of product types.</p>
 *
 *
 * <p>The usage:</p>
 *
 * {@include.example io.sphere.sdk.products.attributeaccess.interfacesstaticmethodstyle.Usage#demo()}
 *
 * <p>The declaration:</p>
 *
 * <p>Create groups of attributes:</p>
 * {@include.example io.sphere.sdk.products.attributeaccess.WithLongDescription}
 * {@include.example io.sphere.sdk.products.attributeaccess.WithColor}
 * {@include.example io.sphere.sdk.products.attributeaccess.WithPromoMoney}
 *
 * <p>Mixin the the attributes:</p>
 * {@include.example io.sphere.sdk.products.attributeaccess.interfacesstaticmethodstyle.TShirt}
 *
 */
public interface AttributeAccessor<M, T> {
    String getName();

    AttributeMapper<T> getMapper();

    public static <M, T> AttributeAccessor<M, T> of(final String name, final AttributeMapper<T> mapper) {
        return new AttributeAccessorImpl<>(name, mapper);
    }

    public static <M> AttributeAccessor<M, LocalizedString> ofLocalizedString(final String name) {
        return of(name, AttributeMapper.of(LocalizedString.typeReference()));
    }

    public static <M> AttributeAccessor<M, Money> ofMoney(final String name) {
        return of(name, null/* TODO */);
    }

    public static <M> AttributeAccessor<M, String> ofString(final String name) {
        return of(name, null/* TODO */);
    }
}
