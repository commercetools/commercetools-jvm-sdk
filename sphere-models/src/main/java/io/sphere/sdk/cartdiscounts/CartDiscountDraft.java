package io.sphere.sdk.cartdiscounts;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.sphere.sdk.models.LocalizedString;

import javax.annotation.Nullable;
import java.time.ZonedDateTime;

/**
 * Draft to create a {@link CartDiscount}.
 * @see CartDiscountDraftBuilder
 */
@JsonDeserialize(as = CartDiscountDraftImpl.class)
public interface CartDiscountDraft {
    String getCartPredicate();

    @Nullable
    LocalizedString getDescription();

    Boolean isActive();

    LocalizedString getName();

    Boolean isRequiresDiscountCode();

    String getSortOrder();

    CartDiscountTarget getTarget();

    @Nullable
    ZonedDateTime getValidFrom();

    @Nullable
    ZonedDateTime getValidUntil();

    CartDiscountValue getValue();
}
