package io.sphere.sdk.orderedit;

import io.sphere.sdk.json.TypeReferences;
import io.sphere.sdk.orderedits.OrderEdit;
import io.sphere.sdk.orderedits.OrderEditDraft;
import io.sphere.sdk.orderedits.OrderEditDraftBuilder;
import io.sphere.sdk.orderedits.commands.OrderEditCreateCommand;
import io.sphere.sdk.orderedits.commands.OrderEditDeleteCommand;
import io.sphere.sdk.orderedits.commands.OrderEditUpdateCommand;
import io.sphere.sdk.orderedits.commands.updateactions.SetCustomField;
import io.sphere.sdk.orderedits.commands.updateactions.SetCustomType;
import io.sphere.sdk.orders.OrderFixtures;
import io.sphere.sdk.test.IntegrationTest;
import io.sphere.sdk.types.CustomFieldsDraft;
import io.sphere.sdk.types.CustomFieldsDraftBuilder;
import io.sphere.sdk.types.Type;
import org.assertj.core.api.Assertions;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;
import java.util.function.Function;

import static io.sphere.sdk.types.TypeFixtures.STRING_FIELD_NAME;
import static io.sphere.sdk.types.TypeFixtures.withUpdateableType;
import static org.assertj.core.api.Assertions.assertThat;

public class OrderEditCustomFieldsIntegrationTest extends IntegrationTest {

    private static final Map<String, Object> CUSTOM_FIELDS_MAP = Collections.singletonMap(STRING_FIELD_NAME, "hello");

    @Test
    public void setCustomTypeById() {
        setCustomType(type -> SetCustomType.ofTypeIdAndObjects(type.getId(), CUSTOM_FIELDS_MAP));
    }

    @Test
    public void setCustomTypeByKey() {
        setCustomType(type -> SetCustomType.ofTypeKeyAndObjects(type.getKey(), CUSTOM_FIELDS_MAP));
    }

    @Test
    public void createOrderEditWithCustomTypeById() {
        createOrderEditWithCustomType(type -> CustomFieldsDraftBuilder.ofTypeId(type.getId()));
    }

    @Test
    public void createOrderEditWithCustomTypeByKey() {
        createOrderEditWithCustomType(type -> CustomFieldsDraftBuilder.ofTypeKey(type.getKey()));
    }

    private void setCustomType(final Function<Type, SetCustomType> updateActionCreator) {
        withUpdateableType(client(), type -> {
            OrderFixtures.withOrder(client(), order -> {
                OrderEditFixtures.withUpdateableOrderEdit(client(), order.toReference(), orderEdit -> {
                    final SetCustomType updateAction = updateActionCreator.apply(type);
                    final OrderEdit updatedOrderEdit = client().executeBlocking(OrderEditUpdateCommand.of(orderEdit, updateAction));
                    Assertions.assertThat(updatedOrderEdit.getCustom().getType()).isEqualTo(type.toReference());
                    Assertions.assertThat(updatedOrderEdit.getCustom().getField(STRING_FIELD_NAME, TypeReferences.stringTypeReference())).isEqualTo("hello");
                    final OrderEdit updatedOrderEdit2 = client().executeBlocking(OrderEditUpdateCommand.of(updatedOrderEdit, SetCustomType.ofRemoveType()));
                    Assertions.assertThat(updatedOrderEdit2.getCustom()).isNull();
                    return updatedOrderEdit2;
                });
            });
            return type;
        });
    }

    private void createOrderEditWithCustomType(final Function<Type, CustomFieldsDraftBuilder> draftCreator) {
        withUpdateableType(client(), type -> {
            OrderFixtures.withOrder(client(), order -> {
                final CustomFieldsDraftBuilder customFieldsDraftBuilder = draftCreator.apply(type);
                final CustomFieldsDraft customFieldsDraft = customFieldsDraftBuilder.addObject(STRING_FIELD_NAME, "a value").build();
                final OrderEditDraft orderEditDraft = OrderEditDraftBuilder.of(order.toReference(), new ArrayList<>()).custom(customFieldsDraft).build();
                final OrderEdit orderEdit = client().executeBlocking(OrderEditCreateCommand.of(orderEditDraft));
                assertThat(orderEdit.getCustom().getField(STRING_FIELD_NAME, TypeReferences.stringTypeReference())).isEqualTo("a value");
                final OrderEdit updatedOrderEdit = client().executeBlocking(OrderEditUpdateCommand.of(orderEdit, SetCustomField.ofObject(STRING_FIELD_NAME, "a new value")));
                assertThat(updatedOrderEdit.getCustom().getField(STRING_FIELD_NAME, TypeReferences.stringTypeReference())).isEqualTo("a new value");
                client().executeBlocking(OrderEditDeleteCommand.of(updatedOrderEdit));
            });
            return type;
        });
    }
}
