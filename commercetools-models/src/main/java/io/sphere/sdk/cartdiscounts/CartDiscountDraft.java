package io.sphere.sdk.cartdiscounts;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.sphere.sdk.annotations.FactoryMethod;
import io.sphere.sdk.annotations.ResourceDraftValue;
import io.sphere.sdk.models.LocalizedString;
import io.sphere.sdk.models.WithKey;
import io.sphere.sdk.types.CustomDraft;
import io.sphere.sdk.types.CustomFields;
import io.sphere.sdk.types.CustomFieldsDraft;

import javax.annotation.Nullable;
import java.time.ZonedDateTime;

/**
 * Draft to create a {@link CartDiscount}.
 * @see CartDiscountDraftBuilder
 */
@JsonDeserialize(as = CartDiscountDraftDsl.class)
@ResourceDraftValue(
        abstractResourceDraftValueClass = true,
        abstractBuilderClass = true,
        factoryMethods = @FactoryMethod(parameterNames = {"name", "cartPredicate", "value", "target", "sortOrder", "requiresDiscountCode"},
                                        useLowercaseBooleans = true))
public interface CartDiscountDraft extends CustomDraft, WithKey {
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

    @Nullable
    String getKey();

    /**
     * Allow to add {@link CustomFields} to a {@link CartDiscount}
     * @return the {@link CustomFields} defined at this {@link CartDiscountDraft}
     */
    @Nullable
    CustomFieldsDraft getCustom();
    /**
     * Specify whether the application of this discount causes the following discounts to be ignored.
     * Defaults to {@link StackingMode#STACKING}.
     *
     * @return the stacking mode of this object
     */
    @Nullable
    StackingMode getStackingMode();
}
