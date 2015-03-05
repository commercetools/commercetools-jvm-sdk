package io.sphere.sdk.orders;

import com.neovisionaries.i18n.CountryCode;
import io.sphere.sdk.carts.CustomLineItem;
import io.sphere.sdk.carts.TaxedPrice;
import io.sphere.sdk.customergroups.CustomerGroup;
import io.sphere.sdk.models.Address;
import io.sphere.sdk.models.Reference;

import javax.money.MonetaryAmount;
import java.time.Instant;
import java.util.List;
import java.util.Optional;


/**
 * Draft for importing an Order.
 *
 * @see io.sphere.sdk.orders.ImportOrderBuilder
 */
public interface ImportOrder {
    Optional<Address> getBillingAddress();

    Optional<Instant> getCompletedAt();

    Optional<CountryCode> getCountry();

    Optional<String> getCustomerEmail();

    Optional<Reference<CustomerGroup>> getCustomerGroup();

    Optional<String> getCustomerId();

    List<CustomLineItem> getCustomLineItems();

    List<ImportLineItem> getLineItems();

    Optional<String> getOrderNumber();

    Optional<OrderState> getOrderState();

    Optional<PaymentState> getPaymentState();

    Optional<ShipmentState> getShipmentState();

    Optional<Address> getShippingAddress();

    Optional<OrderShippingInfo> getShippingInfo();

    Optional<TaxedPrice> getTaxedPrice();

    MonetaryAmount getTotalPrice();
}
