package io.sphere.client.model;

import com.google.common.base.Strings;
import net.jcip.annotations.Immutable;
import org.codehaus.jackson.annotate.JsonCreator;
import org.codehaus.jackson.annotate.JsonProperty;

import javax.annotation.Nonnull;

/** Id and version of a backend object. */
@Immutable
public class VersionedId {
    @Nonnull @JsonProperty("id") private final String id;
    @JsonProperty("version") private final int version;

    // tell the JSON deserializer to use this constructor (to be able to make fields final)
    @JsonCreator
    private VersionedId(@JsonProperty("id") String id, @JsonProperty("version") int version) {
        if (Strings.isNullOrEmpty(id)) throw new IllegalArgumentException("id can't be empty.");
        this.id = id;
        this.version = version;
    }

    /** Creates a VersionedId with given id and version. */
    @Nonnull public static VersionedId create(String id, int version) {
        return new VersionedId(id, version);
    }

    /** The id. */
    @Nonnull public String getId() { return id; }
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
