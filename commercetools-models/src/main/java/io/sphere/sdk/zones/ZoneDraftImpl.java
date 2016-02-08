package io.sphere.sdk.zones;

import com.fasterxml.jackson.annotation.JsonCreator;
import io.sphere.sdk.models.Base;

import javax.annotation.Nullable;
import java.util.Set;

final class ZoneDraftImpl extends Base implements ZoneDraft {
    private final String name;
    @Nullable
    private final String description;
    private final Set<Location> locations;

    @JsonCreator
    ZoneDraftImpl(final String name, final Set<Location> locations, @Nullable final String description) {
        this.description = description;
        this.name = name;
        this.locations = locations;
    }

    @Override
    @Nullable
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
