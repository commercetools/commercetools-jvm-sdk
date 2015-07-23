package io.sphere.sdk.discountcodes;

import com.fasterxml.jackson.annotation.JsonCreator;
import io.sphere.sdk.cartdiscounts.CartDiscount;
import io.sphere.sdk.models.DefaultModelImpl;
import io.sphere.sdk.models.LocalizedStrings;
import io.sphere.sdk.models.Reference;

import javax.annotation.Nullable;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

final class DiscountCodeImpl extends DefaultModelImpl<DiscountCode> implements DiscountCode {
    private final String code;
    @Nullable
    private final LocalizedStrings name;
    @Nullable
    private final LocalizedStrings description;
    private final List<Reference<CartDiscount>> cartDiscounts;
    private final boolean isActive;
    @Nullable
    private final Long maxApplications;
    @Nullable
    private final Long maxApplicationsPerCustomer;
    @Nullable
    private final String cartPredicate;
    private final List<Reference<Object>> references;

    @JsonCreator
    public DiscountCodeImpl(final String id, final long version, final ZonedDateTime createdAt, final ZonedDateTime lastModifiedAt, final List<Reference<CartDiscount>> cartDiscounts, final String code, final LocalizedStrings name, final LocalizedStrings description, final boolean isActive, final Long maxApplications, final Long maxApplicationsPerCustomer, final String cartPredicate, final List<Reference<Object>> references) {
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
    public Optional<String> getCartPredicate() {
        return Optional.ofNullable(cartPredicate);
    }

    @Override
    public String getCode() {
        return code;
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
    public Optional<Long> getMaxApplications() {
        return Optional.ofNullable(maxApplications);
    }

    @Override
    public Optional<Long> getMaxApplicationsPerCustomer() {
        return Optional.ofNullable(maxApplicationsPerCustomer);
    }

    @Override
    @Nullable
    public LocalizedStrings getName() {
        return name;
    }

    @Override
    public List<Reference<Object>> getReferences() {
        return references;
    }
}
