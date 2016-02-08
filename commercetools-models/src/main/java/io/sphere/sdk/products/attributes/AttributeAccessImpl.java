package io.sphere.sdk.products.attributes;

import com.fasterxml.jackson.core.type.TypeReference;
import io.sphere.sdk.models.Base;
import io.sphere.sdk.models.Reference;
import io.sphere.sdk.models.WithKey;

import java.util.Set;
import java.util.function.Predicate;

final class AttributeAccessImpl<T> extends Base implements AttributeAccess<T> {
    private final AttributeMapper<T> attributeMapper;
    private final TypeReference<T> typeReference;
    private final java.util.function.Predicate<AttributeDefinition> canHandle;

    AttributeAccessImpl(final AttributeMapper<T> attributeMapper, final TypeReference<T> typeReference, final Predicate<AttributeDefinition> canHandle) {
        this.attributeMapper = attributeMapper;
        this.typeReference = typeReference;
        this.canHandle = canHandle;
    }

    @Override
    public NamedAttributeAccess<T> ofName(final String name) {
        return new NamedAttributeAccessImpl<>(name, this);
    }

    @Override
    public AttributeMapper<T> attributeMapper() {
        return attributeMapper;
    }

    @Override
    public boolean canHandle(final AttributeDefinition attributeDefinition) {
        return canHandle.test(attributeDefinition);
    }

    static <T extends WithKey> AttributeAccess<T> ofEnumLike(final TypeReference<T> typeReference, final Class<? extends AttributeType> attributeTypeClass) {
        final AttributeMapper<T> mapper = new EnumLikeAttributeMapperImpl<>(typeReference);
        return new AttributeAccessImpl<>(mapper, typeReference, attributeDefinition ->
                attributeTypeClass.isAssignableFrom(attributeDefinition.getAttributeType().getClass())
        );
    }

    static <T> AttributeAccess<T> ofPrimitive(final TypeReference<T> typeReference, final Class<? extends AttributeType> attributeTypeClass) {
        return new AttributeAccessImpl<>(AttributeMapper.of(typeReference), typeReference, attributeDefinition -> attributeTypeClass.isAssignableFrom(attributeDefinition.getAttributeType().getClass()));
    }

    static <T> AttributeAccess<Reference<T>> ofReferenceType(final RichReferenceAttributeType<T> referenceType) {
        final AttributeMapper<Reference<T>> mapper = new ReferenceAttributeMapperImpl<>(referenceType.typeReference());

        return new AttributeAccessImpl<>(mapper, referenceType.typeReference(),
                attributeDefinition -> {
                    if (attributeDefinition.getAttributeType() instanceof ReferenceAttributeType) {
                        final ReferenceAttributeType attributeType = (ReferenceAttributeType) attributeDefinition.getAttributeType();

                        return attributeType.getReferenceTypeId().equals(referenceType.getReferenceTypeId());
                    } else {
                        return false;
                    }
                });
    }

    static <T> AttributeAccess<Set<T>> ofSet(final Class<? extends AttributeType> typeClass, final TypeReference<Set<T>> typeReference) {
        return ofSet(typeClass, typeReference, AttributeMapper.of(typeReference));
    }

    static <T> AttributeAccess<Set<T>> ofSet(final Class<? extends AttributeType> typeClass, final TypeReference<Set<T>> typeReference, final AttributeMapper<Set<T>> mapper) {
        return new AttributeAccessImpl<>(mapper, typeReference, attributeDefinition -> {
            if (attributeDefinition.getAttributeType() instanceof SetAttributeType) {
                final SetAttributeType attributeType = (SetAttributeType) attributeDefinition.getAttributeType();

                return typeClass.isAssignableFrom(attributeType.getElementType().getClass());
            } else {
                return false;
            }
        });
    }

    static <T> AttributeAccess<Set<Reference<T>>> ofSet(final ReferenceAttributeType requiredReferenceType, final TypeReference<Set<Reference<T>>> typeReference) {
        final AttributeMapper<Set<Reference<T>>> mapper = AttributeMapper.of(typeReference);
        return new AttributeAccessImpl<>(mapper, typeReference, new ReferenceSetAttributeDefinitionPredicate(requiredReferenceType));
    }

    static <T extends WithKey> AttributeAccess<Set<T>> ofEnumLikeSet(final Class<? extends AttributeType> clazz,
                                                             final TypeReference<Set<T>> typeReference) {
        final AttributeMapper<Set<T>> mapper = new EnumLikeSetAttributeMapperImpl<>(typeReference);
        return new AttributeAccessImpl<>(mapper, typeReference, attributeDefinition -> {
            if (attributeDefinition.getAttributeType() instanceof SetAttributeType) {
                final SetAttributeType attributeType = (SetAttributeType) attributeDefinition.getAttributeType();

                return clazz.isAssignableFrom(attributeType.getElementType().getClass());
            } else {
                return false;
            }
        });
    }

    private TypeReference<T> attributeTypeReference() {
        return typeReference;
    }

    private static class ReferenceSetAttributeDefinitionPredicate implements Predicate<AttributeDefinition> {
        private final ReferenceAttributeType requiredReferenceType;

        public ReferenceSetAttributeDefinitionPredicate(final ReferenceAttributeType requiredReferenceType) {
            this.requiredReferenceType = requiredReferenceType;
        }

        @Override
        public boolean test(final AttributeDefinition attributeDefinition) {
            final boolean canHandle;

            if (attributeDefinition.getAttributeType() instanceof SetAttributeType) {
                final SetAttributeType attributeType = (SetAttributeType) attributeDefinition.getAttributeType();

                if (attributeType.getElementType() instanceof ReferenceAttributeType) {
                    final ReferenceAttributeType referenceType = (ReferenceAttributeType) attributeType.getElementType();

                    canHandle = referenceType.getReferenceTypeId().equals(requiredReferenceType.getReferenceTypeId());
                } else {
                    canHandle = false;
                }
            } else {
                canHandle = false;
            }

            return canHandle;
        }
    }
}
