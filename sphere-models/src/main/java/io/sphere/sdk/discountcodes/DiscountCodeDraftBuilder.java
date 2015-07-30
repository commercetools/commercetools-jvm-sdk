package io.sphere.sdk.discountcodes;

import io.sphere.sdk.cartdiscounts.CartDiscount;
import io.sphere.sdk.cartdiscounts.CartPredicate;
import io.sphere.sdk.models.*;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class DiscountCodeDraftBuilder extends Base implements Builder<DiscountCodeDraft> {
    @Nullable
    private LocalizedStrings name;
    @Nullable
    private LocalizedStrings description;
    private String code;
    private List<Reference<CartDiscount>> cartDiscounts;
    @Nullable
    private CartPredicate cartPredicate;
    private boolean isActive = true;
    @Nullable
    private Long maxApplications;
    @Nullable
    private Long maxApplicationsPerCustomer;

    private DiscountCodeDraftBuilder(final String code, final List<Reference<CartDiscount>> cartDiscounts) {
        this.code = code;
        this.cartDiscounts = cartDiscounts;
    }

    public static DiscountCodeDraftBuilder of(final DiscountCodeDraft template) {
        return of(template.getCode(), template.getCartDiscounts())
                .name(template.getName())
                .description(template.getDescription())
                .cartPredicate(Optional.ofNullable(template.getCartPredicate()).map(CartPredicate::of).orElse(null))
                .isActive(template.isActive())
                .maxApplications(template.getMaxApplications())
                .maxApplicationsPerCustomer(template.getMaxApplicationsPerCustomer());
    }

    public static DiscountCodeDraftBuilder of(final String code, final Referenceable<CartDiscount> cartDiscount) {
        return of(code, Collections.singletonList(cartDiscount.toReference()));
    }

    public static DiscountCodeDraftBuilder of(final String code, final List<Reference<CartDiscount>> cartDiscounts) {
        return new DiscountCodeDraftBuilder(code, cartDiscounts);
    }

    public DiscountCodeDraftBuilder name(@Nullable final LocalizedStrings name) {
        this.name = name;
        return this;
    }

    public DiscountCodeDraftBuilder description(@Nullable final LocalizedStrings description) {
        this.description = description;
        return this;
    }

    public DiscountCodeDraftBuilder code(final String code) {
        this.code = code;
        return this;
    }

    public DiscountCodeDraftBuilder cartDiscounts(final List<Reference<CartDiscount>> cartDiscounts) {
        this.cartDiscounts = cartDiscounts;
        return this;
    }

    public DiscountCodeDraftBuilder cartPredicate(@Nullable final CartPredicate cartPredicate) {
        this.cartPredicate = cartPredicate;
        return this;
    }

    public DiscountCodeDraftBuilder isActive(final boolean isActive) {
        this.isActive = isActive;
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

    @Override
    public DiscountCodeDraft build() {
        return new DiscountCodeDraft(cartDiscounts, name, description, code, Optional.ofNullable(cartPredicate).map(CartPredicate::toSphereCartPredicate).orElse(null), isActive, maxApplications, maxApplicationsPerCustomer);
    }
}
