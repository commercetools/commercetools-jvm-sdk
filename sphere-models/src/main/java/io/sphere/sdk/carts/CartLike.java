package io.sphere.sdk.carts;

import com.neovisionaries.i18n.CountryCode;
import io.sphere.sdk.customergroups.CustomerGroup;
import io.sphere.sdk.discountcodes.DiscountCodeInfo;
import io.sphere.sdk.models.Address;
import io.sphere.sdk.models.Resource;
import io.sphere.sdk.models.Reference;
import io.sphere.sdk.types.Custom;
import io.sphere.sdk.types.CustomFields;

import javax.annotation.Nullable;
import javax.money.CurrencyUnit;
import javax.money.MonetaryAmount;
import java.util.List;
import java.util.Optional;

/**
 * Interface to collect the common stuff between carts and orders.
 *
 * @param <T> the type of this {@link CartLike}, order or cart
 */
public interface CartLike<T> extends Resource<T>, Custom {

    @Nullable
    String getCustomerId();

    @Nullable
    String getCustomerEmail();

    List<LineItem> getLineItems();

    List<CustomLineItem> getCustomLineItems();

    default Optional<CustomLineItem> findCustomLineItem(final String customLineItemId) {
        return getCustomLineItems().stream().filter(item -> item.getId().equals(customLineItemId)).findAny();
    }

    default Optional<LineItem> findLineItem(final String lineItemId) {
        return getLineItems().stream().filter(item -> item.getId().equals(lineItemId)).findAny();
    }

    MonetaryAmount getTotalPrice();

    @Nullable
    TaxedPrice getTaxedPrice();

    @Nullable
    Address getShippingAddress();

    @Nullable
    Address getBillingAddress();

    /**
     * In the order context the customer group can only be expanded if the customer was in a group before creating the order.
     *
     * @return optional reference of a customer group
     */
    @Nullable
    Reference<CustomerGroup> getCustomerGroup();

    @Nullable
    CountryCode getCountry();

    List<DiscountCodeInfo> getDiscountCodes();

    @Nullable
    CustomFields getCustom();

    @Nullable
    CartShippingInfo getShippingInfo();

    @Nullable
    PaymentInfo getPaymentInfo();

    default CurrencyUnit getCurrency() {
        return getTotalPrice().getCurrency();
    }
}
