package io.sphere.sdk.inventories.commands;

import io.sphere.sdk.channels.ChannelFixtures;
import io.sphere.sdk.channels.ChannelRoles;
import io.sphere.sdk.commands.DeleteCommand;
import io.sphere.sdk.inventories.InventoryEntry;
import io.sphere.sdk.inventories.InventoryEntryDraft;
import io.sphere.sdk.test.IntegrationTest;
import org.junit.Test;

import java.time.Instant;

import static io.sphere.sdk.channels.ChannelFixtures.withChannelOfRole;
import static io.sphere.sdk.test.SphereTestUtils.*;
import static org.fest.assertions.Assertions.assertThat;
import static io.sphere.sdk.test.OptionalAssert.assertThat;

public class InventoryEntryCreateCommandTest extends IntegrationTest {
    @Test
    public void execution() throws Exception {
        withChannelOfRole(client(), ChannelRoles.INVENTORY_SUPPLY, channel -> {
            final String sku = randomKey();
            final int quantityOnStock = 10;
            final Instant expectedDelivery = tomorrowInstant();
            final int restockableInDays = 3;
            final InventoryEntryDraft inventoryEntryDraft = InventoryEntryDraft.of(sku, quantityOnStock)
                    .withExpectedDelivery(expectedDelivery)
                    .withRestockableInDays(restockableInDays)
                    .withSupplyChannel(channel);

            final InventoryEntry inventoryEntry = execute(InventoryEntryCreateCommand.of(inventoryEntryDraft));

            assertThat(inventoryEntry.getSku()).isEqualTo(sku);
            assertThat(inventoryEntry.getQuantityOnStock()).isEqualTo(quantityOnStock);
            assertThat(inventoryEntry.getAvailableQuantity()).isEqualTo(quantityOnStock);
            assertThat(inventoryEntry.getExpectedDelivery()).isPresentAs(expectedDelivery);
            assertThat(inventoryEntry.getRestockableInDays()).isPresentAs(restockableInDays);
            assertThat(inventoryEntry.getSupplyChannel()).isPresentAs(channel.toReference());

            //delete
            final DeleteCommand<InventoryEntry> deleteCommand = InventoryDeleteCommand.of(inventoryEntry);
            final InventoryEntry deletedEntry = execute(deleteCommand);
        });
    }
}