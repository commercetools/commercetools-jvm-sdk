package io.sphere.sdk.orders;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.neovisionaries.i18n.CountryCode;
import io.sphere.sdk.carts.*;
import io.sphere.sdk.customergroups.CustomerGroup;
import io.sphere.sdk.discountcodes.DiscountCodeInfo;
import io.sphere.sdk.models.Address;
import io.sphere.sdk.models.Reference;
import io.sphere.sdk.types.CustomFields;

import javax.annotation.Nullable;
import javax.money.MonetaryAmount;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Set;

/**
 An order can be created from a cart, usually after a checkout process has been completed. Orders can also be imported.

 <p>An order can have {@link io.sphere.sdk.types.Custom custom fields}.</p>


 */
@JsonDeserialize(as=OrderImpl.class)
public interface Order extends CartLike<Order> {
    static String resourceTypeId() {
        return "order";
    }

    static String referenceTypeId(){
        return "order";
    }

    /**
     *
     * @deprecated use {@link #referenceTypeId()} instead
     */
    @Deprecated
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
        return Reference.of(referenceTypeId(), getId(), this);
    }

    @Nullable
    String getOrderNumber();

    InventoryMode getInventoryMode();

    OrderState getOrderState();

    @Nullable
    ShipmentState getShipmentState();

    @Nullable
    PaymentState getPaymentState();

    @Nullable
    OrderShippingInfo getShippingInfo();

    Set<SyncInfo> getSyncInfo();

    List<ReturnInfo> getReturnInfo();

    Long getLastMessageSequenceNumber();

    @Override
    @Nullable
    Address getBillingAddress();

    @Override
    @Nullable
    CountryCode getCountry();

    @Override
    @Nullable
    String getCustomerEmail();

    @Override
    @Nullable
    Reference<CustomerGroup> getCustomerGroup();

    @Override
    @Nullable
    String getCustomerId();

    @Override
    List<CustomLineItem> getCustomLineItems();

    @Override
    List<LineItem> getLineItems();

    @Override
    @Nullable
    Address getShippingAddress();

    @Override
    @Nullable
    TaxedPrice getTaxedPrice();

    @Override
    MonetaryAmount getTotalPrice();

    ZonedDateTime getCompletedAt();

    /**
     * Set when this order was created from a cart. The cart will have the state Ordered.
     *
     * @return cart reference or null
     */
    @Nullable
    Reference<Cart> getCart();

    @Override
    List<DiscountCodeInfo> getDiscountCodes();

    @Nullable
    @Override
    CustomFields getCustom();
}
