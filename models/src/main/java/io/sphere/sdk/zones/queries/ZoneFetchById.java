package io.sphere.sdk.zones.queries;

import io.sphere.sdk.queries.FetchByIdImpl;
import io.sphere.sdk.zones.Zone;

/**
 Gets a zone by ID.

 {@include.example io.sphere.sdk.zones.queries.ZoneFetchByIdTest#fetchById()}
 */
public class ZoneFetchById extends FetchByIdImpl<Zone> {
    private ZoneFetchById(final String id) {
        super(id, ZoneEndpoint.ENDPOINT);
    }

    public static ZoneFetchById of(final String id) {
        return new ZoneFetchById(id);
    }
}
