package io.sphere.sdk.cartdiscounts;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.sphere.sdk.annotations.FactoryMethod;
import io.sphere.sdk.annotations.ResourceDraftValue;
import io.sphere.sdk.models.LocalizedString;

import javax.annotation.Nullable;
import java.time.ZonedDateTime;

/**
 * Draft to create a {@link CartDiscount}.
 * @see CartDiscountDraftBuilder
 */
@JsonDeserialize(as = CartDiscountDraftDsl.class)
@ResourceDraftValue(
        factoryMethods = @FactoryMethod(parameterNames = {"name", "cartPredicate", "value", "target", "sortOrder", "requiresDiscountCode"},
        useLowercaseBooleans = true),
        abstractResourceDraftValueClass = true,
        abstractBuilderClass = true)
public interface CartDiscountDraft {
    String getCartPredicate();

    @Nullable
    LocalizedString getDescription();

    @Nullable
    @JsonProperty("isActive")
    Boolean isActive();

    LocalizedString getName();

    @JsonProperty("requiresDiscountCode")
    Boolean isRequiresDiscountCode();

    String getSortOrder();

    @Nullable
    CartDiscountTarget getTarget();

    @Nullable
    ZonedDateTime getValidFrom();

    @Nullable
    ZonedDateTime getValidUntil();

    CartDiscountValue getValue();
}
