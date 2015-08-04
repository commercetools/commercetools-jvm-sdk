package io.sphere.sdk.products.attributes;

/**
 * <h2>Type-safe attribute access</h2>
 *
 * <h3 id="access-with-static-methods">Style A: Declare attribute description by providing a static method</h3>
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
 * <h3 id="access-with-static-fields">Style B: Declare attribute description by providing a static field</h3>
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
 * <h3 id="access-with-interface-default-methods">Style C: Declare attribute description by providing interfaces and static accessors</h3>
 * <p>This is useful if you have a hierarchy of product types. It uses default implementations for interfaces to simulate multiple inheritance.</p>
 *
 *
 * <p>The usage:</p>
 *
 * {@include.example io.sphere.sdk.products.attributeaccess.interfacesstaticmethodstyle.Usage#demo()}
 *
 * <p>The declaration:</p>
 *
 * <p>Create groups of attributes:</p>
 * {@include.example io.sphere.sdk.products.attributeaccess.interfacesstaticmethodstyle.WithLongDescription}
 * {@include.example io.sphere.sdk.products.attributeaccess.interfacesstaticmethodstyle.WithColor}
 * {@include.example io.sphere.sdk.products.attributeaccess.interfacesstaticmethodstyle.WithPromoMoney}
 *
 * <p>Mixin the the attributes:</p>
 * {@include.example io.sphere.sdk.products.attributeaccess.interfacesstaticmethodstyle.TShirt}
 *
 */
public interface NamedAttributeAccess<T> extends AttributeAccess<T> {
    String getName();

    Attribute valueOf(final T input);

    AttributeDraft draftOf(T value);
}
