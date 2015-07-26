package io.sphere.sdk.discountcodes;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.sphere.sdk.cartdiscounts.CartDiscount;
import io.sphere.sdk.cartdiscounts.CartPredicate;
import io.sphere.sdk.models.Base;
import io.sphere.sdk.models.LocalizedStrings;
import io.sphere.sdk.models.Reference;
import io.sphere.sdk.models.Referenceable;

import javax.annotation.Nullable;
import java.util.List;

import static java.util.Arrays.asList;

/**
 * @see DiscountCodeDraftBuilder
 */
public class DiscountCodeDraft extends Base {
    @Nullable
    private final LocalizedStrings name;
    @Nullable
    private final LocalizedStrings description;
    private final String code;
    private final List<Reference<CartDiscount>> cartDiscounts;
    @Nullable
    private final String cartPredicate;
    private final boolean isActive;
    @Nullable
    private final Long maxApplications;
    @Nullable
    private final Long maxApplicationsPerCustomer;

    DiscountCodeDraft(final List<Reference<CartDiscount>> cartDiscounts, final LocalizedStrings name, final LocalizedStrings description, final String code, final String cartPredicate, final boolean isActive, final Long maxApplications, final Long maxApplicationsPerCustomer) {
        this.cartDiscounts = cartDiscounts;
        this.name = name;
        this.description = description;
        this.code = code;
        this.cartPredicate = cartPredicate;
        this.isActive = isActive;
        this.maxApplications = maxApplications;
        this.maxApplicationsPerCustomer = maxApplicationsPerCustomer;
    }

    public static DiscountCodeDraft of(final String code, final Referenceable<CartDiscount> cartDiscount) {
        return of(code, asList(cartDiscount.toReference()));
    }

    public static DiscountCodeDraft of(final String code, final List<Reference<CartDiscount>> cartDiscounts) {
        return DiscountCodeDraftBuilder.of(code, cartDiscounts).build();
    }

    public DiscountCodeDraft withName(@Nullable final LocalizedStrings name) {
        return newBuilder().name(name).build();
    }

    @Nullable
    public LocalizedStrings getName() {
        return name;
    }

    public DiscountCodeDraft withDescription(@Nullable final LocalizedStrings description) {
        return newBuilder().description(description).build();
    }

    @Nullable
    public LocalizedStrings getDescription() {
        return description;
    }

    public DiscountCodeDraft withCode(final String code) {
        return newBuilder().code(code).build();
    }

    public String getCode() {
        return code;
    }

    public DiscountCodeDraft withCartDiscounts(final Referenceable<CartDiscount> cartDiscount) {
        return withCartDiscounts(asList(cartDiscount.toReference()));
    }

    public DiscountCodeDraft withCartDiscounts(final List<Reference<CartDiscount>> cartDiscounts) {
        return newBuilder().cartDiscounts(cartDiscounts).build();
    }

    public List<Reference<CartDiscount>> getCartDiscounts() {
        return cartDiscounts;
    }


    public DiscountCodeDraft withCartPredicate(@Nullable final CartPredicate cartPredicate) {
        return newBuilder().cartPredicate(cartPredicate).build();
    }

    @Nullable
    public String getCartPredicate() {
        return cartPredicate;
    }

    public DiscountCodeDraft withIsActive(final boolean isActive) {
        return newBuilder().isActive(isActive).build();
    }

    @JsonProperty("isActive")
    public boolean isActive() {
        return isActive;
    }

    public DiscountCodeDraft withMaxApplications(@Nullable final Long maxApplications) {
        return newBuilder().maxApplications(maxApplications).build();
    }

    @Nullable
    public Long getMaxApplications() {
        return maxApplications;
    }

    public DiscountCodeDraft withMaxApplicationsPerCustomer(@Nullable final Long maxApplicationsPerCustomer) {
        return newBuilder().maxApplicationsPerCustomer(maxApplicationsPerCustomer).build();
    }

    @Nullable
    public Long getMaxApplicationsPerCustomer() {
        return maxApplicationsPerCustomer;
    }

    private DiscountCodeDraftBuilder builder(final DiscountCodeDraft draft) {
        return DiscountCodeDraftBuilder.of(this);
    }

    private DiscountCodeDraftBuilder newBuilder() {
        return builder(this);
    }
}
