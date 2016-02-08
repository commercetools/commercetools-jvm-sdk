package io.sphere.sdk.zones;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.neovisionaries.i18n.CountryCode;
import io.sphere.sdk.models.Resource;
import io.sphere.sdk.models.Reference;
import io.sphere.sdk.shippingmethods.ShippingMethod;
import io.sphere.sdk.shippingmethods.ZoneRate;

import javax.annotation.Nullable;
import java.util.Set;

/**
 * Zones define Shipping Rates for a set of Locations.
 *
 * @see io.sphere.sdk.zones.commands.ZoneCreateCommand
 * @see io.sphere.sdk.zones.commands.ZoneUpdateCommand
 * @see io.sphere.sdk.zones.commands.ZoneDeleteCommand
 * @see io.sphere.sdk.zones.queries.ZoneQuery
 * @see io.sphere.sdk.zones.queries.ZoneByIdGet
 * @see ShippingMethod#getZones()
 * @see ZoneRate#getZone()
 */
@JsonDeserialize(as = ZoneImpl.class)
public interface Zone extends Resource<Zone> {

    String getName();

    @Nullable
    String getDescription();

    Set<Location> getLocations();

    @Override
    default Reference<Zone> toReference() {
        return Reference.of(referenceTypeId(), getId(), this);
    }

    /**
     * A type hint for references which resource type is linked in a reference.
     * @see Reference#getTypeId()
     * @return type hint
     */
    static String referenceTypeId() {
        return "zone";
    }

    /**
     *
     * @deprecated use {@link #referenceTypeId()} instead
     * @return referenceTypeId
     */
    @Deprecated
    static String typeId(){
        return "zone";
    }

    /**
     * Creates a container which contains the full Java type information to deserialize this class from JSON.
     *
     * @see io.sphere.sdk.json.SphereJsonUtils#readObject(byte[], TypeReference)
     * @see io.sphere.sdk.json.SphereJsonUtils#readObject(String, TypeReference)
     * @see io.sphere.sdk.json.SphereJsonUtils#readObject(com.fasterxml.jackson.databind.JsonNode, TypeReference)
     * @see io.sphere.sdk.json.SphereJsonUtils#readObjectFromResource(String, TypeReference)
     *
     * @return type reference
     */
    static TypeReference<Zone> typeReference() {
        return new TypeReference<Zone>() {
            @Override
            public String toString() {
                return "TypeReference<Zone>";
            }
        };
    }

    /**
     * Looks up the locations and checks if the country is present. It does not matter if the location has a state specified.
     *
     * @param countryCode the country to search for
     * @return true if the country is somehow in the locations.
     */
    default boolean contains(final CountryCode countryCode) {
        return getLocations().stream().anyMatch(location -> location.getCountry().equals(countryCode));
    }

    /**
     * Creates a reference for one item of this class by a known ID.
     *
     * <p>An example for categories but this applies for other resources, too:</p>
     * {@include.example io.sphere.sdk.categories.CategoryTest#referenceOfId()}
     *
     * <p>If you already have a resource object, then use {@link #toReference()} instead:</p>
     *
     * {@include.example io.sphere.sdk.categories.CategoryTest#toReference()}
     *
     * @param id the ID of the resource which should be referenced.
     * @return reference
     */
    static Reference<Zone> referenceOfId(final String id) {
        return Reference.of(referenceTypeId(), id);
    }
}