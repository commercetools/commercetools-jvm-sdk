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
     *
     * {@include.example io.sphere.sdk.carts.ExternalTaxRatesIntegrationTest#createCartWithALineItemWithAnExternalTaxRate()}
     *
     * <p>Create empty cart</p>
     *
     * {@include.example io.sphere.sdk.carts.ExternalTaxRatesIntegrationTest#createEmptyCart()}
     *
     * <h3 id="add-line-item-with-external-tax-mode">Add a LineItem with an external tax rate</h3>
     *
     * {@include.example io.sphere.sdk.carts.ExternalTaxRatesIntegrationTest#addLineItem()}
     *
     * <p>Error case when the cart does not have the external tax but a line item is added</p>
     *
     * {@include.example io.sphere.sdk.carts.ExternalTaxRatesIntegrationTest#addLineItemOnPlatformCart()}
     *
     * <h3 id="add-line-item-with-delayed-external-tax-rate">Add a LineItem and add the external tax rate later</h3>
     *
     * {@include.example io.sphere.sdk.carts.ExternalTaxRatesIntegrationTest#setLineItemTaxRate()}
     *
     * <h3 id="add-custom-line-item-with-external-tax-mode">Add a CustomLineItem with an external tax rate</h3>
     *
     * {@include.example io.sphere.sdk.carts.ExternalTaxRatesIntegrationTest#addCustomLineItem()}
     *
     * <h3 id="add-line-item-with-delayed-external-tax-rate">Add a CustomLineItem and add the external tax rate later</h3>
     *
     * {@include.example io.sphere.sdk.carts.ExternalTaxRatesIntegrationTest#setCustomLineItemTaxRate()}
     *
     * <h3 id="add-custom-shipping-method-with-external-tax-mode">Add a custom shipping method with an external tax rate</h3>
     *
     * {@include.example io.sphere.sdk.carts.ExternalTaxRatesIntegrationTest#addCustomShippingMethod()}
     *
     * <h3 id="add-custom-shipping-method-with-delayed-external-tax-mode">Add a custom shipping method and add the external tax rate later</h3>
     *
     * {@include.example io.sphere.sdk.carts.ExternalTaxRatesIntegrationTest#setShippingMethodTaxRate()}
     *
     *
     *
     *
     *
     *
     * {@include.example }
     *
     * <!--
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
