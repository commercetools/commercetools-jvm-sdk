package io.sphere.sdk.discountcodes;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.sphere.sdk.cartdiscounts.CartDiscount;
import io.sphere.sdk.models.LocalizedString;
import io.sphere.sdk.models.Reference;
import io.sphere.sdk.models.Referenceable;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;

public class DiscountCodeDraftDsl extends DiscountCodeDraftDslBase<DiscountCodeDraftDsl> {

    DiscountCodeDraftDsl(@JsonProperty("isActive") final Boolean active, final List<Reference<CartDiscount>> cartDiscounts, @Nullable final String cartPredicate, final String code, @Nullable final LocalizedString description, @Nullable final Long maxApplications, @Nullable final Long maxApplicationsPerCustomer, @Nullable final LocalizedString name) {
        super(active, cartDiscounts, cartPredicate, code, description, maxApplications, maxApplicationsPerCustomer, name);
    }

    public DiscountCodeDraftDsl withCartDiscounts(final Referenceable<CartDiscount> cartDiscount) {
        return withCartDiscounts(Collections.singletonList(cartDiscount.toReference()));
    }

    public static DiscountCodeDraftDsl of(final String code, final Referenceable<io.sphere.sdk.cartdiscounts.CartDiscount> cartDiscount) {
        return of(code, Collections.singletonList(cartDiscount.toReference()));
    }

    public DiscountCodeDraftDsl withCartPredicate(@Nullable final io.sphere.sdk.cartdiscounts.CartDiscountPredicate cartPredicate) {
        return newBuilder().cartPredicate(cartPredicate.toSphereCartPredicate()).build();
    }

}
