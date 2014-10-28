package io.sphere.sdk.carts;

import io.sphere.sdk.models.LocalizedString;
import io.sphere.sdk.models.Reference;
import io.sphere.sdk.models.Referenceable;
import io.sphere.sdk.taxcategories.TaxCategory;

import javax.money.MonetaryAmount;

public class CustomLineItemDraft {
    private final LocalizedString name;
    private final MonetaryAmount money;
    private final String slug;
    private final Reference<TaxCategory> taxCategory;
    private final int quantity;

    private CustomLineItemDraft(final LocalizedString name, final String slug, final MonetaryAmount money, final Referenceable<TaxCategory> taxCategory, final int quantity) {
        this.name = name;
        this.money = money;
        this.slug = slug;
        this.taxCategory = taxCategory.toReference();
        this.quantity = quantity;
    }

    public static CustomLineItemDraft of(final LocalizedString name, final String slug, final MonetaryAmount money, final Referenceable<TaxCategory> taxCategory, final int quantity) {
        return new CustomLineItemDraft(name, slug, money, taxCategory, quantity);
    }

    public LocalizedString getName() {
        return name;
    }

    public MonetaryAmount getMoney() {
        return money;
    }

    public String getSlug() {
        return slug;
    }

    public Reference<TaxCategory> getTaxCategory() {
        return taxCategory;
    }

    public int getQuantity() {
        return quantity;
    }
}
