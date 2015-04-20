package io.sphere.sdk.inventories.queries;

import io.sphere.sdk.client.JsonEndpoint;
import io.sphere.sdk.inventories.InventoryEntry;

final class Endpoint {
    static final JsonEndpoint<InventoryEntry> ENDPOINT = JsonEndpoint.of(InventoryEntry.typeReference(), "/inventory");
}
