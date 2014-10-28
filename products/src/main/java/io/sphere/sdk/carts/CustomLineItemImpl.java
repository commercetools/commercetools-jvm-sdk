package io.sphere.sdk.carts;

import io.sphere.sdk.models.LocalizedString;
import io.sphere.sdk.models.Reference;
import io.sphere.sdk.taxcategories.TaxCategory;
import io.sphere.sdk.taxcategories.TaxRate;

import javax.money.MonetaryAmount;
import java.util.List;
import java.util.Optional;

final class CustomLineItemImpl implements CustomLineItem {
    private final String id;
    private final LocalizedString name;
    private final MonetaryAmount money;
    private final String slug;
    private final int quantity;
    private final List<ItemState> state;
    private final Reference<TaxCategory> taxCategory;
    private final Optional<TaxRate> taxRate;

    CustomLineItemImpl(final String id, final LocalizedString name, final MonetaryAmount money,
                       final String slug, final int quantity, final List<ItemState> state,
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
    public LocalizedString getName() {
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
    public int getQuantity() {
        return quantity;
    }

    @Override
    public List<ItemState> getState() {
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
