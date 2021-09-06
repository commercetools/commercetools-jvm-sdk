package io.sphere.sdk.inventory.commands;

import io.sphere.sdk.commands.DeleteCommand;
import io.sphere.sdk.inventory.InventoryEntry;
import io.sphere.sdk.inventory.InventoryEntryDraft;
import io.sphere.sdk.inventory.InventoryEntryFixtures;
import io.sphere.sdk.inventory.messages.InventoryEntryCreatedMessage;
import io.sphere.sdk.messages.queries.MessageQuery;
import io.sphere.sdk.queries.PagedQueryResult;
import io.sphere.sdk.test.IntegrationTest;
import io.sphere.sdk.test.JsonNodeReferenceResolver;
import org.junit.Test;

import java.time.ZonedDateTime;
import java.util.Optional;

import static io.sphere.sdk.channels.ChannelFixtures.withChannelOfRole;
import static io.sphere.sdk.channels.ChannelRole.INVENTORY_SUPPLY;
import static io.sphere.sdk.test.SphereTestUtils.*;
import static org.assertj.core.api.Assertions.assertThat;

public class InventoryEntryCreateCommandIntegrationTest extends IntegrationTest {
    @Test
    public void execution() throws Exception {
        withChannelOfRole(client(), INVENTORY_SUPPLY, channel -> {
            final String sku = randomKey();
            final long quantityOnStock = 10;
            final ZonedDateTime expectedDelivery = tomorrowZonedDateTime();
            final int restockableInDays = 3;
            final InventoryEntryDraft inventoryEntryDraft = InventoryEntryDraft.of(sku, quantityOnStock)
                    .withExpectedDelivery(expectedDelivery)
                    .withRestockableInDays(restockableInDays)
                    .withSupplyChannel(channel);

            final InventoryEntry inventoryEntry = client().executeBlocking(InventoryEntryCreateCommand.of(inventoryEntryDraft));

            assertThat(inventoryEntry.getSku()).isEqualTo(sku);
            assertThat(inventoryEntry.getQuantityOnStock()).isEqualTo(quantityOnStock);
            assertThat(inventoryEntry.getAvailableQuantity()).isEqualTo(quantityOnStock);
            assertThat(inventoryEntry.getExpectedDelivery()).isEqualTo(expectedDelivery);
            assertThat(inventoryEntry.getRestockableInDays()).isEqualTo(restockableInDays);
            assertThat(inventoryEntry.getSupplyChannel()).isEqualTo(channel.toReference());

            assertEventually(() -> {
                final PagedQueryResult<InventoryEntryCreatedMessage> pagedQueryResult = client().executeBlocking(
                        MessageQuery.of().withPredicates(m -> m.resource().is(inventoryEntry))
                            .forMessageType(InventoryEntryCreatedMessage.MESSAGE_HINT)
                );

                final Optional<InventoryEntryCreatedMessage> inventoryCreatedMessage = pagedQueryResult.head();

                assertThat(inventoryCreatedMessage).isPresent();
                assertThat(inventoryCreatedMessage.get().getResource().getId()).isEqualTo(inventoryEntry.getId());
            });

            //delete
            final DeleteCommand<InventoryEntry> deleteCommand = InventoryEntryDeleteCommand.of(inventoryEntry);
            final InventoryEntry deletedEntry = client().executeBlocking(deleteCommand);
        });
    }

    @Test
    public void createByJson() {
        withChannelOfRole(client(), INVENTORY_SUPPLY, channel -> {
            final JsonNodeReferenceResolver referenceResolver = new JsonNodeReferenceResolver();
            referenceResolver.addResourceByKey("supply-channel", channel);
            final InventoryEntryDraft draft = draftFromJsonResource("drafts-tests/inventory.json", InventoryEntryDraft.class, referenceResolver);

            InventoryEntryFixtures.withInventoryEntry(client(), draft, inventoryEntry -> {
                assertThat(inventoryEntry.getSku()).isEqualTo("demo-sku");
                assertThat(inventoryEntry.getQuantityOnStock()).isEqualTo(523);
                assertThat(inventoryEntry.getSupplyChannel()).isEqualTo(channel.toReference());
            });
        });

    }
}