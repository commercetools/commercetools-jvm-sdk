package io.sphere.sdk.shippingmethods;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.sphere.sdk.models.Resource;
import io.sphere.sdk.models.Reference;
import io.sphere.sdk.models.Referenceable;
import io.sphere.sdk.taxcategories.TaxCategory;
import io.sphere.sdk.zones.Zone;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;

import static java.util.stream.Collectors.toList;


@JsonDeserialize(as = ShippingMethodImpl.class)
public interface ShippingMethod extends Resource<ShippingMethod> {
    String getName();

    @Nullable
    String getDescription();

    Reference<TaxCategory> getTaxCategory();

    List<ZoneRate> getZoneRates();

    default List<ShippingRate> getShippingRatesForZone(final Referenceable<Zone> zone) {
        return getZoneRates().stream()
                .filter(rate -> rate.getZone().hasSameIdAs(zone.toReference()))
                .findFirst()
                .map(rate -> rate.getShippingRates())
                .orElse(Collections.emptyList());
    }

    default List<Reference<Zone>> getZones() {
        return getZoneRates().stream().map(rate -> rate.getZone()).collect(toList());
    }

    Boolean isDefault();

    @Override
    default Reference<ShippingMethod> toReference() {
        return Reference.of(referenceTypeId(), getId(), this);
    }

    static String referenceTypeId() {
        return "shipping-method";
    }

    /**
     *
     * @deprecated use {@link #referenceTypeId()} instead
     * @return referenceTypeId
     */
    @Deprecated
    static String typeId() {
        return "shipping-method";
    }

    static TypeReference<ShippingMethod> typeReference() {
        return new TypeReference<ShippingMethod>() {
            @Override
            public String toString() {
                return "TypeReference<ShippingMethod>";
            }
        };
    }

    static Reference<ShippingMethod> referenceOfId(final String id) {
        return Reference.of(referenceTypeId(), id);
    }
}
