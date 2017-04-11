package io.sphere.sdk.cartdiscounts;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.sphere.sdk.models.LocalizedString;

import javax.annotation.Nullable;
import java.time.ZonedDateTime;

public final class CartDiscountDraftDsl extends CartDiscountDraftDslBase<CartDiscountDraftDsl> {

    @JsonCreator
    CartDiscountDraftDsl(@JsonProperty("isActive") final Boolean active, final String cartPredicate, @Nullable final LocalizedString description, final LocalizedString name, @JsonProperty("requiresDiscountCode") final Boolean requiresDiscountCode, final String sortOrder, final CartDiscountTarget target, @Nullable final ZonedDateTime validFrom, @Nullable final ZonedDateTime validUntil, final CartDiscountValue value) {
        super(active, cartPredicate, description, name, requiresDiscountCode, sortOrder, target, validFrom, validUntil, value);
    }

    /**
     * Sets the requiresDiscountCode field to the given value.
     *
     * @param requiresDiscountCode the requiresDiscountCode value
     *
     * @return this object with the {@link #isRequiresDiscountCode()} ()} field set to the given value
     * @deprecated please use {@link #withRequiresDiscountCode(Boolean)} instead
     */
    @Deprecated
    public CartDiscountDraftDsl withIsRequiresDiscountCode(final Boolean requiresDiscountCode) {
        return super.withRequiresDiscountCode(requiresDiscountCode);
    }
}
