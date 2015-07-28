package io.sphere.sdk.cartdiscounts;

import com.fasterxml.jackson.annotation.JsonCreator;
import io.sphere.sdk.models.DefaultModelImpl;
import io.sphere.sdk.models.LocalizedStrings;
import io.sphere.sdk.models.Reference;

import javax.annotation.Nullable;
import java.time.ZonedDateTime;
import java.util.List;

final class CartDiscountImpl extends DefaultModelImpl<CartDiscount> implements CartDiscount {
    private final LocalizedStrings name;
    @Nullable
    private final LocalizedStrings description;
    private final CartDiscountValue value;
    private final String cartPredicate;
    private final CartDiscountTarget target;
    private final String sortOrder;
    private final boolean isActive;
    @Nullable
    private final ZonedDateTime validFrom;
    @Nullable
    private final ZonedDateTime validUntil;
    private final boolean requiresDiscountCode;
    private final List<Reference<Object>> references;

    @JsonCreator
    public CartDiscountImpl(final String id, final Long version, final ZonedDateTime createdAt, final ZonedDateTime lastModifiedAt, final String cartPredicate, final LocalizedStrings name, final LocalizedStrings description, final CartDiscountValue value, final CartDiscountTarget target, final String sortOrder, final boolean isActive, final ZonedDateTime validFrom, final ZonedDateTime validUntil, final boolean requiresDiscountCode, final List<Reference<Object>> references) {
        super(id, version, createdAt, lastModifiedAt);
        this.cartPredicate = cartPredicate;
        this.name = name;
        this.description = description;
        this.value = value;
        this.target = target;
        this.sortOrder = sortOrder;
        this.isActive = isActive;
        this.validFrom = validFrom;
        this.validUntil = validUntil;
        this.requiresDiscountCode = requiresDiscountCode;
        this.references = references;
    }

    @Override
    public String getCartPredicate() {
        return cartPredicate;
    }

    @Override
    @Nullable
    public LocalizedStrings getDescription() {
        return description;
    }

    @Override
    public boolean isActive() {
        return isActive;
    }

    @Override
    public LocalizedStrings getName() {
        return name;
    }

    @Override
    public List<Reference<Object>> getReferences() {
        return references;
    }

    @Override
    public boolean isRequiringDiscountCode() {
        return requiresDiscountCode;
    }

    @Override
    public String getSortOrder() {
        return sortOrder;
    }

    @Override
    public CartDiscountTarget getTarget() {
        return target;
    }

    @Override
    @Nullable
    public ZonedDateTime getValidFrom() {
        return validFrom;
    }

    @Override
    @Nullable
    public ZonedDateTime getValidUntil() {
        return validUntil;
    }

    @Override
    public CartDiscountValue getValue() {
        return value;
    }
}
