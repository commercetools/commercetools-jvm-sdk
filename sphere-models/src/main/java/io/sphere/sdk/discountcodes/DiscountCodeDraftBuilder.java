package io.sphere.sdk.discountcodes;

import io.sphere.sdk.cartdiscounts.CartDiscount;
import io.sphere.sdk.cartdiscounts.CartDiscountPredicate;
import io.sphere.sdk.models.*;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class DiscountCodeDraftBuilder extends Base implements Builder<DiscountCodeDraftDsl> {
    @Nullable
    private LocalizedString name;
    @Nullable
    private LocalizedString description;
    private String code;
    private List<Reference<CartDiscount>> cartDiscounts;
    @Nullable
    private CartDiscountPredicate cartPredicate;
    private Boolean isActive = true;
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
                .cartPredicate(Optional.ofNullable(template.getCartPredicate()).map(CartDiscountPredicate::of).orElse(null))
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

    public DiscountCodeDraftBuilder name(@Nullable final LocalizedString name) {
        this.name = name;
        return this;
    }

    public DiscountCodeDraftBuilder description(@Nullable final LocalizedString description) {
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

    public DiscountCodeDraftBuilder cartPredicate(@Nullable final CartDiscountPredicate cartPredicate) {
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
    public DiscountCodeDraftDsl build() {
        return new DiscountCodeDraftDsl(cartDiscounts, name, description, code, Optional.ofNullable(cartPredicate).map(CartDiscountPredicate::toSphereCartPredicate).orElse(null), isActive, maxApplications, maxApplicationsPerCustomer);
    }
}
