package io.sphere.sdk.discountcodes;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.databind.JsonNode;
import io.sphere.sdk.cartdiscounts.CartDiscount;
import io.sphere.sdk.models.LocalizedString;
import io.sphere.sdk.models.ResourceImpl;
import io.sphere.sdk.models.Reference;

import javax.annotation.Nullable;
import java.time.ZonedDateTime;
import java.util.List;

final class DiscountCodeImpl extends ResourceImpl<DiscountCode> implements DiscountCode {
    private final String code;
    @Nullable
    private final LocalizedString name;
    @Nullable
    private final LocalizedString description;
    private final List<Reference<CartDiscount>> cartDiscounts;
    private final Boolean isActive;
    @Nullable
    private final Long maxApplications;
    @Nullable
    private final Long maxApplicationsPerCustomer;
    @Nullable
    private final String cartPredicate;
    private final List<Reference<JsonNode>> references;

    @JsonCreator
    public DiscountCodeImpl(final String id, final Long version, final ZonedDateTime createdAt, final ZonedDateTime lastModifiedAt, final List<Reference<CartDiscount>> cartDiscounts, final String code, @Nullable final LocalizedString name, @Nullable final LocalizedString description, final Boolean isActive, @Nullable final Long maxApplications, @Nullable final Long maxApplicationsPerCustomer, @Nullable final String cartPredicate, final List<Reference<JsonNode>> references) {
        super(id, version, createdAt, lastModifiedAt);
        this.cartDiscounts = cartDiscounts;
        this.code = code;
        this.name = name;
        this.description = description;
        this.isActive = isActive;
        this.maxApplications = maxApplications;
        this.maxApplicationsPerCustomer = maxApplicationsPerCustomer;
        this.cartPredicate = cartPredicate;
        this.references = references;
    }

    @Override
    public List<Reference<CartDiscount>> getCartDiscounts() {
        return cartDiscounts;
    }

    @Override
    @Nullable
    public String getCartPredicate() {
        return cartPredicate;
    }

    @Override
    public String getCode() {
        return code;
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

    @Nullable
    @Override
    public Long getMaxApplications() {
        return maxApplications;
    }

    @Nullable
    @Override
    public Long getMaxApplicationsPerCustomer() {
        return maxApplicationsPerCustomer;
    }

    @Override
    @Nullable
    public LocalizedString getName() {
        return name;
    }

    @Override
    public List<Reference<JsonNode>> getReferences() {
        return references;
    }
}
