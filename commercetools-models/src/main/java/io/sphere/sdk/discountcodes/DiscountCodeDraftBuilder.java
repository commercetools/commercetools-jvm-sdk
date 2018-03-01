package io.sphere.sdk.discountcodes;

import io.sphere.sdk.cartdiscounts.CartDiscount;
import io.sphere.sdk.cartdiscounts.CartPredicate;
import io.sphere.sdk.models.LocalizedString;
import io.sphere.sdk.models.Reference;
import io.sphere.sdk.models.Referenceable;
import io.sphere.sdk.types.CustomFieldsDraft;

import javax.annotation.Nullable;
import java.time.ZonedDateTime;
import java.util.Collections;
import java.util.List;

public final class DiscountCodeDraftBuilder extends DiscountCodeDraftBuilderBase<DiscountCodeDraftBuilder> {

    DiscountCodeDraftBuilder(final Boolean active, final List<Reference<CartDiscount>> cartDiscounts, final @Nullable String cartPredicate,
                             final String code, final @Nullable CustomFieldsDraft custom, final @Nullable LocalizedString description,@Nullable final List<String> groups,
                             final @Nullable Long maxApplications, final @Nullable Long maxApplicationsPerCustomer, final @Nullable LocalizedString name, @Nullable final ZonedDateTime validFrom, @Nullable final ZonedDateTime validUntil) {
        super(active, cartDiscounts, cartPredicate, code, custom, description,groups, maxApplications, maxApplicationsPerCustomer, name,validFrom,validUntil);
    }

    public DiscountCodeDraftBuilder cartPredicate(@Nullable final CartPredicate cartPredicate) {
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
