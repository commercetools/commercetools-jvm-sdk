package io.sphere.client.shop.model;

import io.sphere.client.model.LocalizedString;
import io.sphere.client.model.Money;
import io.sphere.client.model.ReferenceId;
import io.sphere.internal.command.CartCommands;
import io.sphere.internal.command.Update;
import com.neovisionaries.i18n.CountryCode;

/**
 * CartUpdate is object used to update a {@link Cart} in the backend.
 */
public class CartUpdate extends Update<CartCommands.CartUpdateAction> {

    /** Adds a product variant in the given quantity to the cart. */
    public CartUpdate addLineItem(int quantity, String productId, int variantId) {
        return addLineItem(quantity, productId, variantId, null);
    }

    /** Adds a product variant in the given quantity to the cart with a reference to a Channel. */
    public CartUpdate addLineItem(int quantity, String productId, int variantId, final String channelId) {
        assertNotNegative(quantity);
        add(new CartCommands.AddLineItem(productId, quantity, variantId, channelId));
        return this;
    }

    /** Adds a product master variant in the given quantity to the cart. */
    public CartUpdate addLineItem(int quantity, String productId) {
        return addLineItem(quantity, productId, null);
    }

    /** Adds a product master variant in the given quantity to the cart with a reference to a Channel. */
    public CartUpdate addLineItem(int quantity, String productId, final String channelId) {
        assertNotNegative(quantity);
        add(new CartCommands.AddLineItemFromMasterVariant(productId, quantity, channelId));
        return this;
    }

    /** Removes the line item from the cart. */
    public CartUpdate removeLineItem(String lineItemId) {
        add(new CartCommands.RemoveLineItem(lineItemId));
        return this;
    }

    /** Adds a custom line item in the given quantity to the cart. */
    public CartUpdate addCustomLineItem(LocalizedString name, Money money, String slug, ReferenceId<TaxCategory> taxCategory, int quantity) {
        assertNotNegative(quantity);
        add(new CartCommands.AddCustomLineItem(name, money, slug, taxCategory, quantity));
        return this;
    }

    /** Removes the custom line item from the cart. */
    public CartUpdate removeCustomLineItem(String lineItemId) {
        add(new CartCommands.RemoveCustomLineItem(lineItemId));
        return this;
    }

    /** Decreases the quantity of the given line item. If after the update the quantity of the line item is not greater than 0
     * the line item is removed from the cart. */
    public CartUpdate decreaseLineItemQuantity(String lineItemId, int quantity) {
        assertNotNegative(quantity);
        add(new CartCommands.DecreaseLineItemQuantity(lineItemId, quantity));
        return this;
    }

    /** Sets the quantity of the given line item. If quantity is 0, line item is removed from the cart. */
    public CartUpdate setLineItemQuantity(String lineItemId, int quantity) {
        assertNotNegative(quantity);
        add(new CartCommands.ChangeLineItemQuantity(lineItemId, quantity));
        return this;
    }

    /** Sets the customer email in the cart. */
    public CartUpdate setCustomerEmail(String email) {
        add(new CartCommands.SetCustomerEmail(email));
        return this;
    }

    /** Sets the shipping address of the cart. Setting the shipping address also sets the tax rates of the line items
     * and calculates the taxed price. */
    public CartUpdate setShippingAddress(Address address) {
        add(new CartCommands.SetShippingAddress(address));
        return this;
    }

    /** Sets the billing address of the cart. */
    public CartUpdate setBillingAddress(Address address) {
        add(new CartCommands.SetBillingAddress(address));
        return this;
    }

    /** Sets the country of the cart. When the country is set, the line item prices are updated. */
    public CartUpdate setCountry(CountryCode country) {
        add(new CartCommands.SetCountry(country));
        return this;
    }

    /** Sets the shipping method of the cart. When the shipping method is set, 
     * the backend will set the cart shippingInfo and updates the cart total. */
    public CartUpdate setShippingMethod(ReferenceId<ShippingMethod> shippingMethod) {
        add(new CartCommands.SetShippingMethod(shippingMethod));
        return this;
    }

    /** Sets the custom shipping method (not using project shipping methods). When the shipping method is set, 
     * the backend will set the cart shippingInfo and updates the cart total.*/
    public CartUpdate setCustomShippingMethod(String shippingMethodName, ShippingRate shippingRate, ReferenceId<TaxCategory> taxCategory) {
        add(new CartCommands.SetCustomShippingMethod(shippingMethodName, shippingRate, taxCategory));
        return this;
    }

    /** Sets the tracking data, some info about the delivery (like a DHL tracking number) which is useful to keep an eye 
     * on your delivery, view its status etc.*/
    public CartUpdate setTrackingData(String trackingData) {
        add(new CartCommands.SetTrackingData(trackingData));
        return this;
    }

    /** Updates line item prices and tax rates. */
    public CartUpdate recalculate() {
        add(new CartCommands.RecalculateCartPrices());
        return this;
    }

    private void assertNotNegative(int quantity) {
        if (quantity < 0) throw new IllegalArgumentException("Negative quantity not allowed.");
    }
}
