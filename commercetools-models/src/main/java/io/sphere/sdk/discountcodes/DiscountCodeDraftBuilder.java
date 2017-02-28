package io.sphere.sdk.discountcodes;

import io.sphere.sdk.cartdiscounts.CartDiscount;
import io.sphere.sdk.models.LocalizedString;
import io.sphere.sdk.models.Reference;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;

public final class DiscountCodeDraftBuilder extends DiscountCodeDraftBuilderBase<DiscountCodeDraftBuilder> {

    DiscountCodeDraftBuilder(final Boolean active, final List<Reference<CartDiscount>> cartDiscounts, @Nullable final String cartPredicate, final String code, @Nullable final LocalizedString description, @Nullable final Long maxApplications, @Nullable final Long maxApplicationsPerCustomer, @Nullable final LocalizedString name) {
        super(active, cartDiscounts, cartPredicate, code, description, maxApplications, maxApplicationsPerCustomer, name);
    }

    public DiscountCodeDraftBuilder cartPredicate(@Nullable final CartDiscountPredicate cartPredicate) {
        return cartPredicate(cartPredicate.toSphereCartPredicate());
    }

    public DiscountCodeDraftBuilder isActive(final boolean active) {
        this.active = active;
        return this;
    }

    public static DiscountCodeDraftBuilder of(final String code, final Referenceable<CartDiscount> cartDiscount) {
        return of(code, Collections.singletonList(cartDiscount.toReference()));
    }
}
