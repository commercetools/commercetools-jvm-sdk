package io.sphere.sdk.inventories.queries;

import io.sphere.sdk.channels.ChannelRoles;
import io.sphere.sdk.inventories.InventoryEntry;
import io.sphere.sdk.inventories.InventoryEntryDraft;
import io.sphere.sdk.queries.*;
import io.sphere.sdk.test.IntegrationTest;
import org.junit.Test;

import java.time.ZonedDateTime;

import static io.sphere.sdk.channels.ChannelFixtures.withChannelOfRole;
import static io.sphere.sdk.inventories.InventoryEntryFixtures.withUpdateableInventoryEntry;
import static io.sphere.sdk.inventories.queries.InventoryEntryQuery.model;
import static io.sphere.sdk.test.SphereTestUtils.randomKey;
import static io.sphere.sdk.test.SphereTestUtils.tomorrowZonedDateTime;
import static org.assertj.core.api.Assertions.assertThat;

public class InventoryEntryQueryTest extends IntegrationTest {
    @Test
    public void pure() throws Exception {
        withUpdateableInventoryEntry(client(), entry -> {
            final QueryDsl<InventoryEntry> query = InventoryEntryQuery.of().withSort(QuerySort.of("createdAt desc"));
            final PagedQueryResult<InventoryEntry> result = execute(query);
            assertThat(result.head().get().getId()).isEqualTo(entry.getId());
            return entry;
        });
    }

    @Test
    public void queryModel() throws Exception {
        withChannelOfRole(client(), ChannelRoles.INVENTORY_SUPPLY, channel -> {
            final String sku = randomKey();
            final long quantityOnStock = 10;
            final ZonedDateTime expectedDelivery = tomorrowZonedDateTime();
            final int restockableInDays = 3;
            final InventoryEntryDraft draft = InventoryEntryDraft.of(sku, quantityOnStock)
                    .withExpectedDelivery(expectedDelivery)
                    .withRestockableInDays(restockableInDays)
                    .withSupplyChannel(channel);
            withUpdateableInventoryEntry(client(), draft, entry -> {
                final QueryPredicate<InventoryEntry> skuP = model().sku().is(sku);
                final QueryPredicate<InventoryEntry> channelP = model().supplyChannel().is(channel);
                final QueryPredicate<InventoryEntry> stockP = model().quantityOnStock().is(quantityOnStock);
                final QueryPredicate<InventoryEntry> availableP = model().availableQuantity().is(quantityOnStock);
                final QueryPredicate<InventoryEntry> predicate = skuP.and(channelP).and(availableP).and(stockP);
                final QueryDsl<InventoryEntry> query = InventoryEntryQuery.of()
                        .withPredicate(predicate)
                        .withSort(model().id().sort(QuerySortDirection.DESC));
                final PagedQueryResult<InventoryEntry> result = execute(query);
                assertThat(result.head().map(e -> e.getId())).contains(entry.getId());
                return entry;
            });
        });
    }
}