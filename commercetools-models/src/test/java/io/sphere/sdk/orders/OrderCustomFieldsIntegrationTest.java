package io.sphere.sdk.orders;

import io.sphere.sdk.json.TypeReferences;
import io.sphere.sdk.orders.commands.OrderUpdateCommand;
import io.sphere.sdk.orders.commands.updateactions.SetCustomField;
import io.sphere.sdk.orders.commands.updateactions.SetCustomLineItemCustomField;
import io.sphere.sdk.orders.commands.updateactions.SetCustomLineItemCustomType;
import io.sphere.sdk.orders.commands.updateactions.SetCustomType;
import io.sphere.sdk.test.IntegrationTest;
import org.junit.Test;

import java.util.Collections;
import java.util.Map;

import static io.sphere.sdk.orders.OrderFixtures.*;
import static io.sphere.sdk.types.TypeFixtures.STRING_FIELD_NAME;
import static io.sphere.sdk.types.TypeFixtures.withUpdateableType;
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
    public void customTypesForLineItems() {
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
}
