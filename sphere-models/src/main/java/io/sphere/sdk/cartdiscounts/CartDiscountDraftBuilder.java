package io.sphere.sdk.cartdiscounts;

import io.sphere.sdk.models.Base;
import io.sphere.sdk.models.Builder;
import io.sphere.sdk.models.LocalizedStrings;

import javax.annotation.Nullable;
import java.time.Instant;
import java.util.Optional;

public class CartDiscountDraftBuilder extends Base implements Builder<CartDiscountDraft> {
    private LocalizedStrings name;
    @Nullable
    private LocalizedStrings description;
    private CartDiscountValue value;
    private final CartDiscountPredicate cartPredicate;
    private CartDiscountTarget target;
    private final String sortOrder;
    private boolean isActive = true;
    @Nullable
    private Instant validFrom;
    @Nullable
    private Instant validUntil;
    private final boolean requiresDiscountCode;

    private CartDiscountDraftBuilder(final LocalizedStrings name, final CartDiscountPredicate cartPredicate, final CartDiscountValue value, final CartDiscountTarget target, final String sortOrder, final boolean requiresDiscountCode) {
        this.cartPredicate = cartPredicate;
        this.name = name;
        this.value = value;
        this.target = target;
        this.sortOrder = sortOrder;
        this.requiresDiscountCode = requiresDiscountCode;
    }

    public static CartDiscountDraftBuilder of(final LocalizedStrings name, final CartDiscountPredicate cartPredicate, final CartDiscountValue value, final CartDiscountTarget target, final String sortOrder, final boolean requiresDiscountCode) {
        return new CartDiscountDraftBuilder(name, cartPredicate, value, target, sortOrder, requiresDiscountCode);
    }

    public CartDiscountDraftBuilder description(final LocalizedStrings description) {
        return description(Optional.of(description));
    }
    
    public CartDiscountDraftBuilder description(final Optional<LocalizedStrings> description) {
        this.description = description.orElse(null);
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

    public CartDiscountDraftBuilder validFrom(final Instant validFrom) {
        return validFrom(Optional.of(validFrom));
    }
    
    public CartDiscountDraftBuilder validFrom(final Optional<Instant> validFrom) {
        this.validFrom = validFrom.orElse(null);
        return this;
    }
    
    public CartDiscountDraftBuilder validUntil(final Optional<Instant> validUntil) {
        this.validUntil = validUntil.orElse(null);
        return this;
    }

    public CartDiscountDraftBuilder validUntil(final Instant validUntil) {
        return validUntil(Optional.of(validUntil));
    }

    @Override
    public CartDiscountDraft build() {
        return new CartDiscountDraft(name, cartPredicate, description, value, target, sortOrder, isActive, validFrom, validUntil, requiresDiscountCode);
    }
}
