package io.sphere.sdk.carts;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.sphere.sdk.models.LocalizedString;
import io.sphere.sdk.models.Referenceable;
import io.sphere.sdk.models.ResourceIdentifier;
import io.sphere.sdk.taxcategories.ExternalTaxRateDraft;
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

    /**
     * Gets the tax category for this custom line item.
     *
     * @return tax category or null
     * @see TaxMode
     */
    @Nullable
    ResourceIdentifier<TaxCategory> getTaxCategory();

    /**
     * Possible custom tax rate.
     * If set, this tax rate will override the tax rate selected by the platform.
     *
     * @return external tax rate or null
     * @see #getTaxCategory()
     * @see TaxMode
     */
    @Nullable
    ExternalTaxRateDraft getExternalTaxRate();

    Long getQuantity();

    @Nullable
    @Override
    CustomFieldsDraft getCustom();

    /**
     * Container for the sub-quantity of the line item quantity for the specific address when multiple shipping addresses are required.
     * @return ItemShippingDetailsDraft
     */
    ItemShippingDetailsDraft getShippingDetails();
    /**
     * Creates a draft having a standard tax category and no custom fields.
     * @param name the display name of the custom line item (corresponds to a product name)
     * @param slug unique field in the cart which is intended to identify the custom line item (not translated)
     * @param money the amount of the custom line item
     * @param taxCategory the tax category of the custom line item
     * @param quantity the count of items of this line item in the cart
     * @return draft
     */
    static CustomLineItemDraft of(final LocalizedString name, final String slug, final MonetaryAmount money,
                                  final Referenceable<TaxCategory> taxCategory, final long quantity) {
        return of(name, slug, money, taxCategory, quantity, null);
    }

    /**
     * Creates a draft having a standard tax category and custom fields.
     * @param name the display name of the custom line item (corresponds to a product name)
     * @param slug unique field in the cart which is intended to identify the custom line item (not translated)
     * @param money the amount of the custom line item
     * @param taxCategory the tax category of the custom line item
     * @param quantity the count of items of this line item in the cart
     * @param custom custom fields for the custom line item
     * @return draft
     */
    static CustomLineItemDraft of(final LocalizedString name, final String slug, final MonetaryAmount money,
                                  final Referenceable<TaxCategory> taxCategory, final long quantity,
                                  @Nullable final CustomFieldsDraft custom) {
        return new CustomLineItemDraftImpl(name, slug, money, taxCategory.toResourceIdentifier(), quantity, custom, null,null);
    }

    /**
     * Creates a draft having an external tax rate and no custom fields.
     * @param name the display name of the custom line item (corresponds to a product name)
     * @param slug unique field in the cart which is intended to identify the custom line item (not translated)
     * @param money the amount of the custom line item
     * @param externalTaxRate tax rate specific for this line item if the standard tax rates do not suffice
     * @param quantity the count of items of this line item in the cart
     * @return draft
     */
    static CustomLineItemDraft ofExternalTaxCalculation(final LocalizedString name, final String slug, final MonetaryAmount money,
                                                        final ExternalTaxRateDraft externalTaxRate, final long quantity) {
        return new CustomLineItemDraftImpl(name, slug, money, null, quantity, null, externalTaxRate,null);
    }

    /**
     * Creates a draft having no tax rate (for external tax calculation) and no custom fields.
     * @param name the display name of the custom line item (corresponds to a product name)
     * @param slug unique field in the cart which is intended to identify the custom line item (not translated)
     * @param money the amount of the custom line item
     * @param quantity the count of items of this line item in the cart
     * @return draft
     */
    static CustomLineItemDraft ofExternalTaxCalculation(final LocalizedString name, final String slug, final MonetaryAmount money,
                                                        final long quantity) {
        return new CustomLineItemDraftImpl(name, slug, money, null, quantity, null, null,null);
    }

    /**
     * Creates a draft having an external tax rate and custom fields.
     * @param name the display name of the custom line item (corresponds to a product name)
     * @param slug unique field in the cart which is intended to identify the custom line item (not translated)
     * @param money the amount of the custom line item
     * @param externalTaxRate tax rate specific for this line item if the standard tax rates do not suffice
     * @param quantity the count of items of this line item in the cart
     * @param custom custom fields for the custom line item
     * @return draft
     */
    static CustomLineItemDraft ofExternalTaxCalculation(final LocalizedString name, final String slug, final MonetaryAmount money,
                                                        final ExternalTaxRateDraft externalTaxRate, final long quantity,
                                                        @Nullable final CustomFieldsDraft custom) {
        return new CustomLineItemDraftImpl(name, slug, money, null, quantity, custom, externalTaxRate,null);
    }

    /**
     * Creates a draft having no tax rate (for external tax calculation) and custom fields.
     * @param name the display name of the custom line item (corresponds to a product name)
     * @param slug unique field in the cart which is intended to identify the custom line item (not translated)
     * @param money the amount of the custom line item
     * @param quantity the count of items of this line item in the cart
     * @param custom custom fields for the custom line item
     * @return draft
     *
     * @see io.sphere.sdk.carts.commands.updateactions.SetCustomLineItemTaxRate
     * @see CustomLineItemDraft
     */
    static CustomLineItemDraft ofExternalTaxCalculation(final LocalizedString name, final String slug, final MonetaryAmount money,
                                                        final long quantity,
                                                        @Nullable final CustomFieldsDraft custom) {
        return new CustomLineItemDraftImpl(name, slug, money, null, quantity, custom, null,null);
    }
}
