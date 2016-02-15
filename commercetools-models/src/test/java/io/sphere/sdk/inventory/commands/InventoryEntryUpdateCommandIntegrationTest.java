package io.sphere.sdk.inventory.commands;

import io.sphere.sdk.channels.ChannelFixtures;
import io.sphere.sdk.channels.ChannelRole;
import io.sphere.sdk.commands.UpdateAction;
import io.sphere.sdk.inventory.InventoryEntry;
import io.sphere.sdk.inventory.commands.updateactions.*;
import io.sphere.sdk.test.IntegrationTest;
import io.sphere.sdk.test.SphereTestUtils;
import org.junit.Test;

import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

import static io.sphere.sdk.channels.ChannelFixtures.withChannelOfRole;
import static io.sphere.sdk.inventory.InventoryEntryFixtures.withUpdateableInventoryEntry;
import static org.assertj.core.api.Assertions.assertThat;

public class InventoryEntryUpdateCommandIntegrationTest extends IntegrationTest {
    @Test
    public void addQuantity() throws Exception {
        withUpdateableInventoryEntry(client(), entry -> {
            final long additionalQuantity = 4;
            final UpdateAction<InventoryEntry> action = AddQuantity.of(additionalQuantity);
            final InventoryEntry updatedEntry = client().executeBlocking(InventoryEntryUpdateCommand.of(entry, action));
            assertThat(updatedEntry.getQuantityOnStock()).isEqualTo(entry.getQuantityOnStock() + additionalQuantity);
            return updatedEntry;
        });
    }

    @Test
    public void changeQuantity() throws Exception {
        withUpdateableInventoryEntry(client(), entry -> {
            final UpdateAction<InventoryEntry> action = ChangeQuantity.of(5000L);
            final InventoryEntry updatedEntry = client().executeBlocking(InventoryEntryUpdateCommand.of(entry, action));
            assertThat(updatedEntry.getQuantityOnStock()).isEqualTo(5000);
            return updatedEntry;
        });
    }

    @Test
    public void removeQuantity() throws Exception {
        withUpdateableInventoryEntry(client(), entry -> {
            final long removingQuantity = 4;
            final UpdateAction<InventoryEntry> action = RemoveQuantity.of(removingQuantity);
            final InventoryEntry updatedEntry = client().executeBlocking(InventoryEntryUpdateCommand.of(entry, action));
            assertThat(updatedEntry.getQuantityOnStock()).isEqualTo(entry.getQuantityOnStock() - removingQuantity);
            return updatedEntry;
        });
    }

    @Test
    public void setSupplyChannel() throws Exception {
        withChannelOfRole(client(), ChannelRole.INVENTORY_SUPPLY, channel -> {
            withUpdateableInventoryEntry(client(), entry -> {
                final UpdateAction<InventoryEntry> action = SetSupplyChannel.of(channel);
                final InventoryEntry updatedEntry = client().executeBlocking(InventoryEntryUpdateCommand.of(entry, action));
                assertThat(updatedEntry.getSupplyChannel()).isEqualTo(channel.toReference());
                return updatedEntry;
            });
        });
    }

    @Test
    public void setRestockableInDays() throws Exception {
        withUpdateableInventoryEntry(client(), entry -> {
            final int restockableInDays = Optional.ofNullable(entry.getRestockableInDays()).map(i -> i + 4).orElse(4);
            final UpdateAction<InventoryEntry> action = SetRestockableInDays.of(restockableInDays);
            final InventoryEntry updatedEntry = client().executeBlocking(InventoryEntryUpdateCommand.of(entry, action));
            assertThat(updatedEntry.getRestockableInDays()).isEqualTo(restockableInDays);
            return updatedEntry;
        });
    }

    @Test
    public void setExpectedDelivery() throws Exception {
        withUpdateableInventoryEntry(client(), entry -> {
            final ZonedDateTime expectedDelivery = SphereTestUtils.now().plus(7, ChronoUnit.DAYS);
            final UpdateAction<InventoryEntry> action = SetExpectedDelivery.of(expectedDelivery);
            final InventoryEntry updatedEntry = client().executeBlocking(InventoryEntryUpdateCommand.of(entry, action));
            assertThat(updatedEntry.getExpectedDelivery())
                    .isEqualTo(expectedDelivery);
            return updatedEntry;
        });
    }
}