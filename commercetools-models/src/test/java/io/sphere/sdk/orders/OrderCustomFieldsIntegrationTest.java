package io.sphere.sdk.orders;

import io.sphere.sdk.carts.LineItem;
import io.sphere.sdk.customers.CustomerFixtures;
import io.sphere.sdk.json.TypeReferences;
import io.sphere.sdk.orders.commands.OrderUpdateCommand;
import io.sphere.sdk.orders.commands.updateactions.*;
import io.sphere.sdk.test.IntegrationTest;
import io.sphere.sdk.test.SphereTestUtils;
import org.junit.Test;

import java.time.ZonedDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static io.sphere.sdk.orders.OrderFixtures.*;
import static io.sphere.sdk.types.TypeFixtures.STRING_FIELD_NAME;
import static io.sphere.sdk.types.TypeFixtures.withUpdateableType;
import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;

public class OrderCustomFieldsIntegrationTest extends IntegrationTest {

    public static final Map<String, Object> CUSTOM_FIELDS_MAP = Collections.singletonMap(STRING_FIELD_NAME, "hello");
    public static final ParcelMeasurements SMALL_PARCEL_MEASUREMENTS = ParcelMeasurements.of(2, 3, 1, 4);
    public static final ParcelMeasurements PARCEL_MEASUREMENTS = ParcelMeasurements.of(200, 300, 100, 4000);
    public static final ZonedDateTime ZonedDateTime_IN_PAST = SphereTestUtils.now().minusSeconds(500);
    public static final TrackingData TRACKING_DATA = TrackingData.of()
            .withTrackingId("tracking-id-12")
            .withCarrier("carrier xyz")
            .withProvider("provider foo")
            .withProviderTransaction("prov trans 56");

    @Test
    public void setCustomType() {
        withUpdateableType(client(), type -> {
            withOrder(client(), order -> {
                final Order orderWithType = client().executeBlocking(OrderUpdateCommand.of(order, SetCustomType.ofTypeIdAndObjects(type.getId(), CUSTOM_FIELDS_MAP)));

                assertThat(orderWithType.getCustom().getType()).isEqualTo(type.toReference());
                assertThat(orderWithType.getCustom().getField(STRING_FIELD_NAME, TypeReferences.stringTypeReference())).isEqualTo("hello");

                final Order updatedOrder = client().executeBlocking(OrderUpdateCommand.of(orderWithType, SetCustomField.ofObject(STRING_FIELD_NAME, "other")));
                assertThat(updatedOrder.getCustom().getField(STRING_FIELD_NAME, TypeReferences.stringTypeReference())).isEqualTo("other");

                //test clean up
                return client().executeBlocking(OrderUpdateCommand.of(updatedOrder, SetCustomType.ofRemoveType()));
            });
            return type;
        });
    }

    @Test
    public void customTypesForCustomLineItems() {
        withUpdateableType(client(), type -> {
            withOrderOfCustomLineItems(client(), order -> {
                final String customLineItemId = order.getCustomLineItems().get(0).getId();
                final Order orderWithType = client().executeBlocking(OrderUpdateCommand.of(order, SetCustomLineItemCustomType.ofTypeIdAndObjects(type.getId(), CUSTOM_FIELDS_MAP, customLineItemId)));

                assertThat(orderWithType.getCustomLineItems().get(0).getCustom().getType()).isEqualTo(type.toReference());
                assertThat(orderWithType.getCustomLineItems().get(0).getCustom().getField(STRING_FIELD_NAME, TypeReferences.stringTypeReference())).isEqualTo("hello");

                final Order updatedOrder = client().executeBlocking(OrderUpdateCommand.of(orderWithType, SetCustomLineItemCustomField.ofObject(STRING_FIELD_NAME, "other", customLineItemId)));
                assertThat(updatedOrder.getCustomLineItems().get(0).getCustom().getField(STRING_FIELD_NAME, TypeReferences.stringTypeReference())).isEqualTo("other");

                //test clean up
                client().executeBlocking(OrderUpdateCommand.of(updatedOrder, SetCustomLineItemCustomType.ofRemoveType(customLineItemId)));
            });
            return type;
        });
    }

    @Test
    public void customTypesForLineItems() {
        withUpdateableType(client(), type -> {
            CustomerFixtures.withCustomerInGroup(client(), (customer, customerGroup) -> {
                withOrder(client(), customer, order -> {
                    final String lineItemId = order.getLineItems().get(0).getId();
                    final Order orderWithType = client().executeBlocking(OrderUpdateCommand.of(order, SetLineItemCustomType.ofTypeIdAndObjects(type.getId(), CUSTOM_FIELDS_MAP, lineItemId)));

                    assertThat(orderWithType.getLineItems().get(0).getCustom().getType()).isEqualTo(type.toReference());
                    assertThat(orderWithType.getLineItems().get(0).getCustom().getField(STRING_FIELD_NAME, TypeReferences.stringTypeReference())).isEqualTo("hello");

                    final Order updatedOrder = client().executeBlocking(OrderUpdateCommand.of(orderWithType, SetLineItemCustomField.ofObject(STRING_FIELD_NAME, "other", lineItemId)));
                    assertThat(updatedOrder.getLineItems().get(0).getCustom().getField(STRING_FIELD_NAME, TypeReferences.stringTypeReference())).isEqualTo("other");

                    //test clean up
                    client().executeBlocking(OrderUpdateCommand.of(updatedOrder, SetLineItemCustomType.ofRemoveType(lineItemId)));
                });
            });
            return type;
        });
    }

    @Test
    public void customTypesForReturnedItem() {
        withUpdateableType(client(), type -> {
            withOrder(client(), order -> {
                assertThat(order.getReturnInfo()).isEmpty();
                final String lineItemId = order.getLineItems().get(0).getId();
                final List<LineItemReturnItemDraft> items = asList(LineItemReturnItemDraft.of(1L, lineItemId, ReturnShipmentState.RETURNED, "foo bar"));
                final AddReturnInfo action = AddReturnInfo.of(items).withReturnDate(ZonedDateTime_IN_PAST).withReturnTrackingId("trackingId");
                final Order updatedOrder = client().executeBlocking(OrderUpdateCommand.of(order, action));

                final String returnItemId = updatedOrder.getReturnInfo().get(0).getItems().get(0).getId();
                final Order orderWithType = client().executeBlocking(OrderUpdateCommand.of(updatedOrder, SetReturnItemCustomType.ofTypeIdAndObjects(type.getId(), CUSTOM_FIELDS_MAP, returnItemId)));

                assertThat(orderWithType.getReturnInfo().get(0).getItems().get(0).getCustom().getType()).isEqualTo(type.toReference());
                assertThat(orderWithType.getReturnInfo().get(0).getItems().get(0).getCustom().getField(STRING_FIELD_NAME, TypeReferences.stringTypeReference())).isEqualTo("hello");

                final Order updatedOrderCustomField = client().executeBlocking(OrderUpdateCommand.of(orderWithType, SetReturnItemCustomField.ofObject(STRING_FIELD_NAME, "other", returnItemId)));
                assertThat(updatedOrderCustomField.getReturnInfo().get(0).getItems().get(0).getCustom().getField(STRING_FIELD_NAME, TypeReferences.stringTypeReference())).isEqualTo("other");

                //test clean up
                client().executeBlocking(OrderUpdateCommand.of(updatedOrderCustomField, SetReturnItemCustomType.ofRemoveType(returnItemId)));
            });
            return type;
        });
    }

    @Test
    public void customTypesForParcel() {
        withUpdateableType(client(), type -> {
            withOrder(client(), order -> {
                final LineItem lineItem = order.getLineItems().get(0);
                final List<DeliveryItem> items = asList(DeliveryItem.of(lineItem));
                final Order orderWithDelivery = client().executeBlocking(OrderUpdateCommand.of(order, AddDelivery.of(items)));
                final Delivery delivery = orderWithDelivery.getShippingInfo().getDeliveries().get(0);
                assertThat(delivery.getParcels()).isEmpty();

                final ParcelDraft parcelDraft = ParcelDraft.of(SMALL_PARCEL_MEASUREMENTS, TRACKING_DATA);
                final AddParcelToDelivery action = AddParcelToDelivery.of(delivery, parcelDraft);
                final Order updatedOrder = client().executeBlocking(OrderUpdateCommand.of(orderWithDelivery, action));
                final Parcel actualParcel = updatedOrder.getShippingInfo().getDeliveries().get(0).getParcels().get(0);
                assertThat(actualParcel.getTrackingData()).isEqualTo(TRACKING_DATA);

                final Order orderWithType = client().executeBlocking(OrderUpdateCommand.of(updatedOrder, SetParcelCustomType.ofTypeIdAndObjects(type.getId(), CUSTOM_FIELDS_MAP, actualParcel.getId())));

                assertThat(orderWithType.getShippingInfo().getDeliveries().get(0).getParcels().get(0).getCustom().getType()).isEqualTo(type.toReference());
                assertThat(orderWithType.getShippingInfo().getDeliveries().get(0).getParcels().get(0).getCustom().getField(STRING_FIELD_NAME, TypeReferences.stringTypeReference())).isEqualTo("hello");

                final Order updatedOrderCustomField = client().executeBlocking(OrderUpdateCommand.of(orderWithType, SetParcelCustomField.ofObject(STRING_FIELD_NAME, "other", actualParcel.getId())));
                assertThat(updatedOrderCustomField.getShippingInfo().getDeliveries().get(0).getParcels().get(0).getCustom().getField(STRING_FIELD_NAME, TypeReferences.stringTypeReference())).isEqualTo("other");

                //test clean up
                client().executeBlocking(OrderUpdateCommand.of(updatedOrderCustomField, SetParcelCustomType.ofRemoveType(actualParcel.getId())));
            });
            return type;
        });
    }
}
