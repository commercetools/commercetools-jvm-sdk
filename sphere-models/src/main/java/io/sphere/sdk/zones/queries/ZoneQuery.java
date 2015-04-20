package io.sphere.sdk.zones.queries;

import com.fasterxml.jackson.core.type.TypeReference;
import com.neovisionaries.i18n.CountryCode;
import io.sphere.sdk.queries.DefaultModelQuery;
import io.sphere.sdk.queries.PagedQueryResult;
import io.sphere.sdk.queries.Predicate;
import io.sphere.sdk.queries.QueryDsl;
import io.sphere.sdk.zones.Location;
import io.sphere.sdk.zones.Zone;

public class ZoneQuery extends DefaultModelQuery<Zone> {
    private ZoneQuery() {
        super(ZoneEndpoint.ENDPOINT.endpoint(), resultTypeReference());
    }

    public static TypeReference<PagedQueryResult<Zone>> resultTypeReference() {
        return new TypeReference<PagedQueryResult<Zone>>(){
            @Override
            public String toString() {
                return "TypeReference<PagedQueryResult<Zone>>";
            }
        };
    }

    public static ZoneQueryModel model() {
        return ZoneQueryModel.get();
    }

    public static ZoneQuery of() {
        return new ZoneQuery();
    }

    public QueryDsl<Zone> byName(final String name) {
        return withPredicate(model().name().is(name));
    }

    /**
     * Predicate which matches the country of a location, does not take the state into the consideration.
     * For considering also the state use {@link #byLocation(io.sphere.sdk.zones.Location)}.
     * @param countryCode the country to query for
     * @return query with the same values but a predicate searching for a specific country
     */
    public QueryDsl<Zone> byCountry(final CountryCode countryCode) {
        return withPredicate(model().locations().country().is(countryCode));
    }

    /**
     * Predicate which matches the country and state of a location.
     *
     * For ignoring the state use {@link #byCountry(com.neovisionaries.i18n.CountryCode)}.
     * @param location the location to query for
     * @return query with the same values but a predicate searching for a specific location
     */
    public QueryDsl<Zone> byLocation(final Location location) {
        final Predicate<Zone> predicate =
                location.getState()
                        .map(state -> model().locations().where(l -> l.country().is(location.getCountry()).and(l.state().is(state))))
                        .orElseGet(() -> model().locations().where(l -> l.country().is(location.getCountry()).and(l.state().isNotPresent())));
        return withPredicate(predicate);
    }
}
