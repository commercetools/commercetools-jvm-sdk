package io.sphere.sdk.carts;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.neovisionaries.i18n.CountryCode;
import io.sphere.sdk.customergroups.CustomerGroup;
import io.sphere.sdk.models.Address;
import io.sphere.sdk.models.Reference;
import io.sphere.sdk.orders.OrderLike;

import javax.money.MonetaryAmount;
import java.util.List;
import java.util.Optional;

@JsonDeserialize(as=CartImpl.class)
public interface Cart extends OrderLike<Cart> {

    public static String typeId(){
        return "cart";
    }

    public static TypeReference<Cart> typeReference() {
        return new TypeReference<Cart>() {
            @Override
            public String toString() {
                return "TypeReference<Cart>";
            }
        };
    }

    @Override
    default Reference<Cart> toReference() {
        return Reference.of(typeId(), getId(), this);
    }

    CartState getCartState();

    InventoryMode getInventoryMode();

    Optional<CartShippingInfo> getShippingInfo();

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
}
