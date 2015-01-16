package io.sphere.sdk.zones.queries;

import com.fasterxml.jackson.core.type.TypeReference;
import io.sphere.sdk.queries.DefaultModelQuery;
import io.sphere.sdk.queries.PagedQueryResult;
import io.sphere.sdk.queries.QueryDsl;
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
}
