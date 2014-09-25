package io.sphere.sdk.attributes;

import com.fasterxml.jackson.core.type.TypeReference;
import io.sphere.sdk.models.Base;
import io.sphere.sdk.models.LocalizedString;
import io.sphere.sdk.models.Money;

import java.util.function.Predicate;

import static io.sphere.sdk.models.TypeReferences.*;

public final class TypeSafeAttributeAccess<T> extends Base {
    private final AttributeMapper<T> attributeMapper;
    private final java.util.function.Predicate<AttributeDefinition> canHandle;


    private TypeSafeAttributeAccess(final AttributeMapper<T> attributeMapper, final Predicate<AttributeDefinition> canHandle) {
        this.attributeMapper = attributeMapper;
        this.canHandle = canHandle;
    }

    private static <T> TypeSafeAttributeAccess<T> ofPrimitive(final TypeReference<T> typeReference, final Class<? extends AttributeDefinition> attributeDefinitionClass) {
        return new TypeSafeAttributeAccess<>(AttributeMapper.of(typeReference), attributeDefinition -> attributeDefinitionClass.isAssignableFrom(attributeDefinition.getClass()));
    }

    public static TypeSafeAttributeAccess<LocalizedString> ofLocalizedString() {
        return ofPrimitive(LocalizedString.typeReference(), LocalizedTextAttributeDefinition.class);
    }

    public static TypeSafeAttributeAccess<Money> ofMoney() {
        return ofPrimitive(Money.typeReference(), MoneyAttributeDefinition.class);
    }

    public static TypeSafeAttributeAccess<String> ofString() {
        return ofPrimitive(stringTypeReference(), TextAttributeDefinition.class);
    }


    public static TypeSafeAttributeAccess<Double> ofDouble() {
        return ofPrimitive(doubleTypeReference(), NumberAttributeDefinition.class);
    }

    public <M> AttributeGetterSetter<M, T> getterSetter(final String name) {
        return AttributeGetterSetter.of(name, attributeMapper);
    }

    public <M> AttributeGetter<M, T> getter(final String name) {
        return this.<M>getterSetter(name);
    }

    public <M> AttributeSetter<M, T> setter(final String name) {
        return this.<M>getterSetter(name);
    }

    public AttributeMapper<T> attributeMapper() {
        return attributeMapper;
    }

    public boolean canHandle(final AttributeDefinition attributeDefinition) {
        return canHandle.test(attributeDefinition);
    }
}
