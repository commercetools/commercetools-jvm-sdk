package io.sphere.sdk.zones;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.sphere.sdk.models.DefaultModel;
import io.sphere.sdk.models.Reference;

import java.util.Optional;
import java.util.Set;

/**
 * Zones define Shipping Rates for a set of Locations.
 */
@JsonDeserialize(as = ZoneImpl.class)
public interface Zone extends DefaultModel<Zone> {

    String getName();

    Optional<String> getDescription();

    Set<Location> getLocations();

    @Override
    default Reference<Zone> toReference() {
        return new Reference<>(typeId(), getId(), Optional.of(this));
    }

    public static String typeId(){
        return "zone";
    }

    public static TypeReference<Zone> typeReference(){
        return new TypeReference<Zone>() {
            @Override
            public String toString() {
                return "TypeReference<Zone>";
            }
        };
    }
}