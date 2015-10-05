package io.sphere.sdk.carts;

import io.sphere.sdk.models.LocalizedString;
import io.sphere.sdk.models.Reference;
import io.sphere.sdk.models.Referenceable;
import io.sphere.sdk.taxcategories.TaxCategory;

import javax.money.MonetaryAmount;

/**
 * <p>Draft for adding a custom line item to the cart.</p>
 *
 *  <p>Example for adding a {@link CustomLineItem} to a {@link Cart}:</p>
 {@include.example io.sphere.sdk.carts.commands.CartUpdateCommandTest#addCustomLineItem()}

 @see CustomLineItem
 */
public class CustomLineItemDraft {
    private final LocalizedString name;
    private final MonetaryAmount money;
    private final String slug;
    private final Reference<TaxCategory> taxCategory;
    private final Long quantity;

    private CustomLineItemDraft(final LocalizedString name, final String slug, final MonetaryAmount money, final Referenceable<TaxCategory> taxCategory, final Long quantity) {
        this.name = name;
        this.money = money;
        this.slug = slug;
        this.taxCategory = taxCategory.toReference();
        this.quantity = quantity;
    }

    public static CustomLineItemDraft of(final LocalizedString name, final String slug, final MonetaryAmount money, final Referenceable<TaxCategory> taxCategory, final long quantity) {
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

    public Long getQuantity() {
        return quantity;
    }
}
