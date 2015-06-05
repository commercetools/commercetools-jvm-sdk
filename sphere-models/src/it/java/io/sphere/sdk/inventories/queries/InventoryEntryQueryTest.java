package io.sphere.sdk.inventories.queries;

import io.sphere.sdk.channels.ChannelRoles;
import io.sphere.sdk.inventories.InventoryEntry;
import io.sphere.sdk.inventories.InventoryEntryDraft;
import io.sphere.sdk.queries.*;
import io.sphere.sdk.test.IntegrationTest;
import org.junit.Test;

import java.time.Instant;

import static io.sphere.sdk.channels.ChannelFixtures.withChannelOfRole;
import static io.sphere.sdk.inventories.InventoryEntryFixtures.withUpdateableInventoryEntry;
import static io.sphere.sdk.test.SphereTestUtils.randomKey;
import static io.sphere.sdk.test.SphereTestUtils.tomorrowInstant;
import static org.assertj.core.api.Assertions.assertThat;

public class InventoryEntryQueryTest extends IntegrationTest {
    @Test
    public void pure() throws Exception {
        withUpdateableInventoryEntry(client(), entry -> {
            final Query<InventoryEntry> query = InventoryEntryQuery.of().withSort(QuerySort.of("createdAt desc"));
            final PagedQueryResult<InventoryEntry> result = execute(query);
            assertThat(result.head().get().getId()).isEqualTo(entry.getId());
            return entry;
        });
    }

    @Test
    public void queryModel() throws Exception {
        final ChannelRoles channelRole = ChannelRoles.INVENTORY_SUPPLY;
        withChannelOfRole(client(), channelRole, channel -> {
            final String sku = randomKey();
            final long quantityOnStock = 10;
            final Instant expectedDelivery = tomorrowInstant();
            final int restockableInDays = 3;
            final InventoryEntryDraft draft = InventoryEntryDraft.of(sku, quantityOnStock)
                    .withExpectedDelivery(expectedDelivery)
                    .withRestockableInDays(restockableInDays)
                    .withSupplyChannel(channel);
            withUpdateableInventoryEntry(client(), draft, entry -> {
                final QueryPredicate<InventoryEntry> skuP = InventoryEntryQueryModel.of().sku().is(sku);
                final QueryPredicate<InventoryEntry> channelP = InventoryEntryQueryModel.of().supplyChannel().is(channel);
                final QueryPredicate<InventoryEntry> stockP = InventoryEntryQueryModel.of().quantityOnStock().is(quantityOnStock);
                final QueryPredicate<InventoryEntry> availableP = InventoryEntryQueryModel.of().availableQuantity().is(quantityOnStock);
                final QueryPredicate<InventoryEntry> predicate = skuP.and(channelP).and(availableP).and(stockP);
                final Query<InventoryEntry> query = InventoryEntryQuery.of()
                        .withPredicate(predicate)
                        .withSort(m -> m.id().sort(QuerySortDirection.DESC))
                        .withExpansionPath(m -> m.supplyChannel());
                final PagedQueryResult<InventoryEntry> result = execute(query);
                assertThat(result.head().map(e -> e.getId())).contains(entry.getId());
                assertThat(result.head().get().getSupplyChannel().get().getObj().get().getRoles())
                        .overridingErrorMessage("can expand supplyChannel reference")
                        .contains(channelRole);
                return entry;
            });
        });
    }
}