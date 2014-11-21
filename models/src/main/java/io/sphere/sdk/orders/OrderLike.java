package io.sphere.sdk.orders;

import com.neovisionaries.i18n.CountryCode;
import io.sphere.sdk.carts.CustomLineItem;
import io.sphere.sdk.carts.LineItem;
import io.sphere.sdk.carts.TaxedPrice;
import io.sphere.sdk.customergroups.CustomerGroup;
import io.sphere.sdk.models.Address;
import io.sphere.sdk.models.DefaultModel;
import io.sphere.sdk.models.Reference;

import javax.money.MonetaryAmount;
import java.util.List;
import java.util.Optional;

public interface OrderLike<T> extends DefaultModel<T> {

    Optional<String> getCustomerId();

    Optional<String> getCustomerEmail();

    List<LineItem> getLineItems();

    List<CustomLineItem> getCustomLineItems();

    MonetaryAmount getTotalPrice();

    Optional<TaxedPrice> getTaxedPrice();

    Optional<Address> getShippingAddress();

    Optional<Address> getBillingAddress();

    Optional<Reference<CustomerGroup>> getCustomerGroup();

    Optional<CountryCode> getCountry();
}
