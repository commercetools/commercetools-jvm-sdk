package io.sphere.sdk.orders;

import io.sphere.sdk.carts.ItemState;
import io.sphere.sdk.models.LocalizedStrings;
import io.sphere.sdk.models.Reference;
import io.sphere.sdk.taxcategories.TaxCategory;
import io.sphere.sdk.taxcategories.TaxRate;

import javax.money.MonetaryAmount;
import java.util.Set;

final class CustomLineItemImportDraftImpl implements CustomLineItemImportDraft {
    private final String id;
    private final LocalizedStrings name;
    private final MonetaryAmount money;
    private final String slug;
    private final long quantity;
    private final Set<ItemState> state;
    private final Reference<TaxCategory> taxCategory;
    private final TaxRate taxRate;

    public CustomLineItemImportDraftImpl(final String id, final LocalizedStrings name, final MonetaryAmount money, final String slug, final long quantity, final Set<ItemState> state, final Reference<TaxCategory> taxCategory, final TaxRate taxRate) {
        this.id = id;
        this.name = name;
        this.money = money;
        this.slug = slug;
        this.quantity = quantity;
        this.state = state;
        this.taxCategory = taxCategory;
        this.taxRate = taxRate;
    }

    public String getId() {
        return id;
    }

    @Override
    public MonetaryAmount getMoney() {
        return money;
    }

    @Override
    public LocalizedStrings getName() {
        return name;
    }

    @Override
    public long getQuantity() {
        return quantity;
    }

    @Override
    public String getSlug() {
        return slug;
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
    public TaxRate getTaxRate() {
        return taxRate;
    }
}
