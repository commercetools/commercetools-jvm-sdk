package io.sphere.sdk.orders;

import io.sphere.sdk.customers.CustomerFixtures;
import io.sphere.sdk.json.TypeReferences;
import io.sphere.sdk.orders.commands.OrderUpdateCommand;
import io.sphere.sdk.orders.commands.updateactions.*;
import io.sphere.sdk.test.IntegrationTest;
import io.sphere.sdk.test.SphereTestUtils;
import org.junit.Test;

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
    public void customTypesForReturnedItems() {
        withUpdateableType(client(), type -> {
            withOrder(client(), order -> {
                assertThat(order.getReturnInfo()).isEmpty();
                final String lineItemId = order.getLineItems().get(0).getId();
                final List<LineItemReturnItemDraft> items = asList(LineItemReturnItemDraft.of(1L, lineItemId, ReturnShipmentState.RETURNED, "foo bar"));
                final AddReturnInfo action = AddReturnInfo.of(items).withReturnDate(SphereTestUtils.now().minusSeconds(500)).withReturnTrackingId("trackingId");
                final Order updatedOrder = client().executeBlocking(OrderUpdateCommand.of(order, action));

                final String returnItemId = updatedOrder.getReturnInfo().get(0).getItems().get(0).getId();
                final Order orderWithType = client().executeBlocking(OrderUpdateCommand.of(updatedOrder, SetReturnItemCustomType.ofTypeIdAndObjects(type.getId(), CUSTOM_FIELDS_MAP, returnItemId)));

                assertThat(orderWithType.getReturnInfo().get(0).getItems().get(0).getCustom().getType()).isEqualTo(type.toReference());
                assertThat(orderWithType.getReturnInfo().get(0).getItems().get(0).getCustom().getField(STRING_FIELD_NAME, TypeReferences.stringTypeReference())).isEqualTo("hello");

                final Order updatedOrderCustomField = client().executeBlocking(OrderUpdateCommand.of(orderWithType, SetReturnItemCustomField.ofObject(STRING_FIELD_NAME, "other", returnItemId)));
                assertThat(updatedOrderCustomField.getReturnInfo().get(0).getItems().get(0).getCustom().getField(STRING_FIELD_NAME, TypeReferences.stringTypeReference())).isEqualTo("other");

                //test clean up
                client().executeBlocking(OrderUpdateCommand.of(updatedOrderCustomField, SetLineItemCustomType.ofRemoveType(lineItemId)));
            });
            return type;
        });
    }
}
