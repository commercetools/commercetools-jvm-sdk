package io.sphere.sdk.shippingmethods.queries;

import io.sphere.sdk.queries.QueryModel;
import io.sphere.sdk.queries.QueryModelImpl;
import io.sphere.sdk.queries.ReferenceQueryModel;
import io.sphere.sdk.zones.Zone;

import java.util.Optional;

public class ZoneRateListQueryModel<T> extends QueryModelImpl<T> {
    public ZoneRateListQueryModel(Optional<? extends QueryModel<T>> parent, String pathSegment) {
        super(parent, pathSegment);
    }

    public ReferenceQueryModel<T, Zone> zone() {
        return new ReferenceQueryModel<>(Optional.of(this), "zone");
    }
}
