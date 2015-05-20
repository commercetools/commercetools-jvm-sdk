package io.sphere.sdk.cartdiscounts;

import io.sphere.sdk.models.Base;
import io.sphere.sdk.models.LocalizedStrings;

import javax.annotation.Nullable;
import java.time.Instant;

/**
 *
 * @see CartDiscountDraftBuilder
 */
public class CartDiscountDraft extends Base {
    private final LocalizedStrings name;
    @Nullable
    private final LocalizedStrings description;
    private final CartDiscountValue value;
    private final String cartPredicate;
    private final CartDiscountTarget target;
    private final String sortOrder;
    private final boolean isActive;
    @Nullable
    private final Instant validFrom;
    @Nullable
    private final Instant validUntil;
    private final boolean requiresDiscountCode;

    CartDiscountDraft(final LocalizedStrings name, final CartDiscountPredicate cartPredicate, final LocalizedStrings description, final CartDiscountValue value, final CartDiscountTarget target, final String sortOrder, final boolean isActive, final Instant validFrom, final Instant validUntil, final boolean requiresDiscountCode) {
        this.cartPredicate = cartPredicate.toSphereCartDiscountPredicate();
        this.name = name;
        this.description = description;
        this.value = value;
        this.target = target;
        this.sortOrder = sortOrder;
        this.isActive = isActive;
        this.validFrom = validFrom;
        this.validUntil = validUntil;
        this.requiresDiscountCode = requiresDiscountCode;
    }

    public String getCartPredicate() {
        return cartPredicate;
    }

    @Nullable
    public LocalizedStrings getDescription() {
        return description;
    }

    public boolean isActive() {
        return isActive;
    }

    public LocalizedStrings getName() {
        return name;
    }

    public boolean isRequiresDiscountCode() {
        return requiresDiscountCode;
    }

    public String getSortOrder() {
        return sortOrder;
    }

    public CartDiscountTarget getTarget() {
        return target;
    }

    @Nullable
    public Instant getValidFrom() {
        return validFrom;
    }

    @Nullable
    public Instant getValidUntil() {
        return validUntil;
    }

    public CartDiscountValue getValue() {
        return value;
    }
}
