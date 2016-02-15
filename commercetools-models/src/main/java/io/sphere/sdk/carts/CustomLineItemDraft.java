package io.sphere.sdk.carts;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
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
 {@include.example io.sphere.sdk.carts.commands.CartUpdateCommandIntegrationTest#addCustomLineItem()}

 @see CustomLineItem
 */
@JsonDeserialize(as = CustomLineItemDraftImpl.class)
public interface CustomLineItemDraft extends CustomDraft {
    LocalizedString getName();

    MonetaryAmount getMoney();

    String getSlug();

    Reference<TaxCategory> getTaxCategory();

    Long getQuantity();

    @Nullable
    @Override
    CustomFieldsDraft getCustom();

    static CustomLineItemDraft of(final LocalizedString name, final String slug, final MonetaryAmount money, final Referenceable<TaxCategory> taxCategory, final long quantity) {
        return of(name, slug, money, taxCategory, quantity, null);
    }

    static CustomLineItemDraft of(final LocalizedString name, final String slug, final MonetaryAmount money, final Referenceable<TaxCategory> taxCategory, final long quantity, @Nullable final CustomFieldsDraft custom) {
        return new CustomLineItemDraftImpl(name, slug, money, taxCategory.toReference(), quantity, custom);
    }
}
