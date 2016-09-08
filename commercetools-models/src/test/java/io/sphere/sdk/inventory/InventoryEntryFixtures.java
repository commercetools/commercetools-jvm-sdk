package io.sphere.sdk.inventory;

import io.sphere.sdk.channels.Channel;
import io.sphere.sdk.channels.ChannelRole;
import io.sphere.sdk.client.BlockingSphereClient;
import io.sphere.sdk.inventory.commands.InventoryEntryDeleteCommand;
import io.sphere.sdk.inventory.commands.InventoryEntryCreateCommand;
import io.sphere.sdk.models.Base;

import java.time.ZonedDateTime;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.UnaryOperator;

import static io.sphere.sdk.channels.ChannelFixtures.withChannelOfRole;
import static io.sphere.sdk.test.SphereTestUtils.*;

public class InventoryEntryFixtures extends Base {
    public static void withUpdateableInventoryEntry(final BlockingSphereClient client, final Function<InventoryEntry, InventoryEntry> f) {
        withUpdateableInventoryEntry(client, InventoryEntryDraft.of(randomKey(), 5L), f);
    }

    public static void withUpdateableInventoryEntry(final BlockingSphereClient client, final InventoryEntryDraft inventoryEntryDraft, final Function<InventoryEntry, InventoryEntry> f) {
        final InventoryEntry inventoryEntry = client.executeBlocking(InventoryEntryCreateCommand.of(inventoryEntryDraft));
        final InventoryEntry updatedEntry = f.apply(inventoryEntry);
        client.executeBlocking(InventoryEntryDeleteCommand.of(updatedEntry));
    }

    public static void withInventoryEntryAndSupplyChannel(final BlockingSphereClient client, final ChannelRole channelRole, final BiConsumer<InventoryEntry, Channel> consumer) {
        withChannelOfRole(client, channelRole, channel -> {
            final String sku = randomKey();
            final long quantityOnStock = 10;
            final ZonedDateTime expectedDelivery = tomorrowZonedDateTime();
            final int restockableInDays = 3;
            final InventoryEntryDraft draft = InventoryEntryDraft.of(sku, quantityOnStock)
                    .withExpectedDelivery(expectedDelivery)
                    .withRestockableInDays(restockableInDays)
                    .withSupplyChannel(channel);
            withUpdateableInventoryEntry(client, draft, entry -> {
                consumer.accept(entry, channel);
                return entry;
            });
        });
    }

    public static void withInventoryEntry(final BlockingSphereClient client, final InventoryEntryDraft draft, final Consumer<InventoryEntry> consumer) {
        final InventoryEntry inventoryEntry = client.executeBlocking(InventoryEntryCreateCommand.of(draft));
        consumer.accept(inventoryEntry);
        client.executeBlocking(InventoryEntryDeleteCommand.of(inventoryEntry));
    }

    public static void withInventoryEntry(final BlockingSphereClient client, final UnaryOperator<InventoryEntry> op) {
        final InventoryEntry inventoryEntry = client.executeBlocking(InventoryEntryCreateCommand.of(InventoryEntryDraft.of(randomKey(), 4)));
        client.executeBlocking(InventoryEntryDeleteCommand.of(op.apply(inventoryEntry)));
    }
}
