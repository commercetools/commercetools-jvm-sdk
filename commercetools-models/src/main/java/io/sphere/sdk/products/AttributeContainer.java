package io.sphere.sdk.products;

import io.sphere.sdk.products.attributes.Attribute;
import io.sphere.sdk.products.attributes.AttributeAccess;
import io.sphere.sdk.products.attributes.NamedAttributeAccess;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Optional;

import static java.util.Objects.requireNonNull;

/**
 * A container for attributes. This can be either a product variant or nested attribute.
 */
public interface AttributeContainer {
    List<Attribute> getAttributes();

    /**
     * Access one attribute of a specific name and type which is known in the first place, consult {@link NamedAttributeAccess} how to implement these.
     *
     * @throws io.sphere.sdk.json.JsonException if the type of attribute cannot be parsed
     *
     * @param accessor declaration of the name and type of the attribute
     * @param <T> the underlying type of the attribute
     * @return the value of the attribute, or Optional.empty if absent
     */
    <T> Optional<T> findAttribute(final NamedAttributeAccess<T> accessor);

    default <T> Optional<T> findAttribute(final String name, final AttributeAccess<T> accessor) {
        return findAttribute(accessor.ofName(name));
    }

    default Optional<Attribute> findAttribute(final String attributeName) {
        return Optional.ofNullable(getAttribute(attributeName));
    }

    default boolean hasAttribute(final String attributeName) {
        return getAttributes().stream().anyMatch(attr -> attr.getName().equals(attributeName));
    }

    default boolean hasAttribute(final NamedAttributeAccess<?> namedAccess) {
        return getAttributes().stream().anyMatch(attr -> attr.getName().equals(namedAccess.getName()));
    }

    @Nullable
    default Attribute getAttribute(final String attributeName) {
        requireNonNull(attributeName);
        return getAttributes().stream().filter(attr -> attr.getName().equals(attributeName)).findAny().orElse(null);
    }

    static AttributeContainer of(final List<Attribute> attributes) {
        return AttributeContainerImpl.of(attributes);
    }
}
