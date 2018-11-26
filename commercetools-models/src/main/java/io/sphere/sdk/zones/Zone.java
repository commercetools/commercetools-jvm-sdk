package io.sphere.sdk.zones;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.neovisionaries.i18n.CountryCode;
import io.sphere.sdk.annotations.*;
import io.sphere.sdk.models.Resource;
import io.sphere.sdk.models.Reference;
import io.sphere.sdk.models.WithKey;
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
@ResourceValue
@HasQueryEndpoint(additionalContentsQueryInterface = "\n" +
        "    default ZoneQuery byName(final String name) {\n" +
        "        return withPredicates(m -> m.name().is(name));\n" +
        "    }\n" +
        "\n" +
        "    /**\n" +
        "     * Predicate which matches the country of a location, does not take the state into the consideration.\n" +
        "     * For considering also the state use {@link #byLocation(io.sphere.sdk.zones.Location)}.\n" +
        "     * @param countryCode the country to query for\n" +
        "     * @return query with the same values but a predicate searching for a specific country\n" +
        "     */\n" +
        "    default ZoneQuery byCountry(final CountryCode countryCode) {\n" +
        "        return withPredicates(m -> m.locations().country().is(countryCode));\n" +
        "    }\n" +
        "\n" +
        "    /**\n" +
        "     * Predicate which matches the country and state of a location.\n" +
        "     *\n" +
        "     * For ignoring the state use {@link #byCountry(com.neovisionaries.i18n.CountryCode)}.\n" +
        "     * @param location the location to query for\n" +
        "     * @return query with the same values but a predicate searching for a specific location\n" +
        "     */\n" +
        "    default ZoneQuery byLocation(final Location location) {\n" +
        "        final QueryPredicate<Zone> predicate =\n" +
        "                Optional.ofNullable(location.getState())\n" +
        "                        .map(state -> ZoneQueryModel.of().locations().where(l -> l.country().is(location.getCountry()).and(l.state().is(state))))\n" +
        "                        .orElseGet(() -> ZoneQueryModel.of().locations().where(l -> l.country().is(location.getCountry()).and(l.state().isNotPresent())));\n" +
        "        return withPredicates(predicate);\n" +
        "    }")
@ResourceInfo(pluralName = "zones", pathElement = "zones", commonImports = {
        "com.neovisionaries.i18n.CountryCode",
        "io.sphere.sdk.zones.Location"
})
@HasByIdGetEndpoint(javadocSummary = "Gets a zone by ID.", includeExamples = "io.sphere.sdk.zones.queries.ZoneByIdGetIntegrationTest#fetchById()")
@HasByKeyGetEndpoint(javadocSummary = "Get Zone by Key")
@HasCreateCommand(includeExamples = "io.sphere.sdk.zones.commands.ZoneCreateCommandIntegrationTest#execution()")
@HasUpdateCommand(javadocSummary = "Updates a zone.", updateWith = "key")
@HasDeleteCommand(deleteWith = {"key","id"})
@HasQueryModel
public interface Zone extends Resource<Zone> {

    String getName();

    /**
     * @return Unique identifier for a zone. Must be unique across a project.
     */
    @HasUpdateAction
    @Nullable
    String getKey();

    @Nullable
    @IgnoreInQueryModel
    String getDescription();

    @QueryModelHint(type = "LocationsCollectionQueryModel<Zone>", impl = "return new LocationsCollectionQueryModelImpl<>(this, fieldName);")
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