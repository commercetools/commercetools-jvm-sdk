package io.sphere.sdk.attributes;

import com.fasterxml.jackson.core.type.TypeReference;
import io.sphere.sdk.categories.Category;
import io.sphere.sdk.channels.Channel;
import io.sphere.sdk.models.*;
import io.sphere.sdk.products.Product;
import io.sphere.sdk.producttypes.ProductType;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Set;
import java.util.function.Predicate;

import static io.sphere.sdk.models.TypeReferences.*;

public final class TypeSafeAttributeAccess<T> extends Base {
    private final AttributeMapper<T> attributeMapper;
    private final java.util.function.Predicate<AttributeDefinition> canHandle;


    private TypeSafeAttributeAccess(final AttributeMapper<T> attributeMapper, final Predicate<AttributeDefinition> canHandle) {
        this.attributeMapper = attributeMapper;
        this.canHandle = canHandle;
    }

    public static TypeSafeAttributeAccess<Boolean> ofBoolean() {
        return ofPrimitive(booleanTypeReference(), BooleanAttributeDefinition.class);
    }

    public static TypeSafeAttributeAccess<Set<Boolean>> ofBooleanSet() {
        return ofSet(BooleanType.class, new TypeReference<Set<Boolean>>() {
        });
    }

    public static TypeSafeAttributeAccess<String> ofString() {
        return ofPrimitive(stringTypeReference(), TextAttributeDefinition.class);
    }

    public static TypeSafeAttributeAccess<Set<String>> ofStringSet() {
        return ofSet(TextType.class, new TypeReference<Set<String>>() {
        });
    }

    public static TypeSafeAttributeAccess<String> ofText() {
        return ofString();
    }

    public static TypeSafeAttributeAccess<Set<String>> ofTextSet() {
        return ofStringSet();
    }

    public static TypeSafeAttributeAccess<LocalizedString> ofLocalizedString() {
        return ofPrimitive(LocalizedString.typeReference(), LocalizedTextAttributeDefinition.class);
    }

    public static TypeSafeAttributeAccess<Set<LocalizedString>> ofLocalizedStringSet() {
        return ofSet(LocalizedTextType.class, new TypeReference<Set<LocalizedString>>() {});
    }

    public static TypeSafeAttributeAccess<PlainEnumValue> ofPlainEnumValue() {
        return ofEnumLike(PlainEnumValue.typeReference(), EnumAttributeDefinition.class);
    }

    public static TypeSafeAttributeAccess<Set<PlainEnumValue>> ofPlainEnumValueSet() {
        return ofSet(EnumType.class, new TypeReference<Set<PlainEnumValue>>() {});
    }

    public static TypeSafeAttributeAccess<LocalizedEnumValue> ofLocalizedEnumValue() {
        return ofEnumLike(LocalizedEnumValue.typeReference(), LocalizedEnumAttributeDefinition.class);
    }

    public static TypeSafeAttributeAccess<Set<LocalizedEnumValue>> ofLocalizedEnumValueSet() {
        return ofSet(LocalizedEnumType.class, new TypeReference<Set<LocalizedEnumValue>>() {});
    }

    public static TypeSafeAttributeAccess<Double> ofDouble() {
        return ofPrimitive(doubleTypeReference(), NumberAttributeDefinition.class);
    }

    public static TypeSafeAttributeAccess<Set<Double>> ofDoubleSet() {
        return ofSet(NumberType.class, new TypeReference<Set<Double>>() {});
    }

    public static TypeSafeAttributeAccess<Money> ofMoney() {
        return ofPrimitive(Money.typeReference(), MoneyAttributeDefinition.class);
    }

    public static TypeSafeAttributeAccess<Set<Money>> ofMoneySet() {
        return ofSet(MoneyType.class, new TypeReference<Set<Money>>() {});
    }

    public static TypeSafeAttributeAccess<LocalDate> ofDate() {
        return ofPrimitive(localDateTypeReference(), DateAttributeDefinition.class);
    }

    public static TypeSafeAttributeAccess<Set<LocalDate>> ofDateSet() {
        return ofSet(DateType.class, new TypeReference<Set<LocalDate>>() {});
    }

    public static TypeSafeAttributeAccess<LocalTime> ofTime() {
        return ofPrimitive(localTimeTypeReference(), TimeAttributeDefinition.class);
    }

    public static TypeSafeAttributeAccess<Set<LocalTime>> ofTimeSet() {
        return ofSet(TimeType.class, new TypeReference<Set<LocalTime>>() {
        });
    }

    public static TypeSafeAttributeAccess<Instant> ofDateTime() {
        return ofPrimitive(instantTypeReference(), DateTimeAttributeDefinition.class);
    }

    public static TypeSafeAttributeAccess<Set<Instant>> ofDateTimeSet() {
        return ofSet(DateTimeType.class, new TypeReference<Set<Instant>>() {
        });
    }

    public static TypeSafeAttributeAccess<Reference<Product>> ofProductReference() {
        return OfReferenceType(ReferenceType.ofProduct());
    }

    public static TypeSafeAttributeAccess<Set<Reference<Product>>> ofProductReferenceSet() {
        return ofSet(ReferenceType.ofProduct(), new TypeReference<Set<Reference<Product>>>() {
        });
    }

    public static TypeSafeAttributeAccess<Reference<ProductType>> ofProductTypeReference() {
        return OfReferenceType(ReferenceType.ofProductType());
    }

    public static TypeSafeAttributeAccess<Set<Reference<ProductType>>> ofProductTypeReferenceSet() {
        return ofSet(ReferenceType.ofProductType(), new TypeReference<Set<Reference<ProductType>>>() {
        });
    }

    public static TypeSafeAttributeAccess<Reference<Category>> ofCategoryReference() {
        return OfReferenceType(ReferenceType.ofCategory());
    }

    public static TypeSafeAttributeAccess<Set<Reference<Category>>> ofCategoryReferenceSet() {
        return ofSet(ReferenceType.ofCategory(), new TypeReference<Set<Reference<Category>>>() {
        });
    }

    public static TypeSafeAttributeAccess<Reference<Channel>> ofChannelReference() {
        return OfReferenceType(ReferenceType.ofChannel());
    }

    public static TypeSafeAttributeAccess<Set<Reference<Channel>>> ofChannelReferenceSet() {
        return ofSet(ReferenceType.ofChannel(), new TypeReference<Set<Reference<Channel>>>() {
        });
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

    private static <T extends WithKey> TypeSafeAttributeAccess<T> ofEnumLike(final TypeReference<T> typeReference, final Class<? extends AttributeDefinition> attributeDefinitionClass) {
        final AttributeMapper<T> mapper = new EnumLikeAttributeMapperImpl<>(typeReference);
        return new TypeSafeAttributeAccess<>(mapper, attributeDefinition ->
                attributeDefinitionClass.isAssignableFrom(attributeDefinition.getClass())
        );
    }

    private static <T> TypeSafeAttributeAccess<T> ofPrimitive(final TypeReference<T> typeReference, final Class<? extends AttributeDefinition> attributeDefinitionClass) {
        return new TypeSafeAttributeAccess<>(AttributeMapper.of(typeReference), attributeDefinition -> attributeDefinitionClass.isAssignableFrom(attributeDefinition.getClass()));
    }

    private static <T> TypeSafeAttributeAccess<Reference<T>> OfReferenceType(final RichReferenceType<T> referenceType) {
        final AttributeMapper<Reference<T>> mapper = AttributeMapper.of(referenceType.typeReference());
        return new TypeSafeAttributeAccess<>(mapper,
                attributeDefinition -> {
                    boolean canHandle = false;
                    if (ReferenceAttributeDefinition.class.isAssignableFrom(attributeDefinition.getClass())) {
                        ReferenceAttributeDefinition casted = (ReferenceAttributeDefinition) attributeDefinition;
                        canHandle = casted.getAttributeType().getReferenceTypeId().equals(referenceType.getReferenceTypeId());
                    }
                    return canHandle;
                });
    }

    private static <T> TypeSafeAttributeAccess<Set<T>> ofSet(final Class<? extends AttributeType> typeClass, final TypeReference<Set<T>> typeReference) {
        final AttributeMapper<Set<T>> mapper = AttributeMapper.of(typeReference);
        return new TypeSafeAttributeAccess<>(mapper, attributeDefinition -> {
            boolean canHandle = false;
            if (SetAttributeDefinition.class.isAssignableFrom(attributeDefinition.getClass())) {
                final SetAttributeDefinition setAttributeDefinition = (SetAttributeDefinition) attributeDefinition;
                final SetType attributeType = setAttributeDefinition.getAttributeType();
                canHandle = typeClass.isAssignableFrom(attributeType.getElementType().getClass());
            }
            return canHandle;
        });
    }

    private static <T> TypeSafeAttributeAccess<Set<Reference<T>>> ofSet(final ReferenceType requiredReferenceType, final TypeReference<Set<Reference<T>>> typeReference) {
        final AttributeMapper<Set<Reference<T>>> mapper = AttributeMapper.of(typeReference);
        return new TypeSafeAttributeAccess<>(mapper, attributeDefinition -> {
            boolean canHandle = false;
            if (SetAttributeDefinition.class.isAssignableFrom(attributeDefinition.getClass())) {
                final SetAttributeDefinition setAttributeDefinition = (SetAttributeDefinition) attributeDefinition;
                final SetType attributeType = setAttributeDefinition.getAttributeType();
                if (attributeType.getElementType() instanceof ReferenceType) {
                    final ReferenceType referenceType = (ReferenceType) attributeType.getElementType();
                    canHandle = referenceType.getReferenceTypeId().equals(requiredReferenceType.getReferenceTypeId());
                }
            }
            return canHandle;
        });
    }
}
