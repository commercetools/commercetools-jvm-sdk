package io.sphere.client.model;

import net.jcip.annotations.Immutable;
import org.codehaus.jackson.annotate.JsonCreator;
import org.codehaus.jackson.annotate.JsonProperty;

/** Id and version of a backend object. */
@Immutable
public class VersionedId {
    @JsonProperty("id") private final String id;
    @JsonProperty("version") private final int version;

    // tell the JSON deserializer to use this constructor (to be able to make fields final)
    @JsonCreator
    public VersionedId(@JsonProperty("id") String id, @JsonProperty("version") int version) {
        this.id = id;
        this.version = version;
    }

    /** The id. */
    public String getId() { return id; }
    /** The version. */
    public int getVersion() { return version; }

    @Override public String toString() {
        return String.format("[id: %s, version: %s]", id, version);
    }

    // ---------------------------
    // equals() and hashCode()
    // ---------------------------

    @Override public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VersionedId that = (VersionedId)o;
        if (version != that.version) return false;
        if (!id.equals(that.id)) return false;
        return true;
    }

    @Override public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + version;
        return result;
    }
}
