package io.sphere.sdk.zones.queries;

import io.sphere.sdk.models.Identifiable;
import io.sphere.sdk.expansion.ExpansionPath;
import io.sphere.sdk.queries.MetaModelGetDsl;
import io.sphere.sdk.zones.Zone;
import io.sphere.sdk.zones.expansion.ZoneExpansionModel;

import java.util.List;
import java.util.function.Function;

/**
 Gets a zone by ID.

 {@include.example io.sphere.sdk.zones.queries.ZoneByIdGetTest#fetchById()}
 */
public interface ZoneByIdGet extends MetaModelGetDsl<Zone, Zone, ZoneByIdGet, ZoneExpansionModel<Zone>> {
    static ZoneByIdGet of(final Identifiable<Zone> zone) {
        return of(zone.getId());
    }

    static ZoneByIdGet of(final String id) {
        return new ZoneByIdGetImpl(id);
    }

    @Override
    List<ExpansionPath<Zone>> expansionPaths();

    @Override
    ZoneByIdGet plusExpansionPaths(final ExpansionPath<Zone> expansionPath);

    @Override
    ZoneByIdGet withExpansionPaths(final ExpansionPath<Zone> expansionPath);

    @Override
    ZoneByIdGet withExpansionPaths(final List<ExpansionPath<Zone>> expansionPaths);
}

