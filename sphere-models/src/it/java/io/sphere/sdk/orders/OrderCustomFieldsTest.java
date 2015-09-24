package io.sphere.sdk.orders;

import io.sphere.sdk.json.TypeReferences;
import io.sphere.sdk.orders.commands.OrderUpdateCommand;
import io.sphere.sdk.orders.commands.updateactions.SetCustomField;
import io.sphere.sdk.orders.commands.updateactions.SetCustomType;
import io.sphere.sdk.test.IntegrationTest;
import org.junit.Test;

import java.util.HashMap;

import static io.sphere.sdk.types.TypeFixtures.STRING_FIELD_NAME;
import static io.sphere.sdk.types.TypeFixtures.withUpdateableType;
import static org.assertj.core.api.StrictAssertions.assertThat;

public class OrderCustomFieldsTest extends IntegrationTest {
    @Test
    public void setCustomType() {
        withUpdateableType(client(), type -> {
            OrderFixtures.withOrder(client(), order -> {
                final HashMap<String, Object> fields = new HashMap<>();
                fields.put(STRING_FIELD_NAME, "hello");
                final Order orderWithType = execute(OrderUpdateCommand.of(order, SetCustomType.ofTypeIdAndObjects(type.getId(), fields)));

                assertThat(orderWithType.getCustom().getType()).isEqualTo(type.toReference());
                assertThat(orderWithType.getCustom().getField(STRING_FIELD_NAME, TypeReferences.stringTypeReference())).isEqualTo("hello");

                final Order updatedOrder = execute(OrderUpdateCommand.of(orderWithType, SetCustomField.ofObject(STRING_FIELD_NAME, "other")));
                assertThat(updatedOrder.getCustom().getField(STRING_FIELD_NAME, TypeReferences.stringTypeReference())).isEqualTo("other");

                //test clean up
                execute(OrderUpdateCommand.of(updatedOrder, SetCustomType.ofRemoveType()));
            });
            return type;
        });
    }
}
