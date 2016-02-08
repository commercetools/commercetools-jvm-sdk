package io.sphere.sdk.discountcodes;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.sphere.sdk.cartdiscounts.CartDiscount;
import io.sphere.sdk.cartdiscounts.CartDiscountPredicate;
import io.sphere.sdk.models.Base;
import io.sphere.sdk.models.LocalizedString;
import io.sphere.sdk.models.Reference;
import io.sphere.sdk.models.Referenceable;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;

/**
 * @see DiscountCodeDraftBuilder
 * @see DiscountCodeDraft
 */
public final class DiscountCodeDraftDsl extends Base implements DiscountCodeDraft {
    @Nullable
    private final LocalizedString name;
    @Nullable
    private final LocalizedString description;
    private final String code;
    private final List<Reference<CartDiscount>> cartDiscounts;
    @Nullable
    private final String cartPredicate;
    private final Boolean isActive;
    @Nullable
    private final Long maxApplications;
    @Nullable
    private final Long maxApplicationsPerCustomer;

    DiscountCodeDraftDsl(final List<Reference<CartDiscount>> cartDiscounts, @Nullable final LocalizedString name, @Nullable final LocalizedString description, final String code, @Nullable final String cartPredicate, final Boolean isActive, @Nullable final Long maxApplications, @Nullable final Long maxApplicationsPerCustomer) {
        this.cartDiscounts = cartDiscounts;
        this.name = name;
        this.description = description;
        this.code = code;
        this.cartPredicate = cartPredicate;
        this.isActive = isActive;
        this.maxApplications = maxApplications;
        this.maxApplicationsPerCustomer = maxApplicationsPerCustomer;
    }

    public static DiscountCodeDraftDsl of(final String code, final Referenceable<CartDiscount> cartDiscount) {
        return of(code, Collections.singletonList(cartDiscount.toReference()));
    }

    public static DiscountCodeDraftDsl of(final String code, final List<Reference<CartDiscount>> cartDiscounts) {
        return DiscountCodeDraftBuilder.of(code, cartDiscounts).build();
    }

    public DiscountCodeDraftDsl withName(@Nullable final LocalizedString name) {
        return newBuilder().name(name).build();
    }

    @Override
    @Nullable
    public LocalizedString getName() {
        return name;
    }

    public DiscountCodeDraftDsl withDescription(@Nullable final LocalizedString description) {
        return newBuilder().description(description).build();
    }

    @Override
    @Nullable
    public LocalizedString getDescription() {
        return description;
    }

    public DiscountCodeDraftDsl withCode(final String code) {
        return newBuilder().code(code).build();
    }

    @Override
    public String getCode() {
        return code;
    }

    public DiscountCodeDraftDsl withCartDiscounts(final Referenceable<CartDiscount> cartDiscount) {
        return withCartDiscounts(Collections.singletonList(cartDiscount.toReference()));
    }

    public DiscountCodeDraftDsl withCartDiscounts(final List<Reference<CartDiscount>> cartDiscounts) {
        return newBuilder().cartDiscounts(cartDiscounts).build();
    }

    @Override
    public List<Reference<CartDiscount>> getCartDiscounts() {
        return cartDiscounts;
    }


    public DiscountCodeDraftDsl withCartPredicate(@Nullable final CartDiscountPredicate cartPredicate) {
        return newBuilder().cartPredicate(cartPredicate).build();
    }

    @Override
    @Nullable
    public String getCartPredicate() {
        return cartPredicate;
    }

    public DiscountCodeDraftDsl withIsActive(final Boolean isActive) {
        return newBuilder().isActive(isActive).build();
    }

    @Override
    @JsonProperty("isActive")
    public Boolean isActive() {
        return isActive;
    }

    public DiscountCodeDraftDsl withMaxApplications(@Nullable final Long maxApplications) {
        return newBuilder().maxApplications(maxApplications).build();
    }

    @Override
    @Nullable
    public Long getMaxApplications() {
        return maxApplications;
    }

    public DiscountCodeDraftDsl withMaxApplicationsPerCustomer(@Nullable final Long maxApplicationsPerCustomer) {
        return newBuilder().maxApplicationsPerCustomer(maxApplicationsPerCustomer).build();
    }

    @Override
    @Nullable
    public Long getMaxApplicationsPerCustomer() {
        return maxApplicationsPerCustomer;
    }

    private DiscountCodeDraftBuilder newBuilder() {
        return DiscountCodeDraftBuilder.of(this);
    }
}
