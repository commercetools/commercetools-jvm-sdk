package io.sphere.sdk.inventory.queries;

import io.sphere.sdk.channels.ChannelRole;
import io.sphere.sdk.inventory.InventoryEntry;
import io.sphere.sdk.inventory.InventoryEntryDraft;
import io.sphere.sdk.queries.*;
import io.sphere.sdk.test.IntegrationTest;
import org.junit.Test;

import java.time.ZonedDateTime;

import static io.sphere.sdk.channels.ChannelFixtures.withChannelOfRole;
import static io.sphere.sdk.inventory.InventoryEntryFixtures.withUpdateableInventoryEntry;
import static io.sphere.sdk.test.SphereTestUtils.randomKey;
import static io.sphere.sdk.test.SphereTestUtils.tomorrowZonedDateTime;
import static org.assertj.core.api.Assertions.assertThat;

public class InventoryEntryQueryTest extends IntegrationTest {
    @Test
    public void pure() throws Exception {
        withUpdateableInventoryEntry(client(), entry -> {
            final Query<InventoryEntry> query = InventoryEntryQuery.of().withSort(QuerySort.of("createdAt desc"));
            final PagedQueryResult<InventoryEntry> result = client().executeBlocking(query);
            assertThat(result.head().get().getId()).isEqualTo(entry.getId());
            return entry;
        });
    }

    @Test
    public void queryModel() throws Exception {
        final ChannelRole channelRole = ChannelRole.INVENTORY_SUPPLY;
        withChannelOfRole(client(), channelRole, channel -> {
            final String sku = randomKey();
            final long quantityOnStock = 10;
            final ZonedDateTime expectedDelivery = tomorrowZonedDateTime();
            final int restockableInDays = 3;
            final InventoryEntryDraft draft = InventoryEntryDraft.of(sku, quantityOnStock)
                    .withExpectedDelivery(expectedDelivery)
                    .withRestockableInDays(restockableInDays)
                    .withSupplyChannel(channel);
            withUpdateableInventoryEntry(client(), draft, entry -> {
                final QueryPredicate<InventoryEntry> skuP = InventoryEntryQueryModelImpl.of().sku().is(sku);
                final QueryPredicate<InventoryEntry> channelP = InventoryEntryQueryModelImpl.of().supplyChannel().is(channel);
                final QueryPredicate<InventoryEntry> channelPById = InventoryEntryQueryModelImpl.of().supplyChannel().id().is(channel.getId());
                final QueryPredicate<InventoryEntry> stockP = InventoryEntryQueryModelImpl.of().quantityOnStock().is(quantityOnStock);
                final QueryPredicate<InventoryEntry> availableP = InventoryEntryQueryModelImpl.of().availableQuantity().is(quantityOnStock);
                final QueryPredicate<InventoryEntry> restockableInDaysP = InventoryEntryQueryModelImpl.of().restockableInDays().is(restockableInDays);
                final QueryPredicate<InventoryEntry> predicate = skuP.and(channelP).and(channelPById).and(availableP).and(stockP).and(restockableInDaysP);
                final Query<InventoryEntry> query = InventoryEntryQuery.of()
                        .withPredicates(predicate)
                        .withSort(m -> m.id().sort().desc())
                        .withExpansionPaths(m -> m.supplyChannel());
                final PagedQueryResult<InventoryEntry> result = client().executeBlocking(query);
                assertThat(result.head().map(e -> e.getId())).contains(entry.getId());
                assertThat(result.head().get().getSupplyChannel().getObj().getRoles())
                        .overridingErrorMessage("can expand supplyChannel reference")
                        .contains(channelRole);
                return entry;
            });
        });
    }
}