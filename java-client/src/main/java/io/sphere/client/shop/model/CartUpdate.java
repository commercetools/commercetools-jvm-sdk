package io.sphere.client.shop.model;

import java.util.ArrayList;
import java.util.List;
import io.sphere.internal.command.CartCommands;
import com.neovisionaries.i18n.CountryCode;

/**
 * CartUpdate object used to update a cart in the backend.
 */
public class CartUpdate {
    private List<CartCommands.CartUpdateAction> actions = new ArrayList<CartCommands.CartUpdateAction>();

    /** Adds a product variant in the given quantity to the cart. */
    public CartUpdate addLineItem(int quantity, String productId, String variantId) {
        int vId;
        try {
            vId = Integer.parseInt(variantId);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid variant id: '" + variantId + "'");
        }
        assertNotNegative(quantity);

        this.actions.add(new CartCommands.AddLineItem(productId, quantity, vId));
        return this;
    }

    /** Adds a product master variant in the given quantity to the cart. */
    public CartUpdate addLineItem(int quantity, String productId) {
        assertNotNegative(quantity);
        this.actions.add(new CartCommands.AddLineItemFromMasterVariant(productId, quantity));
        return this;
    }

    /** Removes the line item from the cart. */
    public CartUpdate removeLineItem(String lineItemId) {
        this.actions.add(new CartCommands.RemoveLineItem(lineItemId));
        return this;
    }

    /** Decreases the quantity of the given line item. If after the update the quantity of the line item is not greater than 0
     * the line item is removed from the cart. */
    public CartUpdate decreaseLineItemQuantity(String lineItemId, int quantity) {
        assertNotNegative(quantity);
        this.actions.add(new CartCommands.DecreaseLineItemQuantity(lineItemId, quantity));
        return this;
    }

    /** Sets the quantity of the given line item. If quantity is 0, line item is removed from the cart. */
    public CartUpdate setLineItemQuantity(String lineItemId, int quantity) {
        assertNotNegative(quantity);
        this.actions.add(new CartCommands.SetLineItemQuantity(lineItemId, quantity));
        return this;
    }

    /** Sets the customer email in the cart. */
    public CartUpdate setCustomerEmail(String email) {
        this.actions.add(new CartCommands.SetCustomerEmail(email));
        return this;
    }

    /** Sets the shipping address of the cart. Setting the shipping address also sets the tax rates of the line items
     * and calculates the taxed price. */
    public CartUpdate setShippingAddress(Address address) {
        this.actions.add(new CartCommands.SetShippingAddress(address));
        return this;
    }

    /** Sets the billing address of the cart. */
    public CartUpdate setBillingAddress(Address address) {
        this.actions.add(new CartCommands.SetBillingAddress(address));
        return this;
    }

    /** Sets the country of the cart. When the country is set, the line item prices are updated. */
    public CartUpdate setCountry(CountryCode country) {
        this.actions.add(new CartCommands.SetCountry(country));
        return this;
    }

    /** Updates line item prices and tax rates. */
    public CartUpdate recalculate() {
        this.actions.add(new CartCommands.RecalculateCartPrices());
        return this;
    }

    /** Internal method, should not be called by the shop developer. */
    public CartCommands.UpdateCart createCommand(int version) {
        return new CartCommands.UpdateCart(version, actions);
    }

    private void assertNotNegative(int quantity) {
        if (quantity < 0) throw new IllegalArgumentException("Negative quantity not allowed.");
    }
}
