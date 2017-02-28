package io.sphere.sdk.discountcodes;

import io.sphere.sdk.cartdiscounts.CartDiscount;
import io.sphere.sdk.models.Base;
import io.sphere.sdk.models.Builder;
import io.sphere.sdk.models.LocalizedString;
import io.sphere.sdk.models.Reference;

import javax.annotation.Nullable;
import java.util.List;

public final class DiscountCodeDraftBuilder extends Base implements Builder<DiscountCodeDraftDsl> {
    private Boolean active;

    private List<Reference<CartDiscount>> cartDiscounts;

    @Nullable
    private String cartPredicate;

    private String code;

    @Nullable
    private LocalizedString description;

    @Nullable
    private Long maxApplications;

    @Nullable
    private Long maxApplicationsPerCustomer;

    @Nullable
    private LocalizedString name;

    DiscountCodeDraftBuilder() {
    }

    DiscountCodeDraftBuilder(final Boolean active, final List<Reference<CartDiscount>> cartDiscounts,
                             @Nullable final String cartPredicate, final String code,
                             @Nullable final LocalizedString description, @Nullable final Long maxApplications,
                             @Nullable final Long maxApplicationsPerCustomer, @Nullable final LocalizedString name) {
        this.active = active;
        this.cartDiscounts = cartDiscounts;
        this.cartPredicate = cartPredicate;
        this.code = code;
        this.description = description;
        this.maxApplications = maxApplications;
        this.maxApplicationsPerCustomer = maxApplicationsPerCustomer;
        this.name = name;
    }

    public DiscountCodeDraftBuilder active(final Boolean active) {
        this.active = active;
        return this;
    }

    public DiscountCodeDraftBuilder cartDiscounts(final List<Reference<CartDiscount>> cartDiscounts) {
        this.cartDiscounts = cartDiscounts;
        return this;
    }

    public DiscountCodeDraftBuilder cartPredicate(@Nullable final String cartPredicate) {
        this.cartPredicate = cartPredicate;
        return this;
    }

    public DiscountCodeDraftBuilder code(final String code) {
        this.code = code;
        return this;
    }

    public DiscountCodeDraftBuilder description(@Nullable final LocalizedString description) {
        this.description = description;
        return this;
    }

    public DiscountCodeDraftBuilder maxApplications(@Nullable final Long maxApplications) {
        this.maxApplications = maxApplications;
        return this;
    }

    public DiscountCodeDraftBuilder maxApplicationsPerCustomer(@Nullable final Long maxApplicationsPerCustomer) {
        this.maxApplicationsPerCustomer = maxApplicationsPerCustomer;
        return this;
    }

    public DiscountCodeDraftBuilder name(@Nullable final LocalizedString name) {
        this.name = name;
        return this;
    }

    public DiscountCodeDraftDsl build() {
        return new DiscountCodeDraftDsl(active, cartDiscounts, cartPredicate, code, description, maxApplications, maxApplicationsPerCustomer, name);
    }

    public static DiscountCodeDraftBuilder of(final List<Reference<CartDiscount>> cartDiscounts,
                                              final String code) {
        return new DiscountCodeDraftBuilder(null, cartDiscounts, null, code, null, null, null, null);
    }

    public static DiscountCodeDraftBuilder of(final DiscountCodeDraft template) {
        return new DiscountCodeDraftBuilder(template.isActive(), template.getCartDiscounts(), template.getCartPredicate(), template.getCode(), template.getDescription(), template.getMaxApplications(), template.getMaxApplicationsPerCustomer(), template.getName());
    }
}
