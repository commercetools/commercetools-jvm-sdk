package io.sphere.sdk.shippingmethods;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.sphere.sdk.models.DefaultModel;
import io.sphere.sdk.models.Reference;
import io.sphere.sdk.models.Referenceable;
import io.sphere.sdk.taxcategories.TaxCategory;
import io.sphere.sdk.zones.Zone;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;


@JsonDeserialize(as = ShippingMethodImpl.class)
public interface ShippingMethod extends DefaultModel<ShippingMethod> {
    String getName();

    Optional<String> getDescription();

    Reference<TaxCategory> getTaxCategory();

    List<ZoneRate> getZoneRates();

    default List<ShippingRate> getShippingRatesForZone(final Referenceable<Zone> zone) {
        final Optional<ZoneRate> zoneRateOptional = getZoneRates().stream()
                .filter(rate -> rate.getZone().hasSameIdAs(zone.toReference()))
                .findFirst();
        return zoneRateOptional
                .map(rate -> rate.getShippingRates())
                .orElse(Collections.emptyList());
    }

    default List<Reference<Zone>> getZones() {
        return getZoneRates().stream().map(rate -> rate.getZone()).collect(toList());
    }

    boolean isDefault();

    @Override
    default Reference<ShippingMethod> toReference() {
        return new Reference<>(typeId(), getId(), Optional.of(this));
    }

    public static String typeId(){
        return "shipping-method";
    }

    public static TypeReference<ShippingMethod> typeReference(){
        return new TypeReference<ShippingMethod>() {
            @Override
            public String toString() {
                return "TypeReference<ShippingMethod>";
            }
        };
    }
}
