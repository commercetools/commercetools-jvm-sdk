package io.sphere.sdk.shippingmethods.queries;

import io.sphere.sdk.queries.ReferenceQueryModel;
import io.sphere.sdk.zones.Zone;

public interface ZoneRateCollectionQueryModel<T> {
    ReferenceQueryModel<T, Zone> zone();
}
