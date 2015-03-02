package io.sphere.sdk.zones.queries;

import io.sphere.sdk.queries.ByIdFetchImpl;
import io.sphere.sdk.zones.Zone;

/**
 Gets a zone by ID.

 {@include.example io.sphere.sdk.zones.queries.ZoneByIdFetchTest#fetchById()}
 */
public class ZoneByIdFetch extends ByIdFetchImpl<Zone> {
    private ZoneByIdFetch(final String id) {
        super(id, ZoneEndpoint.ENDPOINT);
    }

    public static ZoneByIdFetch of(final String id) {
        return new ZoneByIdFetch(id);
    }
}
