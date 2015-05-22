package io.sphere.sdk.discountcodes;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.sphere.sdk.cartdiscounts.CartDiscount;
import io.sphere.sdk.cartdiscounts.CartDiscountPredicate;
import io.sphere.sdk.models.Base;
import io.sphere.sdk.models.LocalizedStrings;
import io.sphere.sdk.models.Reference;
import io.sphere.sdk.models.Referenceable;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Optional;

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

    public static DiscountCodeDraft of(final String code) {
        return DiscountCodeDraftBuilder.of(code).build();
    }

    public DiscountCodeDraft withName(final Optional<LocalizedStrings> name) {
        return builder(this).name(name).build();
    }

    public DiscountCodeDraft withName(final LocalizedStrings name) {
        return withName(Optional.of(name));
    }

    public Optional<LocalizedStrings> getName() {
        return Optional.ofNullable(name);
    }

    public DiscountCodeDraft withDescription(final Optional<LocalizedStrings> description) {
        return builder(this).description(description).build();
    }

    public DiscountCodeDraft withDescription(final LocalizedStrings description) {
        return withDescription(Optional.of(description));
    }

    public Optional<LocalizedStrings> getDescription() {
        return Optional.ofNullable(description);
    }

    public DiscountCodeDraft withCode(final String code) {
        return builder(this).code(code).build();
    }

    public String getCode() {
        return code;
    }

    public DiscountCodeDraft withCartDiscounts(final Referenceable<CartDiscount> cartDiscount) {
        return withCartDiscounts(asList(cartDiscount.toReference()));
    }

    public DiscountCodeDraft withCartDiscounts(final List<Reference<CartDiscount>> cartDiscounts) {
        return builder(this).cartDiscounts(cartDiscounts).build();
    }

    public List<Reference<CartDiscount>> getCartDiscounts() {
        return cartDiscounts;
    }


    public DiscountCodeDraft withCartPredicate(final Optional<CartDiscountPredicate> cartPredicate) {
        return builder(this).cartPredicate(cartPredicate).build();
    }

    public DiscountCodeDraft withCartPredicate(final CartDiscountPredicate cartPredicate) {
        return withCartPredicate(Optional.of(cartPredicate));
    }

    public Optional<String> getCartPredicate() {
        return Optional.ofNullable(cartPredicate);
    }

    public DiscountCodeDraft withIsActive(final boolean isActive) {
        return builder(this).isActive(isActive).build();
    }

    @JsonProperty("isActive")
    public boolean isActive() {
        return isActive;
    }

    public DiscountCodeDraft withMaxApplications(final Optional<Long> maxApplications) {
        return builder(this).maxApplications(maxApplications).build();
    }

    public DiscountCodeDraft withMaxApplications(final long maxApplications) {
        return withMaxApplications(Optional.of(maxApplications));
    }

    public Optional<Long> getMaxApplications() {
        return Optional.ofNullable(maxApplications);
    }

    public DiscountCodeDraft withMaxApplicationsPerCustomer(final Optional<Long> maxApplicationsPerCustomer) {
        return builder(this).maxApplicationsPerCustomer(maxApplicationsPerCustomer).build();
    }

    public DiscountCodeDraft withMaxApplicationsPerCustomer(final long maxApplicationsPerCustomer) {
        return withMaxApplicationsPerCustomer(Optional.of(maxApplicationsPerCustomer));
    }

    public Optional<Long> getMaxApplicationsPerCustomer() {
        return Optional.ofNullable(maxApplicationsPerCustomer);
    }

    private DiscountCodeDraftBuilder builder(final DiscountCodeDraft draft) {
        return DiscountCodeDraftBuilder.of(this);
    }
}
