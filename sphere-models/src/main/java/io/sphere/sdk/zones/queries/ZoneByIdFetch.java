package io.sphere.sdk.zones.queries;

import io.sphere.sdk.models.Identifiable;
import io.sphere.sdk.expansion.ExpansionPath;
import io.sphere.sdk.queries.MetaModelFetchDsl;
import io.sphere.sdk.zones.Zone;
import io.sphere.sdk.zones.expansion.ZoneExpansionModel;

import java.util.List;
import java.util.function.Function;

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

    @Override
    ZoneByIdFetch withExpansionPaths(final Function<ZoneExpansionModel<Zone>, ExpansionPath<Zone>> m);

    @Override
    ZoneByIdFetch plusExpansionPaths(final Function<ZoneExpansionModel<Zone>, ExpansionPath<Zone>> m);

    @Override
    List<ExpansionPath<Zone>> expansionPaths();

    @Override
    ZoneByIdFetch plusExpansionPaths(final ExpansionPath<Zone> expansionPath);

    @Override
    ZoneByIdFetch withExpansionPaths(final ExpansionPath<Zone> expansionPath);

    @Override
    ZoneByIdFetch withExpansionPaths(final List<ExpansionPath<Zone>> expansionPaths);
}

