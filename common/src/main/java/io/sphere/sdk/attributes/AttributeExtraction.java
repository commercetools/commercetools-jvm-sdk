package io.sphere.sdk.attributes;

import io.sphere.sdk.models.Base;

import java.util.Optional;
import java.util.function.Function;

public final class AttributeExtraction<T> extends Base {
    private final AttributeDefinition attributeDefinition;
    private final Attribute attribute;
    private final Optional<T> value;

    public AttributeExtraction(final AttributeDefinition attributeDefinition, final Attribute attribute, final Optional<T> value) {
        this.attributeDefinition = attributeDefinition;
        this.attribute = attribute;
        this.value = value;
    }

    public static <T> AttributeExtraction<T> of(final AttributeDefinition attributeDefinition, final Attribute attribute) {
        return of(attributeDefinition, attribute, Optional.empty());
    }

    private static <T> AttributeExtraction<T> of(final AttributeDefinition attributeDefinition, final Attribute attribute, final Optional<T> value) {
        return new AttributeExtraction<>(attributeDefinition, attribute, value);
    }

    private AttributeExtraction<T> withValue(final Optional<T> value) {
        return of(attributeDefinition, attribute, value);
    }

    public <I> AttributeExtraction<T> ifGuarded(final TypeSafeAttributeAccess<I> extraction, final Function<I, Optional<T>> function) {
        return value.map(x -> this).orElseGet(() -> {
            final Optional<T> transformed = calculateValue(extraction).flatMap(value -> function.apply(value));
            return withValue(transformed);
        });
    }

    public <I> AttributeExtraction<T> ifIs(final TypeSafeAttributeAccess<I> extraction, final Function<I, T> function) {
        return ifIs(extraction, function, x -> true);
    }

    private <I> Optional<I> calculateValue(final TypeSafeAttributeAccess<I> extraction) {
        Optional<I> valueOption = Optional.empty();
        if (extraction.canHandle(attributeDefinition)) {
            final AttributeMapper<I> mapper = extraction.attributeMapper();
            final I attributeValue = attribute.getValue(mapper);
            valueOption = Optional.of(attributeValue);
        }
        return valueOption;
    }

    public <I> AttributeExtraction<T> ifIs(final TypeSafeAttributeAccess<I> extraction, final Function<I, T> function, final java.util.function.Predicate<I> guard) {
        return value.map(x -> this).orElseGet(() -> {
            final Optional<T> newValue = calculateValue(extraction).flatMap(attributeValue -> {
                Optional<T> mappedValue = Optional.empty();
                if (guard.test(attributeValue)) {
                    mappedValue = Optional.of(function.apply(attributeValue));
                }
                return mappedValue;
            });
            return withValue(newValue);
        });
    }

    public Optional<T> getValue() {
        return value;
    }
}
