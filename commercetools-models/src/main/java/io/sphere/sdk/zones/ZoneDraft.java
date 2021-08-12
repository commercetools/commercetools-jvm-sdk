package io.sphere.sdk.zones;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.neovisionaries.i18n.CountryCode;
import io.sphere.sdk.annotations.FactoryMethod;
import io.sphere.sdk.annotations.ResourceDraftValue;

import javax.annotation.Nullable;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;

@JsonDeserialize(as = ZoneDraftDsl.class)
@ResourceDraftValue(
        factoryMethods = {
            @FactoryMethod(parameterNames = {"name", "locations", "description"}),
            @FactoryMethod(parameterNames = {"name", "locations"})})
public interface ZoneDraft {
    @Nullable
    String getDescription();

    Set<Location> getLocations();

    String getName();

    /**
     * @return Unique identifier for a zone. Must be unique across a project.
     */
    @Nullable
    String getKey();



    static ZoneDraft ofCountries(final String name, final Set<CountryCode> countries, @Nullable final String description) {
        final Set<Location> locations = countries.stream().map(country -> Location.of(country)).collect(Collectors.toCollection(LinkedHashSet::new));
        return of(name, locations, description);
    }

    static ZoneDraft of(final String name, final Set<Location> locations, @Nullable final String description) {
        return ZoneDraftDsl.of(name, locations, description);
    }

    static ZoneDraft of(final String name, final Set<Location> locations) {
        return ZoneDraftDsl.of(name, locations);
    }

}
