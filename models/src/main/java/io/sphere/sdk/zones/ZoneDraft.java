package io.sphere.sdk.zones;

import com.neovisionaries.i18n.CountryCode;

import java.util.Optional;
import java.util.Set;

import static java.util.stream.Collectors.toSet;

public class ZoneDraft {
    private final String name;
    private final Optional<String> description;
    private final Set<Location> locations;

    private ZoneDraft(final String name, final Set<Location> locations, final Optional<String> description) {
        this.description = description;
        this.name = name;
        this.locations = locations;
    }

    public Optional<String> getDescription() {
        return description;
    }

    public Set<Location> getLocations() {
        return locations;
    }

    public String getName() {
        return name;
    }

    public static ZoneDraft of(final String name, final Set<Location> locations) {
        return of(name, locations, Optional.empty());
    }

    public static ZoneDraft of(final String name, final Set<Location> locations, final String description) {
        return of(name, locations, Optional.of(description));
    }

    public static ZoneDraft ofCountries(final String name, final Set<CountryCode> countries, final String description) {
        return ofCountries(name, countries, Optional.of(description));
    }

    public static ZoneDraft ofCountries(final String name, final Set<CountryCode> countries, final Optional<String> description) {
        final Set<Location> locations = countries.stream().map(country -> Location.of(country)).collect(toSet());
        return new ZoneDraft(name, locations, description);
    }

    public static ZoneDraft of(final String name, final Set<Location> locations, final Optional<String> description) {
        return new ZoneDraft(name, locations, description);
    }
}
