package io.sphere.sdk.inventory.queries;

import io.sphere.sdk.client.SphereRequest;
import io.sphere.sdk.inventory.InventoryEntry;
import io.sphere.sdk.test.IntegrationTest;
import org.junit.Test;

import static io.sphere.sdk.channels.ChannelRole.INVENTORY_SUPPLY;
import static io.sphere.sdk.inventory.InventoryEntryFixtures.*;
import static org.assertj.core.api.Assertions.assertThat;

public class InventoryEntryByIdGetIntegrationTest extends IntegrationTest {
    @Test
    public void execution() throws Exception {
        withInventoryEntryAndSupplyChannel(client(), INVENTORY_SUPPLY, (entry, channel) -> {
            final SphereRequest<InventoryEntry> sphereRequest = InventoryEntryByIdGet.of(entry)
                    .withExpansionPaths(m -> m.supplyChannel());
            final InventoryEntry actual = client().executeBlocking(sphereRequest);
            assertThat(actual.getId()).contains(entry.getId());
            assertThat(actual.getSupplyChannel().getObj().getId()).isEqualTo(channel.getId());
        });
    }
}