package io.sphere.sdk.cartdiscounts;

import io.sphere.sdk.models.LocalizedString;

import javax.annotation.Nullable;
import java.time.ZonedDateTime;

public final class CartDiscountDraftBuilder extends CartDiscountDraftBuilderBase<CartDiscountDraftBuilder> {
    CartDiscountDraftBuilder() {
    }

    CartDiscountDraftBuilder(final Boolean active, final String cartPredicate, @Nullable final LocalizedString description, final LocalizedString name, final Boolean requiresDiscountCode, final String sortOrder, final CartDiscountTarget target, @Nullable final ZonedDateTime validFrom, @Nullable final ZonedDateTime validUntil, final CartDiscountValue value) {
        super(active, cartPredicate, description, name, requiresDiscountCode, sortOrder, target, validFrom, validUntil, value);
    }

    public static CartDiscountDraftBuilder of(final String cartPredicate, final LocalizedString name,
                                              final boolean requiresDiscountCode, final String sortOrder, final CartDiscountTarget target,
                                              final CartDiscountValue value) {
        return new CartDiscountDraftBuilder(null, cartPredicate, null, name, requiresDiscountCode, sortOrder, target, null, null, value);
    }

    public static CartDiscountDraftBuilder of(final CartDiscountDraft template) {
        return new CartDiscountDraftBuilder(template.isActive(), template.getCartPredicate(), template.getDescription(), template.getName(), template.isRequiresDiscountCode(), template.getSortOrder(), template.getTarget(), template.getValidFrom(), template.getValidUntil(), template.getValue());
    }

    public CartDiscountDraftBuilder cartPredicate(final CartDiscountPredicate cartPredicate) {
        this.cartPredicate = cartPredicate.toSphereCartPredicate();
        return this;
    }

    public static CartDiscountDraftBuilder of(final io.sphere.sdk.models.LocalizedString name, final CartDiscountPredicate cartPredicate, final io.sphere.sdk.cartdiscounts.CartDiscountValue value, final io.sphere.sdk.cartdiscounts.CartDiscountTarget target, final java.lang.String sortOrder, final boolean requiresDiscountCode) {
        return of(cartPredicate.toSphereCartPredicate(), name, requiresDiscountCode, sortOrder, target, value);
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
