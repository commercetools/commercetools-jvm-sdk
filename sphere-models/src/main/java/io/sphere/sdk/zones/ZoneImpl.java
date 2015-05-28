package io.sphere.sdk.zones;

import com.fasterxml.jackson.annotation.JsonCreator;
import io.sphere.sdk.models.DefaultModelImpl;

import java.time.ZonedDateTime;
import java.util.Optional;
import java.util.Set;

final class ZoneImpl extends DefaultModelImpl<Zone> implements Zone {
    private final String name;
    private final Optional<String> description;
    private final Set<Location> locations;

    @JsonCreator
    ZoneImpl(final String id, final long version, final ZonedDateTime createdAt, final ZonedDateTime lastModifiedAt, final Optional<String> description, final String name, final Set<Location> locations) {
        super(id, version, createdAt, lastModifiedAt);
        this.description = description;
        this.name = name;
        this.locations = locations;
    }

    @Override
    public Optional<String> getDescription() {
        return description;
    }

    @Override
    public Set<Location> getLocations() {
        return locations;
    }

    @Override
    public String getName() {
        return name;
    }
}
