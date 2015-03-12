package io.sphere.sdk.inventories.commands;

import io.sphere.sdk.commands.UpdateAction;
import io.sphere.sdk.inventories.InventoryEntry;
import io.sphere.sdk.inventories.commands.updateactions.AddQuantity;
import io.sphere.sdk.test.IntegrationTest;
import org.junit.Test;

import static io.sphere.sdk.inventories.InventoryEntryFixtures.withUpdateableInventoryEntry;
import static org.fest.assertions.Assertions.assertThat;

public class InventoryEntryUpdateCommandTest extends IntegrationTest {
    @Test
    public void addQuantity() throws Exception {
        withUpdateableInventoryEntry(client(), entry -> {
            final long additionalQuantity = 4;
            final UpdateAction<InventoryEntry> action = AddQuantity.of(additionalQuantity);
            final InventoryEntry updatedEntry = execute(InventoryEntryUpdateCommand.of(entry, action));
            assertThat(updatedEntry.getQuantityOnStock()).isEqualTo(entry.getQuantityOnStock() + additionalQuantity);
            return updatedEntry;
        });
    }
}