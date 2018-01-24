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

public final class DiscountCodeDraftDsl extends DiscountCodeDraftDslBase<DiscountCodeDraftDsl> {

    DiscountCodeDraftDsl(final Boolean active, final List<Reference<CartDiscount>> cartDiscounts, final @Nullable String cartPredicate,
                         final String code, final @Nullable CustomFieldsDraft custom, final @Nullable LocalizedString description,@Nullable final List<String> groups,
                         final @Nullable Long maxApplications, final @Nullable Long maxApplicationsPerCustomer, final @Nullable LocalizedString name,@Nullable final ZonedDateTime validFrom, @Nullable final ZonedDateTime validUntil) {
        super(active, cartDiscounts, cartPredicate, code, custom, description,groups, maxApplications, maxApplicationsPerCustomer, name,validFrom,validUntil);
    }

    public DiscountCodeDraftDsl withCartDiscounts(final Referenceable<CartDiscount> cartDiscount) {
        return withCartDiscounts(Collections.singletonList(cartDiscount.toReference()));
    }

    public static DiscountCodeDraftDsl of(final String code, final Referenceable<io.sphere.sdk.cartdiscounts.CartDiscount> cartDiscount) {
        return of(code, Collections.singletonList(cartDiscount.toReference()));
    }

    public DiscountCodeDraftDsl withCartPredicate(@Nullable final CartPredicate cartPredicate) {
        return newBuilder().cartPredicate(cartPredicate.toSphereCartPredicate()).build();
    }

}
