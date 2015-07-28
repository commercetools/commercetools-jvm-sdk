package io.sphere.sdk.carts.commands.updateactions;

import io.sphere.sdk.carts.Cart;
import io.sphere.sdk.carts.CustomLineItemDraft;
import io.sphere.sdk.commands.UpdateAction;
import io.sphere.sdk.models.LocalizedStrings;
import io.sphere.sdk.models.Reference;
import io.sphere.sdk.models.Referenceable;
import io.sphere.sdk.taxcategories.TaxCategory;

import javax.money.MonetaryAmount;

/**
 Adds a CustomLineItem to the cart.

 {@include.example io.sphere.sdk.carts.commands.CartUpdateCommandTest#addCustomLineItem()}
 */
public class AddCustomLineItem extends UpdateAction<Cart> {
    private final LocalizedStrings name;
    private final Long quantity;
    private final MonetaryAmount money;
    private final String slug;
    private final Reference<TaxCategory> taxCategory;

    private AddCustomLineItem(final LocalizedStrings name, final String slug,
                              final MonetaryAmount money, final Referenceable<TaxCategory> taxCategory,
                              final Long quantity) {
        super("addCustomLineItem");
        this.name = name;
        this.quantity = quantity;
        this.money = money;
        this.slug = slug;
        this.taxCategory = taxCategory.toReference();
    }

    public static AddCustomLineItem of(final LocalizedStrings name, final String slug,
                                       final MonetaryAmount money, final Referenceable<TaxCategory> taxCategory,
                                       final long quantity) {
        return new AddCustomLineItem(name, slug, money, taxCategory, quantity);
    }

    public static AddCustomLineItem of(final CustomLineItemDraft draft) {
        return of(draft.getName(), draft.getSlug(), draft.getMoney(),
                draft.getTaxCategory(), draft.getQuantity());
    }

    public LocalizedStrings getName() {
        return name;
    }

    public Long getQuantity() {
        return quantity;
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
}
