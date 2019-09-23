package io.sphere.sdk.orders;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.neovisionaries.i18n.CountryCode;
import io.sphere.sdk.carts.*;
import io.sphere.sdk.customergroups.CustomerGroup;
import io.sphere.sdk.models.Address;
import io.sphere.sdk.models.KeyReference;
import io.sphere.sdk.models.Reference;
import io.sphere.sdk.stores.Store;
import io.sphere.sdk.types.CustomFieldsDraft;

import javax.annotation.Nullable;
import javax.money.MonetaryAmount;
import java.time.ZonedDateTime;
import java.util.List;


/**
 * Draft for importing an Order.
 *
 * @see OrderImportDraftBuilder
 */
@JsonDeserialize(as = OrderImportDraftImpl.class)
public interface OrderImportDraft {
    @Nullable
    Address getBillingAddress();

    ZonedDateTime getCompletedAt();

    @Nullable
    CountryCode getCountry();

    @Nullable
    String getCustomerEmail();

    @Nullable
    Reference<CustomerGroup> getCustomerGroup();

    @Nullable
    String getCustomerId();

    List<CustomLineItemImportDraft> getCustomLineItems();

    List<LineItemImportDraft> getLineItems();

    @Nullable
    String getOrderNumber();

    OrderState getOrderState();

    @Nullable
    PaymentState getPaymentState();

    @Nullable
    ShipmentState getShipmentState();

    @Nullable
    Address getShippingAddress();

    @Nullable
    ShippingInfoImportDraft getShippingInfo();

    @Nullable
    TaxedPrice getTaxedPrice();

    MonetaryAmount getTotalPrice();

    @Nullable
    CustomFieldsDraft getCustom();

    @Nullable
    RoundingMode getTaxRoundingMode();

    @Nullable
    InventoryMode getInventoryMode();

    @Nullable
    TaxCalculationMode getTaxCalculationMode();

    @Nullable
    CartOrigin getOrigin();

    @Nullable
    List<Address> getItemShippingAddresses();

    @Nullable
    KeyReference<Store> getStore();
}
