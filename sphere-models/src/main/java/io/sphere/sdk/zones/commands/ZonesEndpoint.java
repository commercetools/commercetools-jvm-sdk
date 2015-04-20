package io.sphere.sdk.zones.commands;

import io.sphere.sdk.client.JsonEndpoint;
import io.sphere.sdk.zones.Zone;

final class ZonesEndpoint {
    static final JsonEndpoint<Zone> ENDPOINT = JsonEndpoint.of(Zone.typeReference(), "/zones");
}
