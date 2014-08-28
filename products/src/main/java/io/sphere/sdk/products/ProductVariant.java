package io.sphere.sdk.products;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.sphere.sdk.models.AttributeAccessor;

import java.util.List;
import java.util.Optional;

/**
 * <h2>Type-safe attribute access</h2>
 *
 * Use {@link io.sphere.sdk.products.ProductVariant#getAttribute(io.sphere.sdk.models.AttributeAccessor)} to access attributes where you know the name and the type.
 *
 *
 *
 *
 *
 * <h3>Declare attribute description by providing a static method</h3>
 * <p>This style is useful if you have a few product types.</p>
 *
 * <p>The declaration:</p>
 *
 * {@include.example io.sphere.sdk.products.attributeaccess.staticmethodstyle.TShirt}
 *
 * <p>The usage:</p>
 *
 * {@include.example io.sphere.sdk.products.attributeaccess.staticmethodstyle.Usage#demo()}
 *
 *
 *
 *
 *
 *
 *
 *
 * <h3>Declare attribute description by providing a static field</h3>
 * <p>This style is useful if you have a few product types.</p>
 *
 * <p>The declaration:</p>
 *
 * {@include.example io.sphere.sdk.products.attributeaccess.staticattributestyle.TShirt}
 *
 * <p>The usage:</p>
 *
 * {@include.example io.sphere.sdk.products.attributeaccess.staticattributestyle.Usage#demo()}
 *
 *
 *
 *
 *
 *
 *
 *
 * <h3>Declare attribute description by providing interfaces and static accessors</h3>
 * <p>This is useful if you have a hierarchy of product types.</p>
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
 * <p>The usage:</p>
 *
 * {@include.example io.sphere.sdk.products.attributeaccess.interfacesstaticmethodstyle.Usage#demo()}
 */
@JsonDeserialize(as = ProductVariantImpl.class)
public interface ProductVariant {

    long getId();

    Optional<String> getSku();

    List<Price> getPrices();

    List<Attribute> getAttributes();

    <T> Optional<T> getAttribute(final AttributeAccessor<Product, T> c);

    //TODO images

    //TODO availability
}
