package io.sphere.sdk.discountcodes;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.sphere.sdk.models.Reference;
import io.sphere.sdk.models.Referenceable;

import java.util.Optional;

@JsonDeserialize(as = DiscountCodeInfoImpl.class)
public interface DiscountCodeInfo {
    Reference<DiscountCode> getDiscountCode();

    DiscountCodeState getState();

    static DiscountCodeInfo of(final Referenceable<DiscountCode> discountCode, final DiscountCodeState state) {
        final Reference<DiscountCode> discountCodeReference =
                Optional.ofNullable(discountCode).map(d -> d.toReference()).orElse(null);
        return new DiscountCodeInfoImpl(discountCodeReference, state);
    }
}
