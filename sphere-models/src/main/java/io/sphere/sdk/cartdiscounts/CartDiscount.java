package io.sphere.sdk.cartdiscounts;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.sphere.sdk.models.Resource;
import io.sphere.sdk.models.LocalizedString;
import io.sphere.sdk.models.Reference;

import javax.annotation.Nullable;
import java.time.ZonedDateTime;
import java.util.List;

@JsonDeserialize(as=CartDiscountImpl.class)
public interface CartDiscount extends Resource<CartDiscount> {
    String getCartPredicate();

    @Nullable
    LocalizedString getDescription();

    Boolean isActive();

    LocalizedString getName();

    List<Reference<JsonNode>> getReferences();

    Boolean isRequiringDiscountCode();

    String getSortOrder();

    CartDiscountTarget getTarget();

    @Nullable
    ZonedDateTime getValidFrom();

    @Nullable
    ZonedDateTime getValidUntil();

    CartDiscountValue getValue();

    static String referenceTypeId() {
        return "cart-discount";
    }

    /**
     *
     * @deprecated use {@link #referenceTypeId()} instead
     * @return referenceTypeId
     */
    @Deprecated
    static String typeId() {
        return "cart-discount";
    }

    @Override
    default Reference<CartDiscount> toReference() {
        return Reference.of(referenceTypeId(), getId(), this);
    }

    static TypeReference<CartDiscount> typeReference() {
        return new TypeReference<CartDiscount>() {
            @Override
            public String toString() {
                return "TypeReference<CartDiscount>";
            }
        };
    }
}
