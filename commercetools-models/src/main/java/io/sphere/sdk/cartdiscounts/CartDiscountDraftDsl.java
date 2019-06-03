package io.sphere.sdk.cartdiscounts;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.sphere.sdk.models.LocalizedString;
import io.sphere.sdk.types.CustomFieldsDraft;

import javax.annotation.Nullable;
import java.time.ZonedDateTime;

public final class CartDiscountDraftDsl extends CartDiscountDraftDslBase<CartDiscountDraftDsl> {

    @JsonCreator
    CartDiscountDraftDsl(final @JsonProperty("isActive") @Nullable Boolean active, final String cartPredicate, @Nullable final CustomFieldsDraft custom, final @Nullable LocalizedString description,
                         final String key, final LocalizedString name, final @JsonProperty("requiresDiscountCode") Boolean requiresDiscountCode,
                         final String sortOrder, final StackingMode stackingMode, final @Nullable CartDiscountTarget target, final @Nullable ZonedDateTime validFrom,
                         final @Nullable ZonedDateTime validUntil, final CartDiscountValue value) {
        super(active, cartPredicate,custom, description, key, name, requiresDiscountCode, sortOrder, stackingMode, target, validFrom, validUntil, value);
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
