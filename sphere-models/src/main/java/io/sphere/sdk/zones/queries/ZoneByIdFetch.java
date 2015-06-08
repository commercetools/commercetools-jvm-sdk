package io.sphere.sdk.zones.queries;

import io.sphere.sdk.models.Identifiable;
import io.sphere.sdk.queries.ExpansionPath;
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
    ZoneByIdFetch withExpansionPath(final Function<ZoneExpansionModel<Zone>, ExpansionPath<Zone>> m);

    @Override
    ZoneByIdFetch plusExpansionPath(final Function<ZoneExpansionModel<Zone>, ExpansionPath<Zone>> m);

    @Override
    List<ExpansionPath<Zone>> expansionPaths();

    @Override
    ZoneByIdFetch plusExpansionPath(final ExpansionPath<Zone> expansionPath);

    @Override
    ZoneByIdFetch withExpansionPath(final ExpansionPath<Zone> expansionPath);

    @Override
    ZoneByIdFetch withExpansionPath(final List<ExpansionPath<Zone>> expansionPaths);
}

