package io.sphere.sdk.zones.queries;

import com.fasterxml.jackson.core.type.TypeReference;
import com.neovisionaries.i18n.CountryCode;
import io.sphere.sdk.queries.PagedQueryResult;
import io.sphere.sdk.queries.QueryPredicate;
import io.sphere.sdk.queries.MetaModelQueryDsl;
import io.sphere.sdk.zones.Location;
import io.sphere.sdk.zones.Zone;
import io.sphere.sdk.zones.expansion.ZoneExpansionModel;

import java.util.Optional;

/**
 * {@doc.gen summary zones}
 */
public interface ZoneQuery extends MetaModelQueryDsl<Zone, ZoneQuery, ZoneQueryModel, ZoneExpansionModel<Zone>> {
    static TypeReference<PagedQueryResult<Zone>> resultTypeReference() {
        return new TypeReference<PagedQueryResult<Zone>>(){
            @Override
            public String toString() {
                return "TypeReference<PagedQueryResult<Zone>>";
            }
        };
    }

    static ZoneQuery of() {
        return new ZoneQueryImpl();
    }

    default ZoneQuery byName(final String name) {
        return withPredicates(m -> m.name().is(name));
    }

    /**
     * Predicate which matches the country of a location, does not take the state into the consideration.
     * For considering also the state use {@link #byLocation(io.sphere.sdk.zones.Location)}.
     * @param countryCode the country to query for
     * @return query with the same values but a predicate searching for a specific country
     */
    default ZoneQuery byCountry(final CountryCode countryCode) {
        return withPredicates(m -> m.locations().country().is(countryCode));
    }

    /**
     * Predicate which matches the country and state of a location.
     *
     * For ignoring the state use {@link #byCountry(com.neovisionaries.i18n.CountryCode)}.
     * @param location the location to query for
     * @return query with the same values but a predicate searching for a specific location
     */
    default ZoneQuery byLocation(final Location location) {
        final QueryPredicate<Zone> predicate =
                Optional.ofNullable(location.getState())
                        .map(state -> ZoneQueryModel.of().locations().where(l -> l.country().is(location.getCountry()).and(l.state().is(state))))
                        .orElseGet(() -> ZoneQueryModel.of().locations().where(l -> l.country().is(location.getCountry()).and(l.state().isNotPresent())));
        return withPredicates(predicate);
    }
}
