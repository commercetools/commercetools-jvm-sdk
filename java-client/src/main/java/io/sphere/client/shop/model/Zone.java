package io.sphere.client.shop.model;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nonnull;
import io.sphere.client.model.VersionedId;
import org.codehaus.jackson.annotate.JsonProperty;

/** Zone groups locations. */
public class Zone {
    @Nonnull private String id;
    @JsonProperty("version") private int version;
    @Nonnull private String name;
    @Nonnull private String description = "";
    @Nonnull private List<Location> locations = new ArrayList<Location>();

    // for JSON deserializer
    protected Zone() {}

    /** The unique id. */
    @Nonnull public String getId() { return id; }

    /** The {@link #getId() id} plus version. */
    @Nonnull public VersionedId getIdAndVersion() { return VersionedId.create(id, version); }

    /** The name of the zone. */
    @Nonnull public String getName() { return name; }

    /** The description of the zone. */
    @Nonnull public String getDescription() { return description; }

    /** The locations that belong to the zone. */
    @Nonnull public List<Location> getLocations() { return locations; }
}
