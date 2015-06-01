package io.sphere.sdk.inventories.commands;

import io.sphere.sdk.commands.UpdateAction;
import io.sphere.sdk.inventories.InventoryEntry;
import io.sphere.sdk.inventories.commands.updateactions.AddQuantity;
import io.sphere.sdk.inventories.commands.updateactions.RemoveQuantity;
import io.sphere.sdk.inventories.commands.updateactions.SetExpectedDelivery;
import io.sphere.sdk.inventories.commands.updateactions.SetRestockableInDays;
import io.sphere.sdk.test.IntegrationTest;
import io.sphere.sdk.test.SphereTestUtils;
import org.junit.Test;

import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;

import static io.sphere.sdk.inventories.InventoryEntryFixtures.withUpdateableInventoryEntry;
import static org.assertj.core.api.Assertions.assertThat;

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
            assertThat(updatedEntry.getRestockableInDays()).contains(restockableInDays);
            return updatedEntry;
        });
    }

    @Test
    public void setExpectedDelivery() throws Exception {
        withUpdateableInventoryEntry(client(), entry -> {
            final ZonedDateTime expectedDelivery = SphereTestUtils.now().plus(7, ChronoUnit.DAYS);
            final UpdateAction<InventoryEntry> action = SetExpectedDelivery.of(expectedDelivery);
            final InventoryEntry updatedEntry = execute(InventoryEntryUpdateCommand.of(entry, action));
            assertThat(updatedEntry.getExpectedDelivery())
                    .contains(expectedDelivery)
                    .isNotEqualTo(entry.getExpectedDelivery());
            return updatedEntry;
        });
    }
}