package io.sphere.sdk.attributes;

import io.sphere.sdk.models.Base;
import io.sphere.sdk.producttypes.AttributeDefinitionContainer;

import javax.annotation.Nullable;
import java.util.Optional;
import java.util.function.Function;

public final class AttributeExtraction<T> extends Base {
    private final Optional<AttributeDefinition> attributeDefinitionOption;
    private final Attribute attribute;
    private @Nullable final T value;

    private AttributeExtraction(final Optional<AttributeDefinition> attributeDefinitionOption, final Attribute attribute, @Nullable final T value) {
        this.attributeDefinitionOption = attributeDefinitionOption;
        this.attribute = attribute;
        this.value = value;
    }

    public static <T> AttributeExtraction<T> of(final AttributeDefinitionContainer productType, final Attribute attribute) {
        final Optional<AttributeDefinition> attributeDefinition = productType.getAttribute(attribute.getName());
        return of(attributeDefinition, attribute, null);
    }

    public static <T> AttributeExtraction<T> of(final AttributeDefinition attributeDefinition, final Attribute attribute) {
        return of(Optional.of(attributeDefinition), attribute, null);
    }

    private static <T> AttributeExtraction<T> of(final Optional<AttributeDefinition> attributeDefinition, final Attribute attribute, @Nullable final T value) {
        return new AttributeExtraction<>(attributeDefinition, attribute, value);
    }

    private AttributeExtraction<T> withValue(@Nullable final T value) {
        return of(attributeDefinitionOption, attribute, value);
    }

    public <I> AttributeExtraction<T> ifGuarded(final AttributeAccess<I> extraction, final Function<I, Optional<T>> function) {
        return Optional.ofNullable(value).map(x -> this).orElseGet(() -> {
            final T transformed = calculateValue(extraction).flatMap(value -> function.apply(value)).orElse(null);
            return withValue(transformed);
        });
    }

    public <I> AttributeExtraction<T> ifIs(final AttributeAccess<I> extraction, final Function<I, T> function) {
        return ifIs(extraction, function, x -> true);
    }

    @SuppressWarnings("unchecked")
    private <A> Optional<A> calculateValue(final AttributeAccess<A> extraction) {
        return attributeDefinitionOption.flatMap(attributeDefinition -> calculateValue(extraction, attributeDefinition));
    }

    private <A> Optional<A> calculateValue(final AttributeAccess<A> extraction, final AttributeDefinition attributeDefinition) {
        if (extraction.canHandle(attributeDefinition)) {
            final A value = attribute.getValue(extraction);
            return Optional.of(value);
        } else {
            return Optional.empty();
        }
    }

    public <A> AttributeExtraction<T> ifIs(final AttributeAccess<A> extraction, final Function<A, T> function, final java.util.function.Predicate<A> guard) {
        return Optional.ofNullable(value).map(x -> this).orElseGet(() -> {
            final T newValue = calculateValue(extraction).flatMap(attributeValue -> {
                Optional<T> mappedValue = Optional.empty();
                if (guard.test(attributeValue)) {
                    mappedValue = Optional.of(function.apply(attributeValue));
                }
                return mappedValue;
            }).orElse(null);
            return withValue(newValue);
        });
    }

    public Optional<T> findValue() {
        return Optional.ofNullable(getValue());
    }

    public T getValue() {
        return value;
    }
}
