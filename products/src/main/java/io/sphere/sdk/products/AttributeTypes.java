package io.sphere.sdk.products;

import com.fasterxml.jackson.core.type.TypeReference;
import io.sphere.sdk.models.Base;
import io.sphere.sdk.models.LocalizedString;
import io.sphere.sdk.models.Money;
import io.sphere.sdk.producttypes.attributes.*;

import java.util.function.Predicate;

import static io.sphere.sdk.models.TypeReferences.*;

//TODO rename, it is ambigious with AttributeType
public final class AttributeTypes<T> extends Base {
    private final AttributeMapper<T> attributeMapper;
    private final java.util.function.Predicate<AttributeDefinition> canHandle;


    private AttributeTypes(final AttributeMapper<T> attributeMapper, final Predicate<AttributeDefinition> canHandle) {
        this.attributeMapper = attributeMapper;
        this.canHandle = canHandle;
    }

    private static <T> AttributeTypes<T> ofPrimitive(final TypeReference<T> typeReference, final Class<? extends AttributeDefinition> attributeDefinitionClass) {
        return new AttributeTypes<>(AttributeMapper.of(typeReference), attributeDefinition -> attributeDefinitionClass.isAssignableFrom(attributeDefinition.getClass()));
    }

    public static AttributeTypes<LocalizedString> ofLocalizedString() {
        return ofPrimitive(LocalizedString.typeReference(), LocalizedTextAttributeDefinition.class);
    }

    public static AttributeTypes<Money> ofMoney() {
        return ofPrimitive(Money.typeReference(), MoneyAttributeDefinition.class);
    }

    public static AttributeTypes<String> ofString() {
        return ofPrimitive(stringTypeReference(), TextAttributeDefinition.class);
    }


    public static AttributeTypes<Double> ofDouble() {
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
