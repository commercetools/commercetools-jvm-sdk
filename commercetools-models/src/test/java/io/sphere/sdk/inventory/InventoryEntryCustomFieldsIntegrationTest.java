package io.sphere.sdk.inventory;

import io.sphere.sdk.inventory.commands.InventoryEntryCreateCommand;
import io.sphere.sdk.inventory.commands.InventoryEntryDeleteCommand;
import io.sphere.sdk.inventory.commands.InventoryEntryUpdateCommand;
import io.sphere.sdk.inventory.commands.updateactions.SetCustomField;
import io.sphere.sdk.inventory.commands.updateactions.SetCustomType;
import io.sphere.sdk.test.IntegrationTest;
import io.sphere.sdk.types.CustomFieldsDraft;
import io.sphere.sdk.types.CustomFieldsDraftBuilder;
import org.junit.Test;

import java.util.HashMap;

import static io.sphere.sdk.test.SphereTestUtils.randomKey;
import static io.sphere.sdk.types.TypeFixtures.STRING_FIELD_NAME;
import static io.sphere.sdk.types.TypeFixtures.withUpdateableType;
import static org.assertj.core.api.Assertions.assertThat;

public class InventoryEntryCustomFieldsIntegrationTest extends IntegrationTest {
    @Test
    public void createInventoryEntryWithCustomType() {
        withUpdateableType(client(), type -> {
            final CustomFieldsDraft customFieldsDraft = CustomFieldsDraftBuilder.ofType(type)
                    .addObject(STRING_FIELD_NAME, "a value")
                    .build();

            final InventoryEntryDraft draft = InventoryEntryDraft.of(randomKey(), 5).withCustom(customFieldsDraft);
            final InventoryEntry inventoryEntry = client().executeBlocking(InventoryEntryCreateCommand.of(draft));
            assertThat(inventoryEntry.getCustom().getFieldAsString(STRING_FIELD_NAME))
                    .isEqualTo("a value");

            final InventoryEntryUpdateCommand updateCommand =
                    InventoryEntryUpdateCommand.of(inventoryEntry, SetCustomField.ofObject(STRING_FIELD_NAME, "a new value"));
            final InventoryEntry updatedInventoryEntry = client().executeBlocking(updateCommand);
            assertThat(updatedInventoryEntry.getCustom()
                    .getFieldAsString(STRING_FIELD_NAME)).isEqualTo("a new value");

            //clean up
            client().executeBlocking(InventoryEntryDeleteCommand.of(updatedInventoryEntry));
            return type;
        });
    }

    @Test
    public void setCustomType() {
        withUpdateableType(client(), type -> {
            InventoryEntryFixtures.withInventoryEntry(client(), inventoryEntry -> {
                final HashMap<String, Object> fields = new HashMap<>();
                fields.put(STRING_FIELD_NAME, "hello");
                final InventoryEntryUpdateCommand updateCommand =
                        InventoryEntryUpdateCommand.of(inventoryEntry, SetCustomType.ofTypeIdAndObjects(type.getId(), fields));
                final InventoryEntry updatedInventoryEntry = client().executeBlocking(updateCommand);
                assertThat(updatedInventoryEntry.getCustom().getType()).isEqualTo(type.toReference());
                assertThat(updatedInventoryEntry.getCustom().getFieldAsString(STRING_FIELD_NAME)).isEqualTo("hello");

                final InventoryEntry updated2 =
                        client().executeBlocking(InventoryEntryUpdateCommand.of(updatedInventoryEntry, SetCustomType.ofRemoveType()));
                assertThat(updated2.getCustom()).isNull();

                return updated2;
            });
            return type;
        });
    }
}
