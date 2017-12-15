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
     * <h3 id="change-the-tax-mode-of-a-cart">Change the tax mode of a cart</h3>
     * <p>Example</p>
     * {@include.example io.sphere.sdk.carts.ExternalTaxRatesIntegrationTest#changeTaxMode()}
     *
     * <p>It fails if the products does not have a tax category set</p>
     * {@include.example io.sphere.sdk.carts.ExternalTaxRatesIntegrationTest#errorMovingFromExternalToPlatformTaxMode()}
     *
     * <p>In case you use {@link CustomLineItem}s as well as external and the platform tax mode it is advised to
     * add the tax category at the time of the creation of the custom line item if you don't know which tax mode will be used later.
     * A cart with an external tax mode having a custom line item without a tax category cannot transformed into a cart with platform tax calculation,
     * to recover from this the custom object needs to be removed and added with a tax category.</p>
     *
     */
    EXTERNAL,

    /**
     * The tax amounts and the tax rates as well as the tax portions are set externally per {@link ExternalTaxAmountDraft}.
     *
     * A cart with this tax mode can only be ordered if the cart itself and all line items,
     * all custom line items and the shipping method have an external tax amount and rate set.
     */
    EXTERNAL_AMOUNT,

    /**
     * No taxes are added to the cart.
     */
    DISABLED;

    @JsonCreator
    public static TaxMode ofSphereValue(final String value) {
        return SphereEnumeration.findBySphereName(values(), value).get();
    }
}
