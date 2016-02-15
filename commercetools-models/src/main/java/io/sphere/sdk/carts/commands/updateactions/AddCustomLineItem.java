package io.sphere.sdk.carts.commands.updateactions;

import io.sphere.sdk.carts.Cart;
import io.sphere.sdk.carts.CustomLineItemDraft;
import io.sphere.sdk.commands.UpdateActionImpl;
import io.sphere.sdk.models.LocalizedString;
import io.sphere.sdk.models.Reference;
import io.sphere.sdk.models.Referenceable;
import io.sphere.sdk.taxcategories.TaxCategory;
import io.sphere.sdk.types.CustomDraft;
import io.sphere.sdk.types.CustomFieldsDraft;

import javax.annotation.Nullable;
import javax.money.MonetaryAmount;

/**
 Adds a CustomLineItem to the cart.

 {@doc.gen intro}

 <p>Add a simple {@link io.sphere.sdk.carts.CustomLineItem}:</p>
 {@include.example io.sphere.sdk.carts.commands.CartUpdateCommandIntegrationTest#addCustomLineItem()}

 <p>Add a {@link io.sphere.sdk.carts.CustomLineItem} with {@link io.sphere.sdk.types.CustomFields}:</p>
 {@include.example io.sphere.sdk.carts.CustomLineItemCustomFieldsIntegrationTest#creation()}

 @see Cart#getCustomLineItems()
 @see RemoveCustomLineItem
 */
public final class AddCustomLineItem extends UpdateActionImpl<Cart> implements CustomDraft {
    private final LocalizedString name;
    private final Long quantity;
    private final MonetaryAmount money;
    private final String slug;
    private final Reference<TaxCategory> taxCategory;
    @Nullable
    private final CustomFieldsDraft custom;

    private AddCustomLineItem(final LocalizedString name, final String slug,
                              final MonetaryAmount money, final Referenceable<TaxCategory> taxCategory,
                              final Long quantity, @Nullable final CustomFieldsDraft custom) {
        super("addCustomLineItem");
        this.name = name;
        this.quantity = quantity;
        this.money = money;
        this.slug = slug;
        this.custom = custom;
        this.taxCategory = taxCategory.toReference();
    }

    public static AddCustomLineItem of(final LocalizedString name, final String slug,
                                       final MonetaryAmount money, final Referenceable<TaxCategory> taxCategory,
                                       final long quantity) {
        return of(name, slug, money, taxCategory, quantity, null);
    }

    public static AddCustomLineItem of(final LocalizedString name, final String slug,
                                       final MonetaryAmount money, final Referenceable<TaxCategory> taxCategory,
                                       final long quantity, @Nullable final CustomFieldsDraft custom) {
        return new AddCustomLineItem(name, slug, money, taxCategory, quantity, custom);
    }

    public static AddCustomLineItem of(final CustomLineItemDraft draft) {
        return of(draft.getName(), draft.getSlug(), draft.getMoney(),
                draft.getTaxCategory(), draft.getQuantity(), draft.getCustom());
    }

    public LocalizedString getName() {
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

    @Nullable
    public CustomFieldsDraft getCustom() {
        return custom;
    }

    public AddCustomLineItem withCustom(@Nullable final CustomFieldsDraft custom) {
        return new AddCustomLineItem(getName(), getSlug(), getMoney(), getTaxCategory(), getQuantity(), custom);
    }
}
