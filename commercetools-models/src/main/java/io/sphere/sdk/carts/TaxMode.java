package io.sphere.sdk.carts;

import com.fasterxml.jackson.annotation.JsonCreator;
import io.sphere.sdk.models.SphereEnumeration;
import io.sphere.sdk.taxcategories.TaxCategory;

public enum TaxMode implements SphereEnumeration {
    /**
     * The tax rates are selected by the platform from the {@link TaxCategory tax categories} based on the cart shipping address.
     */
    PLATFORM,

    /**
     * The tax rates are set externally. A cart with this tax mode can only be ordered if all line items, all custom line items and the shipping method have an external tax rate set.
     *
     * <h3 id="create-cart-with-external-tax-mode">Create a cart with external tax mode</h3>
     *
     * <p>Cart creation with line item</p>
     * {@include.example io.sphere.sdk.carts.ExternalTaxRatesIntegrationTest#createCartWithALineItemWithAnExternalTaxRate()}
     *
     * <p>Create empty cart</p>
     *
     * {@include.example io.sphere.sdk.carts.ExternalTaxRatesIntegrationTest#createEmptyCart()}
     *
     * <h3 id="add-line-item-with-external-tax-mode">Add a LineItem</h3>
     *
     * {@include.example io.sphere.sdk.carts.ExternalTaxRatesIntegrationTest#addLineItem()}
     *
     *
     *
     *
     *
     * {@include.example }
     * {@include.example }
     *
     * <!--
     *
     * - fill this cart with line item and custom rate
     *   - error case, cart with platform mode and then add line item with tax rate
     * - fill custom line item
     * - set custom shipping method
     * - update line item rate
     * - update custom line item rate
     * - update shipping method rate
     *   - also for normal one?
     *   - check taxedPrice
     * - change tax mode for the cart
     *
     * -->
     *
     */
    EXTERNAL,

    /**
     * No taxes are added to the cart.
     */
    DISABLED;
    @JsonCreator
    public static TaxMode ofSphereValue(final String value) {
        return SphereEnumeration.findBySphereName(values(), value).get();
    }
}
