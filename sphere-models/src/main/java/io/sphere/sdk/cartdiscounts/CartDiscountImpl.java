package io.sphere.sdk.cartdiscounts;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.databind.JsonNode;
import io.sphere.sdk.models.LocalizedString;
import io.sphere.sdk.models.ResourceImpl;
import io.sphere.sdk.models.Reference;

import javax.annotation.Nullable;
import java.time.ZonedDateTime;
import java.util.List;

final class CartDiscountImpl extends ResourceImpl<CartDiscount> implements CartDiscount {
    private final LocalizedString name;
    @Nullable
    private final LocalizedString description;
    private final CartDiscountValue value;
    private final String cartPredicate;
    private final CartDiscountTarget target;
    private final String sortOrder;
    private final Boolean isActive;
    @Nullable
    private final ZonedDateTime validFrom;
    @Nullable
    private final ZonedDateTime validUntil;
    private final Boolean requiresDiscountCode;
    private final List<Reference<JsonNode>> references;

    @JsonCreator
    public CartDiscountImpl(final String id, final Long version, final ZonedDateTime createdAt, final ZonedDateTime lastModifiedAt, final String cartPredicate, final LocalizedString name, @Nullable final LocalizedString description, final CartDiscountValue value, final CartDiscountTarget target, final String sortOrder, final Boolean isActive, @Nullable final ZonedDateTime validFrom, @Nullable final ZonedDateTime validUntil, final Boolean requiresDiscountCode, final List<Reference<JsonNode>> references) {
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
    public LocalizedString getDescription() {
        return description;
    }

    @Override
    public Boolean isActive() {
        return isActive;
    }

    @Override
    public LocalizedString getName() {
        return name;
    }

    @Override
    public List<Reference<JsonNode>> getReferences() {
        return references;
    }

    @Override
    public Boolean isRequiringDiscountCode() {
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
