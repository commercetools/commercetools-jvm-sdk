package io.sphere.sdk.cartdiscounts;

import io.sphere.sdk.models.LocalizedString;
import io.sphere.sdk.types.CustomFieldsDraft;

import javax.annotation.Nullable;
import java.time.ZonedDateTime;

public final class CartDiscountDraftBuilder extends CartDiscountDraftBuilderBase<CartDiscountDraftBuilder> {

    CartDiscountDraftBuilder(final @Nullable Boolean active, final String cartPredicate, final @Nullable CustomFieldsDraft custom, final @Nullable LocalizedString description, final String key, final LocalizedString name,
                             final Boolean requiresDiscountCode, final String sortOrder, final StackingMode stackingMode, final @Nullable CartDiscountTarget target,
                             final @Nullable ZonedDateTime validFrom, final  @Nullable ZonedDateTime validUntil, final CartDiscountValue value) {
        super(active, cartPredicate,custom, description, key, name, requiresDiscountCode, sortOrder, stackingMode, target, validFrom, validUntil, value);
    }

    public static CartDiscountDraftBuilder of(final String cartPredicate, final LocalizedString name,
                                              final boolean requiresDiscountCode, final String sortOrder, final CartDiscountTarget target,
                                              final CartDiscountValue value) {
        return new CartDiscountDraftBuilder(null, cartPredicate, null,null, null, name, requiresDiscountCode, sortOrder, null, target, null, null, value);
    }

    public static CartDiscountDraftBuilder of(final LocalizedString name, final CartPredicate cartPredicate, final CartDiscountValue value, final CartDiscountTarget target, final String sortOrder, final boolean requiresDiscountCode) {
        return of(name, cartPredicate.toSphereCartPredicate(), value, target, sortOrder, requiresDiscountCode);
    }

    public CartDiscountDraftBuilder cartPredicate(final CartPredicate cartPredicate) {
        this.cartPredicate = cartPredicate.toSphereCartPredicate();
        return this;
    }

    public CartDiscountDraftBuilder active(final boolean active) {
        this.active = active;
        return this;
    }

    public CartDiscountDraftBuilder isActive(final boolean active) {
        this.active = active;
        return this;
    }

    public CartDiscountDraftBuilder requiresDiscountCode(final boolean requiresDiscountCode) {
        this.requiresDiscountCode = requiresDiscountCode;
        return this;
    }
}
