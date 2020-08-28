package io.sphere.sdk.orders.messages;


import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        property = "type",
        defaultImpl = SimpleOrderMessage.class,
        visible = true
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = ParcelRemovedFromDeliveryMessage.class, name = "ParcelRemovedFromDelivery"),
        @JsonSubTypes.Type(value = OrderShipmentStateChangedMessage.class, name = "OrderShipmentStateChanged"),
        @JsonSubTypes.Type(value = OrderStateTransitionMessage.class, name = "OrderStateTransition"),
        @JsonSubTypes.Type(value = LineItemStateTransitionMessage.class, name = "LineItemStateTransition"),
        @JsonSubTypes.Type(value = DeliveryAddedMessage.class, name = "DeliveryAdded"),
        @JsonSubTypes.Type(value = OrderShippingAddressSetMessage.class, name = "OrderShippingAddressSet"),
        @JsonSubTypes.Type(value = OrderImportedMessage.class, name = "OrderImported"),
        @JsonSubTypes.Type(value = ParcelTrackingDataUpdatedMessage.class, name = "ParcelTrackingDataUpdated"),
        @JsonSubTypes.Type(value = OrderCreatedMessage.class, name = "OrderCreated"),
        @JsonSubTypes.Type(value = DeliveryItemsUpdatedMessage.class, name = "DeliveryItemsUpdated"),
        @JsonSubTypes.Type(value = CustomLineItemStateTransitionMessage.class, name = "CustomLineItemStateTransition"),
        @JsonSubTypes.Type(value = ParcelItemsUpdatedMessage.class, name = "ParcelItemsUpdated"),
        @JsonSubTypes.Type(value = OrderCustomerEmailSetMessage.class, name = "OrderCustomerEmailSet"),
        @JsonSubTypes.Type(value = OrderCustomerGroupSetMessage.class, name = "OrderCustomerGroupSet"),
        @JsonSubTypes.Type(value = OrderStateChangedMessage.class, name = "OrderStateChanged"),
        @JsonSubTypes.Type(value = OrderCustomerSetMessage.class, name = "OrderCustomerSet"),
        @JsonSubTypes.Type(value = OrderPaymentStateChangedMessage.class, name = "OrderPaymentStateChanged"),
        @JsonSubTypes.Type(value = ParcelAddedToDeliveryMessage.class, name = "ParcelAddedToDelivery"),
        @JsonSubTypes.Type(value = OrderDeletedMessage.class, name = "OrderDeleted"),
        @JsonSubTypes.Type(value = LineItemLikeStateTransitionMessage.class, name = "LineItemLikeStateTransition"),
        @JsonSubTypes.Type(value = ReturnInfoAddedMessage.class, name = "ReturnInfoAdded"),
        @JsonSubTypes.Type(value = OrderBillingAddressSetMessage.class, name = "OrderBillingAddressSet"),
        @JsonSubTypes.Type(value = DeliveryAddressSetMessage.class, name = "DeliveryAddressSet"),
        @JsonSubTypes.Type(value = ParcelMeasurementsUpdatedMessage.class, name = "ParcelMeasurementsUpdated"),
        @JsonSubTypes.Type(value = OrderReturnShipmentStateChangedMessage.class, name = "OrderReturnShipmentStateChanged"),
        @JsonSubTypes.Type(value = SimpleOrderMessage.class, name = "SimpleOrder"),
        @JsonSubTypes.Type(value = DeliveryRemovedMessage.class, name = "DeliveryRemoved"),
        @JsonSubTypes.Type(value = OrderEditAppliedMessage.class, name = "OrderEditApplied"),
        @JsonSubTypes.Type(value = OrderShippingInfoSetMessage.class, name = "OrderShippingInfoSet"),
        @JsonSubTypes.Type(value = OrderDiscountCodeAddedMessage.class, name = "OrderDiscountCodeAdded"),
        @JsonSubTypes.Type(value = OrderDiscountCodeStateSetMessage.class, name = "OrderDiscountCodeStateSet"),
        @JsonSubTypes.Type(value = OrderStoreSetMessage.class, name = "OrderStoreSet"),
})
public interface OrderMessage {

    String getType();

}
