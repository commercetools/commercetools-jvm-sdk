package io.sphere.sdk.carts;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.neovisionaries.i18n.CountryCode;
import io.sphere.sdk.annotations.IgnoreInQueryModel;
import io.sphere.sdk.carts.commands.updateactions.ChangeTaxMode;
import io.sphere.sdk.customergroups.CustomerGroup;
import io.sphere.sdk.customers.Customer;
import io.sphere.sdk.discountcodes.DiscountCodeInfo;
import io.sphere.sdk.models.Address;
import io.sphere.sdk.models.Reference;
import io.sphere.sdk.models.Resource;
import io.sphere.sdk.products.PriceUtils;
import io.sphere.sdk.types.Custom;
import io.sphere.sdk.types.CustomFields;

import javax.annotation.Nullable;
import javax.money.CurrencyUnit;
import javax.money.MonetaryAmount;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import static io.sphere.sdk.products.PriceUtils.zeroAmount;

/**
 * Interface to collect the common stuff between carts and orders.
 *
 * <p>If you want to use this class as abstraction for {@link Cart} and {@link io.sphere.sdk.orders.Order} then use it as {@code CartLike<?>}.</p>
 *
 * @param <T> the type of this {@link CartLike}, order or cart
 */
public interface CartLike<T> extends Resource<T>, Custom {

    /**
     * The ID of the customer to which this cart/order belongs to.
     *
     * @see io.sphere.sdk.customers.commands.CustomerSignInCommand
     * @see io.sphere.sdk.carts.commands.updateactions.SetCustomerId
     *
     * @return id of the customer or null
     */
    @Nullable
    String getCustomerId();

    /**
     * The email of the customer which should be used for this cart/order. It can differ from {@link Customer#getEmail()}.
     *
     * @see io.sphere.sdk.carts.commands.updateactions.SetCustomerEmail
     *
     * @return email
     */
    @Nullable
    String getCustomerEmail();

    /**
     * Snapshots of the products added to the cart.
     *
     * @see io.sphere.sdk.carts.commands.updateactions.AddLineItem
     * @see io.sphere.sdk.carts.commands.updateactions.ChangeLineItemQuantity
     * @see io.sphere.sdk.carts.commands.updateactions.RemoveLineItem
     *
     * @return line items
     */
    List<LineItem> getLineItems();

    /**
     * Custom goods, fees or discounts added to the cart.
     *
     * @see io.sphere.sdk.carts.commands.updateactions.AddCustomLineItem
     * @see io.sphere.sdk.carts.commands.updateactions.RemoveCustomLineItem
     *
     * @return custom line items
     */
    List<CustomLineItem> getCustomLineItems();

    /**
     * Searches for custom line items by ID of a custom line item.
     *
     * @param customLineItemId the ID of the custom line item to search for.
     * @return optional custom line item
     */
    default Optional<CustomLineItem> findCustomLineItem(final String customLineItemId) {
        return getCustomLineItems().stream().filter(item -> item.getId().equals(customLineItemId)).findAny();
    }

    /**
     * Searches for line items by ID of a line item.
     *
     * @param lineItemId the ID of the line item to search for.
     * @return optional line item
     */
    default Optional<LineItem> findLineItem(final String lineItemId) {
        return getLineItems().stream().filter(item -> item.getId().equals(lineItemId)).findAny();
    }

    MonetaryAmount getTotalPrice();

    /**
     * The taxed price.
     *
     * The shipping address ({@link #getShippingAddress()}) is used to determine tax rate of the line items.
     *
     *
     * @return taxed price or null
     */
    @Nullable
    TaxedPrice getTaxedPrice();

    /**
     * Address to ship the goods.
     *
     * @see io.sphere.sdk.carts.commands.updateactions.SetShippingAddress
     * @see #getTaxedPrice()
     *
     * @return address or null
     */
    @Nullable
    Address getShippingAddress();

    /**
     * Address for the bill.
     *
     * @see io.sphere.sdk.carts.commands.updateactions.SetBillingAddress
     *
     * @return address or null
     */
    @Nullable
    Address getBillingAddress();

    /**
     * In the order context the customer group can only be expanded if the customer was in a group before creating the order.
     *
     * @return reference of a customer group or null
     */
    @Nullable
    Reference<CustomerGroup> getCustomerGroup();

    @Nullable
    CountryCode getCountry();

    /**
     * Discount codes belonging to this cart.
     *
     * @see io.sphere.sdk.carts.commands.updateactions.AddDiscountCode
     *
     * @return discount codes
     */
    List<DiscountCodeInfo> getDiscountCodes();

    @Nullable
    CustomFields getCustom();

    /**
     * Information about shipping set by the backend once the shipping method is set.
     *
     * @see io.sphere.sdk.carts.commands.updateactions.SetShippingMethod
     * @see io.sphere.sdk.carts.commands.updateactions.SetCustomShippingMethod
     * @see io.sphere.sdk.orders.commands.updateactions.AddDelivery
     *
     * @return shipping info
     */
    @Nullable
    CartShippingInfo getShippingInfo();

    /**
     * Get associated payments.
     *
     * @return payments
     * @see io.sphere.sdk.carts.commands.updateactions.AddPayment
     * @see io.sphere.sdk.carts.commands.updateactions.RemovePayment
     * @see io.sphere.sdk.orders.commands.updateactions.AddPayment
     * @see io.sphere.sdk.orders.commands.updateactions.RemovePayment
     */
    @Nullable
    PaymentInfo getPaymentInfo();

    /**
     * Tax mode of this cart.
     *
     * @see ChangeTaxMode
     *
     * @return tax mode
     */
    @IgnoreInQueryModel
    TaxMode getTaxMode();


    /**
     * Tax calculation mode of this cart.
     *
     * @see io.sphere.sdk.carts.commands.updateactions.ChangeTaxCalculationMode
     *
     * @return TaxCalculationMode
     */
    @IgnoreInQueryModel
    TaxCalculationMode getTaxCalculationMode();



    /**
     * The currency of this cart/order.
     * @return currency
     */
    @JsonIgnore
    default CurrencyUnit getCurrency() {
        return getTotalPrice().getCurrency();
    }

    /**
     * Identifies carts and orders belonging to an anonymous session (the customer has not signed up/in yet).
     * @return anonymousId
     */
    @Nullable
    String getAnonymousId();

    @Nullable
    Locale getLocale();


    /**
     *  The shippingRateInput is used as an input to select a shipping rate price tier
     * @return shippingRateInput
     */
    @Nullable
    ShippingRateInput getShippingRateInput();

    /**
     * When calculating taxes in {@code taxedPrice}, the tax rounding mode is used for decimal values.
     * @return the tax rounding mode
     */
    RoundingMode getTaxRoundingMode();


    CartOrigin getOrigin();

    List<Address> getItemShippingAddresses();
    /**
     * Returns the subtotal price of the cart, which is calculated by adding the prices of line items and custom line items,
     * thus excluding shipping costs and discounts that are applied to the entire cart.
     * @return the estimated subtotal price of the cart
     */
    default MonetaryAmount calculateSubTotalPrice() {
        final MonetaryAmount lineItemTotal = getLineItems().stream()
                .map(LineItem::getTotalPrice)
                .reduce(zeroAmount(getCurrency()), MonetaryAmount::add);
        final MonetaryAmount customLineItemTotal = getCustomLineItems().stream()
                .map(CustomLineItem::getTotalPrice)
                .reduce(zeroAmount(getCurrency()), MonetaryAmount::add);
        return lineItemTotal.add(customLineItemTotal);
    }

    /**
     * Tries to calculate all the taxes applied to the cart, without discriminating between different tax portions.
     * Only possible if taxes have already been applied to the cart.
     * @return the taxes applied to the cart, or absent if taxes have not been applied yet
     */
    default Optional<MonetaryAmount> calculateTotalAppliedTaxes() {
        return Optional.ofNullable(getTaxedPrice())
                .map(PriceUtils::calculateAppliedTaxes);
    }
}
