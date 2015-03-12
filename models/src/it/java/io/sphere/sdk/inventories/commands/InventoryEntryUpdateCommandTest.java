package io.sphere.sdk.inventories.commands;

import io.sphere.sdk.commands.UpdateAction;
import io.sphere.sdk.inventories.InventoryEntry;
import io.sphere.sdk.inventories.commands.updateactions.AddQuantity;
import io.sphere.sdk.inventories.commands.updateactions.RemoveQuantity;
import io.sphere.sdk.inventories.commands.updateactions.SetRestockableInDays;
import io.sphere.sdk.test.IntegrationTest;
import org.junit.Test;

import static io.sphere.sdk.inventories.InventoryEntryFixtures.withUpdateableInventoryEntry;
import static org.fest.assertions.Assertions.assertThat;
import static io.sphere.sdk.test.OptionalAssert.assertThat;

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

    @Test
    public void removeQuantity() throws Exception {
        withUpdateableInventoryEntry(client(), entry -> {
            final long removingQuantity = 4;
            final UpdateAction<InventoryEntry> action = RemoveQuantity.of(removingQuantity);
            final InventoryEntry updatedEntry = execute(InventoryEntryUpdateCommand.of(entry, action));
            assertThat(updatedEntry.getQuantityOnStock()).isEqualTo(entry.getQuantityOnStock() - removingQuantity);
            return updatedEntry;
        });
    }

    @Test
    public void setRestockableInDays() throws Exception {
        withUpdateableInventoryEntry(client(), entry -> {
            final int restockableInDays = entry.getRestockableInDays().map(i -> i + 4).orElse(4);
            final UpdateAction<InventoryEntry> action = SetRestockableInDays.of(restockableInDays);
            final InventoryEntry updatedEntry = execute(InventoryEntryUpdateCommand.of(entry, action));
            assertThat(updatedEntry.getRestockableInDays()).isPresentAs(restockableInDays);
            return updatedEntry;
        });
    }
}