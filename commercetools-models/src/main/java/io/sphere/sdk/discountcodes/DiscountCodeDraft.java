package io.sphere.sdk.discountcodes;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.sphere.sdk.annotations.FactoryMethod;
import io.sphere.sdk.annotations.IgnoreInQueryModel;
import io.sphere.sdk.annotations.ResourceDraftValue;
import io.sphere.sdk.cartdiscounts.CartDiscount;
import io.sphere.sdk.models.LocalizedString;
import io.sphere.sdk.models.Reference;
import io.sphere.sdk.models.Referenceable;
import io.sphere.sdk.types.CustomFieldsDraft;

import javax.annotation.Nullable;
import java.time.ZonedDateTime;
import java.util.Collections;
import java.util.List;

/**
 * @see DiscountCodeDraftBuilder
 * @see DiscountCodeDraftDsl
 */
@JsonDeserialize(as = DiscountCodeDraftDsl.class)
@ResourceDraftValue(
        abstractBuilderClass = true,
        abstractResourceDraftValueClass = true,
        factoryMethods = {@FactoryMethod(parameterNames = {"code", "cartDiscounts"})})
public interface DiscountCodeDraft {
    @Nullable
    LocalizedString getName();

    @Nullable
    LocalizedString getDescription();

    String getCode();

    List<Reference<CartDiscount>> getCartDiscounts();

    @Nullable
    String getCartPredicate();

    @JsonProperty("isActive")
    Boolean isActive();

    @Nullable
    List<String> getGroups();

    @Nullable
    @IgnoreInQueryModel
    ZonedDateTime getValidFrom();

    @Nullable
    @IgnoreInQueryModel
    ZonedDateTime getValidUntil();

    @Nullable
    Long getMaxApplications();

    @Nullable
    Long getMaxApplicationsPerCustomer();

    @Nullable
    CustomFieldsDraft getCustom();

    static DiscountCodeDraftDsl of(final String code, final Referenceable<CartDiscount> cartDiscount) {
        return DiscountCodeDraft.of(code, Collections.singletonList(cartDiscount.toReference()));
    }

    static DiscountCodeDraftDsl of(final String code, final List<Reference<CartDiscount>> cartDiscounts) {
        return DiscountCodeDraftDsl.of(code, cartDiscounts);
    }
}
