package io.sphere.sdk.cartdiscounts;

import io.sphere.sdk.models.Base;
import io.sphere.sdk.models.Builder;
import io.sphere.sdk.models.LocalizedStrings;

import javax.annotation.Nullable;
import java.time.ZonedDateTime;
import java.util.Optional;

public class CartDiscountDraftBuilder extends Base implements Builder<CartDiscountDraft> {
    private LocalizedStrings name;
    @Nullable
    private LocalizedStrings description;
    private CartDiscountValue value;
    private CartPredicate cartPredicate;
    private CartDiscountTarget target;
    private final String sortOrder;
    private boolean isActive = true;
    @Nullable
    private ZonedDateTime validFrom;
    @Nullable
    private ZonedDateTime validUntil;
    private final boolean requiresDiscountCode;

    private CartDiscountDraftBuilder(final LocalizedStrings name, final CartPredicate cartPredicate, final CartDiscountValue value, final CartDiscountTarget target, final String sortOrder, final boolean requiresDiscountCode) {
        this.cartPredicate = cartPredicate;
        this.name = name;
        this.value = value;
        this.target = target;
        this.sortOrder = sortOrder;
        this.requiresDiscountCode = requiresDiscountCode;
    }

    public static CartDiscountDraftBuilder of(final LocalizedStrings name, final CartPredicate cartPredicate, final CartDiscountValue value, final CartDiscountTarget target, final String sortOrder, final boolean requiresDiscountCode) {
        return new CartDiscountDraftBuilder(name, cartPredicate, value, target, sortOrder, requiresDiscountCode);
    }

    public CartDiscountDraftBuilder description(@Nullable final LocalizedStrings description) {
        this.description = description;
        return this;
    }

    public CartDiscountDraftBuilder value(final CartDiscountValue value) {
        this.value = value;
        return this;
    }

    public CartDiscountDraftBuilder target(final CartDiscountTarget target) {
        this.target = target;
        return this;
    }

    public CartDiscountDraftBuilder name(final LocalizedStrings name) {
        this.name = name;
        return this;
    }

    public CartDiscountDraftBuilder isActive(final boolean isActive) {
        this.isActive = isActive;
        return this;
    }

    public CartDiscountDraftBuilder cartPredicate(final CartPredicate cartPredicate) {
        this.cartPredicate = cartPredicate;
        return this;
    }

    public CartDiscountDraftBuilder validFrom(@Nullable final ZonedDateTime validFrom) {
        this.validFrom = validFrom;
        return this;
    }
    
    public CartDiscountDraftBuilder validUntil(@Nullable final ZonedDateTime validUntil) {
        this.validUntil = validUntil;
        return this;
    }

    @Override
    public CartDiscountDraft build() {
        return new CartDiscountDraft(name, cartPredicate, description, value, target, sortOrder, isActive, validFrom, validUntil, requiresDiscountCode);
    }
}
