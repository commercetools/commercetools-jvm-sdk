package io.sphere.sdk.zones;

import com.neovisionaries.i18n.CountryCode;

import javax.annotation.Nullable;
import java.util.Set;

import static java.util.stream.Collectors.toSet;

public class ZoneDraft {
    private final String name;
    @Nullable
    private final String description;
    private final Set<Location> locations;

    private ZoneDraft(final String name, final Set<Location> locations, @Nullable final String description) {
        this.description = description;
        this.name = name;
        this.locations = locations;
    }

    @Nullable
    public String getDescription() {
        return description;
    }

    public Set<Location> getLocations() {
        return locations;
    }

    public String getName() {
        return name;
    }

    public static ZoneDraft of(final String name, final Set<Location> locations) {
        return of(name, locations, null);
    }

    public static ZoneDraft ofCountries(final String name, final Set<CountryCode> countries, @Nullable final String description) {
        final Set<Location> locations = countries.stream().map(country -> Location.of(country)).collect(toSet());
        return new ZoneDraft(name, locations, description);
    }

    public static ZoneDraft of(final String name, final Set<Location> locations, @Nullable final String description) {
        return new ZoneDraft(name, locations, description);
    }
}
