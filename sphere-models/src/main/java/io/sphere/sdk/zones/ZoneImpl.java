package io.sphere.sdk.zones;

import com.fasterxml.jackson.annotation.JsonCreator;
import io.sphere.sdk.models.ResourceImpl;

import javax.annotation.Nullable;
import java.time.ZonedDateTime;
import java.util.Set;

final class ZoneImpl extends ResourceImpl<Zone> implements Zone {
    private final String name;
    @Nullable
    private final String description;
    private final Set<Location> locations;

    @JsonCreator
    ZoneImpl(final String id, final Long version, final ZonedDateTime createdAt, final ZonedDateTime lastModifiedAt, @Nullable final String description, final String name, final Set<Location> locations) {
        super(id, version, createdAt, lastModifiedAt);
        this.description = description;
        this.name = name;
        this.locations = locations;
    }

    @Nullable
    @Override
    public String getDescription() {
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
