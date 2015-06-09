package io.sphere.sdk.orders;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.neovisionaries.i18n.CountryCode;
import io.sphere.sdk.carts.*;
import io.sphere.sdk.customergroups.CustomerGroup;
import io.sphere.sdk.models.Address;
import io.sphere.sdk.models.Reference;

import javax.money.MonetaryAmount;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@JsonDeserialize(as=OrderImpl.class)
public interface Order extends CartLike<Order> {
    static String typeId(){
        return "order";
    }

    static TypeReference<Order> typeReference() {
        return new TypeReference<Order>() {
            @Override
            public String toString() {
                return "TypeReference<Order>";
            }
        };
    }

    @Override
    default Reference<Order> toReference() {
        return Reference.of(typeId(), getId(), this);
    }

    Optional<String> getOrderNumber();

    InventoryMode getInventoryMode();

    OrderState getOrderState();

    Optional<ShipmentState> getShipmentState();

    Optional<PaymentState> getPaymentState();

    Optional<OrderShippingInfo> getShippingInfo();

    Set<SyncInfo> getSyncInfo();

    List<ReturnInfo> getReturnInfo();

    long getLastMessageSequenceNumber();

    @Override
    Optional<Address> getBillingAddress();

    @Override
    Optional<CountryCode> getCountry();

    @Override
    Optional<String> getCustomerEmail();

    @Override
    Optional<Reference<CustomerGroup>> getCustomerGroup();

    @Override
    Optional<String> getCustomerId();

    @Override
    List<CustomLineItem> getCustomLineItems();

    @Override
    List<LineItem> getLineItems();

    @Override
    Optional<Address> getShippingAddress();

    @Override
    Optional<TaxedPrice> getTaxedPrice();

    @Override
    MonetaryAmount getTotalPrice();

    Optional<ZonedDateTime> getCompletedAt();
}
