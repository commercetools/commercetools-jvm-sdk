package io.sphere.sdk.discountcodes;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.sphere.sdk.cartdiscounts.CartDiscount;
import io.sphere.sdk.models.DefaultModel;
import io.sphere.sdk.models.LocalizedStrings;
import io.sphere.sdk.models.Reference;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Optional;

@JsonDeserialize(as = DiscountCodeImpl.class)
public interface DiscountCode extends DefaultModel<DiscountCode> {
    List<Reference<CartDiscount>> getCartDiscounts();

    Optional<String> getCartPredicate();

    String getCode();

    @Nullable
    LocalizedStrings getDescription();

    boolean isActive();

    Optional<Long> getMaxApplications();

    Optional<Long> getMaxApplicationsPerCustomer();

    @Nullable
    LocalizedStrings getName();

    List<Reference<Object>> getReferences();

    @Override
    default Reference<DiscountCode> toReference() {
        return Reference.of(typeId(), getId(), this);
    }

    static String typeId(){
        return "discount-code";
    }

    static TypeReference<DiscountCode> typeReference() {
        return new TypeReference<DiscountCode>() {
            @Override
            public String toString() {
                return "TypeReference<DiscountCode>";
            }
        };
    }
}
