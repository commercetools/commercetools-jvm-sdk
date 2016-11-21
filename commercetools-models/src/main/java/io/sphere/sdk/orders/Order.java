package io.sphere.sdk.orders;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.neovisionaries.i18n.CountryCode;
import io.sphere.sdk.annotations.HasQueryEndpoint;
import io.sphere.sdk.annotations.ResourceInfo;
import io.sphere.sdk.annotations.ResourceValue;
import io.sphere.sdk.carts.*;
import io.sphere.sdk.customergroups.CustomerGroup;
import io.sphere.sdk.discountcodes.DiscountCodeInfo;
import io.sphere.sdk.models.Address;
import io.sphere.sdk.models.Reference;
import io.sphere.sdk.orders.messages.OrderBillingAddressSetMessage;
import io.sphere.sdk.orders.messages.OrderShippingAddressSetMessage;
import io.sphere.sdk.states.State;
import io.sphere.sdk.types.CustomFields;
import io.sphere.sdk.types.TypeDraft;

import javax.annotation.Nullable;
import javax.money.MonetaryAmount;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Set;

/**
 An order can be created from a cart, usually after a checkout process has been completed. Orders can also be imported.

 <p>An order can have {@link io.sphere.sdk.types.Custom custom fields}.</p>

@see Cart
@see io.sphere.sdk.orders.commands.OrderFromCartCreateCommand
@see io.sphere.sdk.orders.commands.OrderDeleteCommand
@see io.sphere.sdk.orders.commands.OrderImportCommand
@see io.sphere.sdk.orders.commands.OrderUpdateCommand
@see io.sphere.sdk.orders.queries.OrderByIdGet
@see io.sphere.sdk.orders.queries.OrderQuery
 */
@JsonDeserialize(as=OrderImpl.class)
@ResourceValue
@HasQueryEndpoint(additionalContents = {"\n" +
        "    default OrderQuery byCustomerId(final String customerId) {\n" +
        "        return withPredicates(m -> m.customerId().is(customerId));\n" +
        "    }\n" +
        "\n" +
        "    default OrderQuery byCustomerEmail(final String customerEmail) {\n" +
        "        return withPredicates(m -> m.customerEmail().is(customerEmail));\n" +
        "    }"})
@ResourceInfo(pluralName = "orders", pathElement = "orders")
public interface Order extends CartLike<Order> {
    /**
     * An identifier for this resource which supports {@link CustomFields}.
     * @see TypeDraft#getResourceTypeIds()
     * @see io.sphere.sdk.types.Custom
     * @return ID of this resource type
     */
    static String resourceTypeId() {
        return "order";
    }

    /**
     * A type hint for references which resource type is linked in a reference.
     * @see Reference#getTypeId()
     * @return type hint
     */
    static String referenceTypeId() {
        return "order";
    }

    /**
     * Creates a container which contains the full Java type information to deserialize this class from JSON.
     *
     * @see io.sphere.sdk.json.SphereJsonUtils#readObject(byte[], TypeReference)
     * @see io.sphere.sdk.json.SphereJsonUtils#readObject(String, TypeReference)
     * @see io.sphere.sdk.json.SphereJsonUtils#readObject(com.fasterxml.jackson.databind.JsonNode, TypeReference)
     * @see io.sphere.sdk.json.SphereJsonUtils#readObjectFromResource(String, TypeReference)
     *
     * @return type reference
     */
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

    /**
     * String that uniquely identifies an order. It can be used to create more human-readable (in contrast to ID) identifier for the order. It should be unique across a project. Once it’s set it cannot be changed.
     * @return order number or null
     */
    @Nullable
    String getOrderNumber();

    InventoryMode getInventoryMode();

    /**
     * State of this order.
     * @return state
     * @see io.sphere.sdk.orders.commands.updateactions.ChangeOrderState
     */
    OrderState getOrderState();

    /**
     * Shipment state of this order.
     *
     * @return shipment state or null
     * @see io.sphere.sdk.orders.commands.updateactions.ChangeShipmentState
     */
    @Nullable
    ShipmentState getShipmentState();

    /**
     * Payment state of this order.
     *
     *
     * @return payment state or null
     * @see io.sphere.sdk.orders.commands.updateactions.ChangePaymentState
     */
    @Nullable
    PaymentState getPaymentState();

    @Nullable
    OrderShippingInfo getShippingInfo();

    /**
     *Sync info of this order.
     * @return sync infos
     * @see io.sphere.sdk.orders.commands.updateactions.UpdateSyncInfo
     */
    Set<SyncInfo> getSyncInfo();

    List<ReturnInfo> getReturnInfo();

    Long getLastMessageSequenceNumber();

    /**
     * The billing address.
     * @return address or null
     * @see io.sphere.sdk.orders.commands.updateactions.SetBillingAddress
     * @see OrderBillingAddressSetMessage
     */
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

    /**
     * The shipping address.
     *
     * @see io.sphere.sdk.orders.commands.updateactions.SetShippingAddress
     * @see OrderShippingAddressSetMessage
     * @return address or null
     */
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

    /**
     * Returns this state of this Order.
     *
     * @return state of this order or null
     *
     * @see io.sphere.sdk.orders.commands.updateactions.TransitionState
     */
    @Nullable
    Reference<State> getState();

    /**
     * Get associated payments.
     *
     * @return payments
     * @see io.sphere.sdk.orders.commands.updateactions.AddPayment
     * @see io.sphere.sdk.orders.commands.updateactions.RemovePayment
     */
    @Nullable
    @Override
    PaymentInfo getPaymentInfo();

    /**
     * Creates a reference for one item of this class by a known ID.
     *
     * <p>An example for categories but this applies for other resources, too:</p>
     * {@include.example io.sphere.sdk.categories.CategoryTest#referenceOfId()}
     *
     * <p>If you already have a resource object, then use {@link #toReference()} instead:</p>
     *
     * {@include.example io.sphere.sdk.categories.CategoryTest#toReference()}
     *
     * @param id the ID of the resource which should be referenced.
     * @return reference
     */
    static Reference<Order> referenceOfId(final String id) {
        return Reference.of(referenceTypeId(), id);
    }
}
