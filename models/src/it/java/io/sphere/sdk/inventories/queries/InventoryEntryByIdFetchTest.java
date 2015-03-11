package io.sphere.sdk.inventories.queries;

import io.sphere.sdk.inventories.InventoryEntry;
import io.sphere.sdk.test.IntegrationTest;
import org.junit.Test;

import java.util.Optional;

import static io.sphere.sdk.inventories.InventoryFixtures.withUpdateableInventoryEntry;
import static io.sphere.sdk.test.OptionalAssert.assertThat;

public class InventoryEntryByIdFetchTest extends IntegrationTest {
    @Test
    public void execution() throws Exception {
        withUpdateableInventoryEntry(client(), entry -> {
            final InventoryEntryByIdFetch fetch = InventoryEntryByIdFetch.of(entry.getId());
            final Optional<InventoryEntry> loadedEntry = execute(fetch);
            assertThat(loadedEntry.map(e -> e.getId())).isPresentAs(entry.getId());
            return entry;
        });
    }
}