package io.sphere.sdk.inventory.commands;

import io.sphere.sdk.client.JsonEndpoint;
import io.sphere.sdk.inventory.InventoryEntry;

final class InventoryEntryEndpoint {
    static final JsonEndpoint<InventoryEntry> ENDPOINT = JsonEndpoint.of(InventoryEntry.typeReference(), "/inventory");
}
