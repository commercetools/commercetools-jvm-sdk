package io.sphere.sdk.shippingmethods;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.sphere.sdk.annotations.CopyFactoryMethod;
import io.sphere.sdk.annotations.FactoryMethod;
import io.sphere.sdk.annotations.ResourceDraftValue;
import io.sphere.sdk.models.Reference;
import io.sphere.sdk.zones.Zone;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Defines shipping rates (prices) for a specific zone.
 *
 * @see ShippingMethod#getZoneRates()
 * @see ShippingMethodDraft#getZoneRates()
 */
@ResourceDraftValue(factoryMethods = @FactoryMethod(parameterNames = {"zone", "shippingRates"}),
                    copyFactoryMethods = @CopyFactoryMethod(ZoneRate.class))
@JsonDeserialize(as = ZoneRateDraftDsl.class)
public interface ZoneRateDraft {

    Reference<Zone> getZone();

    List<ShippingRateDraft> getShippingRates();

    @JsonIgnore
    static ZoneRateDraft of(final Reference<Zone> zone, final List<ShippingRateDraft> shippingRateDrafts) {
        return  ZoneRateDraftDsl.of(zone, shippingRateDrafts);
    }

    @JsonIgnore
    static ZoneRateDraft of(final ZoneRate zoneRate){
        List<ShippingRateDraft> shippingRateDrafts =  zoneRate.getShippingRates().stream().map(ShippingRateDraft::of).collect(Collectors.toList());
        return  ZoneRateDraftDsl.of(zoneRate.getZone(),shippingRateDrafts);
    }
}
