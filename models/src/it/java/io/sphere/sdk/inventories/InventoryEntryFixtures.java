package io.sphere.sdk.inventories;

import io.sphere.sdk.client.TestClient;
import io.sphere.sdk.inventories.commands.InventoryDeleteCommand;
import io.sphere.sdk.inventories.commands.InventoryEntryCreateCommand;
import io.sphere.sdk.models.Base;

import java.util.function.Function;

import static io.sphere.sdk.test.SphereTestUtils.*;

public class InventoryEntryFixtures extends Base {
    public static void withUpdateableInventoryEntry(final TestClient client, final Function<InventoryEntry, InventoryEntry> f) {
        final InventoryEntryDraft inventoryEntryDraft = InventoryEntryDraft.of(randomKey(), 5);
        final InventoryEntry inventoryEntry = client.execute(InventoryEntryCreateCommand.of(inventoryEntryDraft));
        final InventoryEntry updatedEntry = f.apply(inventoryEntry);
        client.execute(InventoryDeleteCommand.of(updatedEntry));
    }
}
