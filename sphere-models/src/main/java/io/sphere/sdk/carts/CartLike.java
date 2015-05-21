package io.sphere.sdk.carts;

import com.neovisionaries.i18n.CountryCode;
import io.sphere.sdk.customergroups.CustomerGroup;
import io.sphere.sdk.discountcodes.DiscountCodeReference;
import io.sphere.sdk.models.Address;
import io.sphere.sdk.models.DefaultModel;
import io.sphere.sdk.models.Reference;

import javax.money.MonetaryAmount;
import java.util.List;
import java.util.Optional;

/**
 * Interface to collect the common stuff between carts and orders.
 * @param <T> the type of this {@link CartLike}, order or cart
 */
public interface CartLike<T> extends DefaultModel<T> {

    Optional<String> getCustomerId();

    Optional<String> getCustomerEmail();

    List<LineItem> getLineItems();

    List<CustomLineItem> getCustomLineItems();

    default Optional<CustomLineItem> getCustomLineItem(final String customLineItemId) {
        return getCustomLineItems().stream().filter(item -> item.getId().equals(customLineItemId)).findAny();
    }

    default Optional<LineItem> getLineItem(final String lineItemId) {
        return getLineItems().stream().filter(item -> item.getId().equals(lineItemId)).findAny();
    }

    MonetaryAmount getTotalPrice();

    Optional<TaxedPrice> getTaxedPrice();

    Optional<Address> getShippingAddress();

    Optional<Address> getBillingAddress();

    Optional<Reference<CustomerGroup>> getCustomerGroup();

    Optional<CountryCode> getCountry();

    List<DiscountCodeReference> getDiscountCodes();
}
