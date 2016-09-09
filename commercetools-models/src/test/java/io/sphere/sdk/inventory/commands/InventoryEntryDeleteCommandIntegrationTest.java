package io.sphere.sdk.inventory.commands;

import io.sphere.sdk.commands.DeleteCommand;
import io.sphere.sdk.inventory.InventoryEntry;
import io.sphere.sdk.inventory.InventoryEntryDraft;
import io.sphere.sdk.inventory.messages.InventoryEntryDeletedMessage;
import io.sphere.sdk.messages.queries.MessageQuery;
import io.sphere.sdk.queries.PagedQueryResult;
import io.sphere.sdk.queries.Query;
import io.sphere.sdk.test.IntegrationTest;
import org.junit.Test;

import java.util.Optional;

import static io.sphere.sdk.channels.ChannelFixtures.withChannelOfRole;
import static io.sphere.sdk.channels.ChannelRole.INVENTORY_SUPPLY;
import static io.sphere.sdk.test.SphereTestUtils.assertEventually;
import static io.sphere.sdk.test.SphereTestUtils.randomKey;
import static org.assertj.core.api.Assertions.assertThat;

public class InventoryEntryDeleteCommandIntegrationTest extends IntegrationTest {
    @Test
    public void execution() throws Exception {
        withChannelOfRole(client(), INVENTORY_SUPPLY, channel -> {
            final String sku = randomKey();
            final long quantityOnStock = 10;
            final InventoryEntryDraft inventoryEntryDraft = InventoryEntryDraft.of(sku, quantityOnStock)
                    .withSupplyChannel(channel);

            final InventoryEntry inventoryEntry = client().executeBlocking(InventoryEntryCreateCommand.of(inventoryEntryDraft));

            //delete
            final DeleteCommand<InventoryEntry> deleteCommand = InventoryEntryDeleteCommand.of(inventoryEntry);
            final InventoryEntry deletedEntry = client().executeBlocking(deleteCommand);

            final Query<InventoryEntryDeletedMessage> query =
                    MessageQuery.of()
                            .withSort(m -> m.createdAt().sort().desc())
                            .withExpansionPaths(m -> m.resource())
                            .withLimit(1L)
                            .withPredicates(m -> m.resource().is(inventoryEntry))
                            .forMessageType(InventoryEntryDeletedMessage.MESSAGE_HINT);
            assertEventually(() -> {
                final PagedQueryResult<InventoryEntryDeletedMessage> pagedQueryResult = client().executeBlocking(query);
                final Optional<InventoryEntryDeletedMessage> messageOptional = pagedQueryResult.head();

                assertThat(messageOptional).isPresent();
                final InventoryEntryDeletedMessage message = messageOptional.get();
                assertThat(message.getSupplyChannel()).isEqualTo(channel.toReference());
                assertThat(message.getSku()).isEqualTo(sku);
            });
        });
    }
}