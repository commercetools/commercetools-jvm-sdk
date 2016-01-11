package io.sphere.sdk.carts;

import com.fasterxml.jackson.annotation.JsonCreator;
import io.sphere.sdk.models.Base;
import io.sphere.sdk.models.LocalizedString;
import io.sphere.sdk.models.Reference;
import io.sphere.sdk.models.Referenceable;
import io.sphere.sdk.taxcategories.TaxCategory;
import io.sphere.sdk.types.CustomDraft;
import io.sphere.sdk.types.CustomFieldsDraft;

import javax.annotation.Nullable;
import javax.money.MonetaryAmount;

/**
 * <p>Draft for adding a custom line item to the cart.</p>
 *
 *  <p>Example for adding a {@link CustomLineItem} to a {@link Cart}:</p>
 {@include.example io.sphere.sdk.carts.commands.CartUpdateCommandTest#addCustomLineItem()}

 @see CustomLineItem
 */
public class CustomLineItemDraft extends Base implements CustomDraft {
    private final LocalizedString name;
    private final MonetaryAmount money;
    private final String slug;
    private final Reference<TaxCategory> taxCategory;
    private final Long quantity;
    @Nullable
    private final CustomFieldsDraft custom;

    @JsonCreator
    private CustomLineItemDraft(final LocalizedString name, final String slug, final MonetaryAmount money, final Reference<TaxCategory> taxCategory, final Long quantity, @Nullable final CustomFieldsDraft custom) {
        this.name = name;
        this.money = money;
        this.slug = slug;
        this.custom = custom;
        this.taxCategory = taxCategory;
        this.quantity = quantity;
    }

    public static CustomLineItemDraft of(final LocalizedString name, final String slug, final MonetaryAmount money, final Referenceable<TaxCategory> taxCategory, final long quantity) {
        return of(name, slug, money, taxCategory, quantity, null);
    }

    public static CustomLineItemDraft of(final LocalizedString name, final String slug, final MonetaryAmount money, final Referenceable<TaxCategory> taxCategory, final long quantity, @Nullable final CustomFieldsDraft custom) {
        return new CustomLineItemDraft(name, slug, money, taxCategory.toReference(), quantity, custom);
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

    @Nullable
    @Override
    public CustomFieldsDraft getCustom() {
        return custom;
    }
}
