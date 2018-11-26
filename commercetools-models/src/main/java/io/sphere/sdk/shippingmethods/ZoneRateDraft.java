package io.sphere.sdk.shippingmethods;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.sphere.sdk.annotations.FactoryMethod;
import io.sphere.sdk.annotations.ResourceDraftValue;
import io.sphere.sdk.annotations.ResourceValue;
import io.sphere.sdk.models.Reference;
import io.sphere.sdk.models.Referenceable;
import io.sphere.sdk.models.ResourceIdentifier;
import io.sphere.sdk.zones.Zone;

import java.util.List;


@JsonDeserialize(as = ZoneRateDraftDsl.class)
@ResourceDraftValue(factoryMethods = @FactoryMethod(parameterNames = {"zone","shippingRates"}))
public interface ZoneRateDraft {

    ResourceIdentifier<Zone> getZone();

    List<ShippingRate> getShippingRates();

}
