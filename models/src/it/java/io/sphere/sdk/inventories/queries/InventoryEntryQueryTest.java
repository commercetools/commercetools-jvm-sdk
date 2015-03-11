package io.sphere.sdk.inventories.queries;

import io.sphere.sdk.inventories.InventoryEntry;
import io.sphere.sdk.queries.PagedQueryResult;
import io.sphere.sdk.queries.QueryDsl;
import io.sphere.sdk.queries.Sort;
import io.sphere.sdk.test.IntegrationTest;
import org.junit.Test;

import static io.sphere.sdk.inventories.InventoryEntryFixtures.withUpdateableInventoryEntry;
import static org.fest.assertions.Assertions.assertThat;

public class InventoryEntryQueryTest extends IntegrationTest {
    @Test
    public void pure() throws Exception {
        withUpdateableInventoryEntry(client(), entry -> {
            final QueryDsl<InventoryEntry> query = InventoryEntryQuery.of().withSort(Sort.of("createdAt desc"));
            final PagedQueryResult<InventoryEntry> result = execute(query);
            assertThat(result.head().get().getId()).isEqualTo(entry.getId());
            return entry;
        });
    }
}