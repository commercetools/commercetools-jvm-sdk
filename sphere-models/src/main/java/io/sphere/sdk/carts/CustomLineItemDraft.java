package io.sphere.sdk.carts;

import io.sphere.sdk.models.LocalizedStrings;
import io.sphere.sdk.models.Reference;
import io.sphere.sdk.models.Referenceable;
import io.sphere.sdk.taxcategories.TaxCategory;

import javax.money.MonetaryAmount;

public class CustomLineItemDraft {
    private final LocalizedStrings name;
    private final MonetaryAmount money;
    private final String slug;
    private final Reference<TaxCategory> taxCategory;
    private final Long quantity;

    private CustomLineItemDraft(final LocalizedStrings name, final String slug, final MonetaryAmount money, final Referenceable<TaxCategory> taxCategory, final Long quantity) {
        this.name = name;
        this.money = money;
        this.slug = slug;
        this.taxCategory = taxCategory.toReference();
        this.quantity = quantity;
    }

    public static CustomLineItemDraft of(final LocalizedStrings name, final String slug, final MonetaryAmount money, final Referenceable<TaxCategory> taxCategory, final long quantity) {
        return new CustomLineItemDraft(name, slug, money, taxCategory, quantity);
    }

    public LocalizedStrings getName() {
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

    public Long getQuantity() {
        return quantity;
    }
}
