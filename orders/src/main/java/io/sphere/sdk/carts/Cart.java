package io.sphere.sdk.carts;

import com.fasterxml.jackson.core.type.TypeReference;
import com.neovisionaries.i18n.CountryCode;
import io.sphere.sdk.customergroups.CustomerGroup;
import io.sphere.sdk.models.Address;
import io.sphere.sdk.models.DefaultModel;
import io.sphere.sdk.models.Reference;

import javax.money.MonetaryAmount;
import java.util.List;
import java.util.Optional;

public interface Cart extends DefaultModel<Cart> {

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
        return new Reference<>(typeId(), getId(), Optional.of(this));
    }

    Optional<String> getCustomerId();

    Optional<String> getCustomerEmail();

    List<LineItem> getLineItems();

    List<CustomLineItem> getCustomLineItems();

    MonetaryAmount getTotalPrice();

    Optional<TaxedPrice> getTaxedPrice();

    CartState getCartState();

    Optional<Address> getShippingAddress();

    Optional<Address> getBillingAddress();

    InventoryMode getInventoryMode();

    Optional<Reference<CustomerGroup>> getCustomerGroup();

    Optional<CountryCode> getCountry();

    Optional<ShippingInfo> getShippingInfo();
}
