package io.sphere.sdk.carts;

import com.fasterxml.jackson.annotation.JsonCreator;
import io.sphere.sdk.models.LocalizedStrings;
import io.sphere.sdk.models.Reference;
import io.sphere.sdk.taxcategories.TaxCategory;
import io.sphere.sdk.taxcategories.TaxRate;

import javax.money.MonetaryAmount;
import java.util.Optional;
import java.util.Set;

final class CustomLineItemImpl implements CustomLineItem {
    private final String id;
    private final LocalizedStrings name;
    private final MonetaryAmount money;
    private final String slug;
    private final long quantity;
    private final Set<ItemState> state;
    private final Reference<TaxCategory> taxCategory;
    private final Optional<TaxRate> taxRate;

    @JsonCreator
    CustomLineItemImpl(final String id, final LocalizedStrings name, final MonetaryAmount money,
                       final String slug, final long quantity, final Set<ItemState> state,
                       final Reference<TaxCategory> taxCategory, final Optional<TaxRate> taxRate) {
        this.id = id;
        this.name = name;
        this.money = money;
        this.slug = slug;
        this.quantity = quantity;
        this.state = state;
        this.taxCategory = taxCategory;
        this.taxRate = taxRate;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public LocalizedStrings getName() {
        return name;
    }

    @Override
    public MonetaryAmount getMoney() {
        return money;
    }

    @Override
    public String getSlug() {
        return slug;
    }

    @Override
    public long getQuantity() {
        return quantity;
    }

    @Override
    public Set<ItemState> getState() {
        return state;
    }

    @Override
    public Reference<TaxCategory> getTaxCategory() {
        return taxCategory;
    }

    @Override
    public Optional<TaxRate> getTaxRate() {
        return taxRate;
    }
}
