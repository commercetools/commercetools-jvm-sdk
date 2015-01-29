package io.sphere.sdk.attributes;

import com.fasterxml.jackson.core.type.TypeReference;
import io.sphere.sdk.categories.Category;
import io.sphere.sdk.channels.Channel;
import io.sphere.sdk.models.*;
import io.sphere.sdk.products.Product;
import io.sphere.sdk.producttypes.ProductType;

import javax.money.MonetaryAmount;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Set;
import java.util.function.Predicate;

import static io.sphere.sdk.models.TypeReferences.*;

/**
 *
 * @param <T> the type of the attribute
 * @see io.sphere.sdk.attributes.AttributeGetterSetter
 */
public final class AttributeAccess<T> extends Base {
    private final AttributeMapper<T> attributeMapper;
    private final java.util.function.Predicate<AttributeDefinition> canHandle;


    private AttributeAccess(final AttributeMapper<T> attributeMapper, final Predicate<AttributeDefinition> canHandle) {
        this.attributeMapper = attributeMapper;
        this.canHandle = canHandle;
    }

    public static AttributeAccess<Boolean> ofBoolean() {
        return ofPrimitive(booleanTypeReference(), BooleanAttributeDefinition.class);
    }

    public static AttributeAccess<Set<Boolean>> ofBooleanSet() {
        return ofSet(BooleanType.class, new TypeReference<Set<Boolean>>() {
        });
    }

    public static AttributeAccess<String> ofString() {
        return ofPrimitive(stringTypeReference(), TextAttributeDefinition.class);
    }

    public static AttributeAccess<Set<String>> ofStringSet() {
        return ofSet(TextType.class, new TypeReference<Set<String>>() {
        });
    }

    public static AttributeAccess<String> ofText() {
        return ofString();
    }

    public static AttributeAccess<Set<String>> ofTextSet() {
        return ofStringSet();
    }

    public static AttributeAccess<LocalizedStrings> ofLocalizedStrings() {
        return ofPrimitive(LocalizedStrings.typeReference(), LocalizedTextAttributeDefinition.class);
    }

    public static AttributeAccess<Set<LocalizedStrings>> ofLocalizedStringsSet() {
        return ofSet(LocalizedTextType.class, new TypeReference<Set<LocalizedStrings>>() {});
    }

    public static AttributeAccess<PlainEnumValue> ofPlainEnumValue() {
        return ofEnumLike(PlainEnumValue.typeReference(), EnumAttributeDefinition.class);
    }

    public static AttributeAccess<Set<PlainEnumValue>> ofPlainEnumValueSet() {
        return ofEnumLikeSet(EnumType.class, new TypeReference<Set<PlainEnumValue>>() {});
    }

    public static AttributeAccess<LocalizedEnumValue> ofLocalizedEnumValue() {
        return ofEnumLike(LocalizedEnumValue.typeReference(), LocalizedEnumAttributeDefinition.class);
    }

    public static AttributeAccess<Set<LocalizedEnumValue>> ofLocalizedEnumValueSet() {
        return ofEnumLikeSet(LocalizedEnumType.class, new TypeReference<Set<LocalizedEnumValue>>() {});
    }

    public static AttributeAccess<Double> ofDouble() {
        return ofPrimitive(doubleTypeReference(), NumberAttributeDefinition.class);
    }

    public static AttributeAccess<Set<Double>> ofDoubleSet() {
        return ofSet(NumberType.class, new TypeReference<Set<Double>>() {});
    }

    public static AttributeAccess<MonetaryAmount> ofMoney() {
        return ofPrimitive(monetaryAmountTypeReference(), MoneyAttributeDefinition.class);
    }

    public static AttributeAccess<Set<MonetaryAmount>> ofMoneySet() {
        return ofSet(MoneyType.class, new TypeReference<Set<MonetaryAmount>>() {});
    }

    public static AttributeAccess<LocalDate> ofDate() {
        return ofPrimitive(localDateTypeReference(), DateAttributeDefinition.class);
    }

    public static AttributeAccess<Set<LocalDate>> ofDateSet() {
        return ofSet(DateType.class, new TypeReference<Set<LocalDate>>() {});
    }

    public static AttributeAccess<LocalTime> ofTime() {
        return ofPrimitive(localTimeTypeReference(), TimeAttributeDefinition.class);
    }

    public static AttributeAccess<Set<LocalTime>> ofTimeSet() {
        return ofSet(TimeType.class, new TypeReference<Set<LocalTime>>() {
        });
    }

    public static AttributeAccess<Instant> ofDateTime() {
        return ofPrimitive(instantTypeReference(), DateTimeAttributeDefinition.class);
    }

    public static AttributeAccess<Set<Instant>> ofDateTimeSet() {
        return ofSet(DateTimeType.class, new TypeReference<Set<Instant>>() {
        });
    }

    public static AttributeAccess<Reference<Product>> ofProductReference() {
        return OfReferenceType(ReferenceType.ofProduct());
    }

    public static AttributeAccess<Set<Reference<Product>>> ofProductReferenceSet() {
        return ofSet(ReferenceType.ofProduct(), new TypeReference<Set<Reference<Product>>>() {
        });
    }

    public static AttributeAccess<Reference<ProductType>> ofProductTypeReference() {
        return OfReferenceType(ReferenceType.ofProductType());
    }

    public static AttributeAccess<Set<Reference<ProductType>>> ofProductTypeReferenceSet() {
        return ofSet(ReferenceType.ofProductType(), new TypeReference<Set<Reference<ProductType>>>() {
        });
    }

    public static AttributeAccess<Reference<Category>> ofCategoryReference() {
        return OfReferenceType(ReferenceType.ofCategory());
    }

    public static AttributeAccess<Set<Reference<Category>>> ofCategoryReferenceSet() {
        return ofSet(ReferenceType.ofCategory(), new TypeReference<Set<Reference<Category>>>() {
        });
    }

    public static AttributeAccess<Reference<Channel>> ofChannelReference() {
        return OfReferenceType(ReferenceType.ofChannel());
    }

    public static AttributeAccess<Set<Reference<Channel>>> ofChannelReferenceSet() {
        return ofSet(ReferenceType.ofChannel(), new TypeReference<Set<Reference<Channel>>>() {
        });
    }

    public <M> AttributeGetterSetter<M, T> getterSetter(final String name) {
        return ofName(name);
    }

    public <M> AttributeGetterSetter<M, T> ofName(final String name) {
        return AttributeGetterSetter.of(name, attributeMapper);
    }

    public <M> AttributeGetter<M, T> getter(final String name) {
        return this.<M>ofName(name);
    }

    public <M> AttributeSetter<M, T> setter(final String name) {
        return this.<M>ofName(name);
    }

    public AttributeMapper<T> attributeMapper() {
        return attributeMapper;
    }

    public boolean canHandle(final AttributeDefinition attributeDefinition) {
        return canHandle.test(attributeDefinition);
    }

    private static <T extends WithKey> AttributeAccess<T> ofEnumLike(final TypeReference<T> typeReference, final Class<? extends AttributeDefinition> attributeDefinitionClass) {
        final AttributeMapper<T> mapper = new EnumLikeAttributeMapperImpl<>(typeReference);
        return new AttributeAccess<>(mapper, attributeDefinition ->
                attributeDefinitionClass.isAssignableFrom(attributeDefinition.getClass())
        );
    }

    private static <T> AttributeAccess<T> ofPrimitive(final TypeReference<T> typeReference, final Class<? extends AttributeDefinition> attributeDefinitionClass) {
        return new AttributeAccess<>(AttributeMapper.of(typeReference), attributeDefinition -> attributeDefinitionClass.isAssignableFrom(attributeDefinition.getClass()));
    }

    private static <T> AttributeAccess<Reference<T>> OfReferenceType(final RichReferenceType<T> referenceType) {
        final AttributeMapper<Reference<T>> mapper = new ReferenceAttributeMapperImpl<>(referenceType.typeReference());
        return new AttributeAccess<>(mapper,
                attributeDefinition -> {
                    boolean canHandle = false;
                    if (ReferenceAttributeDefinition.class.isAssignableFrom(attributeDefinition.getClass())) {
                        ReferenceAttributeDefinition casted = (ReferenceAttributeDefinition) attributeDefinition;
                        canHandle = casted.getAttributeType().getReferenceTypeId().equals(referenceType.getReferenceTypeId());
                    }
                    return canHandle;
                });
    }

    private static <T> AttributeAccess<Set<T>> ofSet(final Class<? extends AttributeType> typeClass, final TypeReference<Set<T>> typeReference) {
        final AttributeMapper<Set<T>> mapper = AttributeMapper.of(typeReference);
        return new AttributeAccess<>(mapper, attributeDefinition -> {
            boolean canHandle = false;
            if (SetAttributeDefinition.class.isAssignableFrom(attributeDefinition.getClass())) {
                final SetAttributeDefinition setAttributeDefinition = (SetAttributeDefinition) attributeDefinition;
                final SetType attributeType = setAttributeDefinition.getAttributeType();
                canHandle = typeClass.isAssignableFrom(attributeType.getElementType().getClass());
            }
            return canHandle;
        });
    }

    private static <T> AttributeAccess<Set<Reference<T>>> ofSet(final ReferenceType requiredReferenceType, final TypeReference<Set<Reference<T>>> typeReference) {
        final AttributeMapper<Set<Reference<T>>> mapper = AttributeMapper.of(typeReference);
        return new AttributeAccess<>(mapper, attributeDefinition -> {
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

    private static <T extends WithKey> AttributeAccess<Set<T>> ofEnumLikeSet(final Class<? extends AttributeType> clazz,
                                                             final TypeReference<Set<T>> typeReference) {
        final AttributeMapper<Set<T>> mapper = new EnumLikeSetAttributeMapperImpl<>(typeReference);
        return new AttributeAccess<>(mapper, attributeDefinition -> {
            boolean canHandle1 = false;
            if (SetAttributeDefinition.class.isAssignableFrom(attributeDefinition.getClass())) {
                final SetAttributeDefinition setAttributeDefinition = (SetAttributeDefinition) attributeDefinition;
                final SetType attributeType = setAttributeDefinition.getAttributeType();
                canHandle1 = clazz.isAssignableFrom(attributeType.getElementType().getClass());
            }
            return canHandle1;
        });
    }
}
