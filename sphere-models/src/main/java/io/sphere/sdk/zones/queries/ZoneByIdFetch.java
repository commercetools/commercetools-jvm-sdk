package io.sphere.sdk.zones.queries;

import io.sphere.sdk.models.Identifiable;
import io.sphere.sdk.queries.MetaModelFetchDsl;
import io.sphere.sdk.zones.Zone;
import io.sphere.sdk.zones.expansion.ZoneExpansionModel;

/**
 Gets a zone by ID.

 {@include.example io.sphere.sdk.zones.queries.ZoneByIdFetchTest#fetchById()}
 */
public interface ZoneByIdFetch extends MetaModelFetchDsl<Zone, ZoneByIdFetch, ZoneExpansionModel<Zone>> {
    static ZoneByIdFetch of(final Identifiable<Zone> cartDiscount) {
        return of(cartDiscount.getId());
    }

    static ZoneByIdFetch of(final String id) {
        return new ZoneByIdFetchImpl(id);
    }
}

