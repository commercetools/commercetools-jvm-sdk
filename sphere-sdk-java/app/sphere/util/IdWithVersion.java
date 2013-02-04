package sphere.util;

import net.jcip.annotations.Immutable;
import org.codehaus.jackson.annotate.JsonCreator;
import org.codehaus.jackson.annotate.JsonProperty;

/** Helper object for the SDK to keep cart id and version in session. */
@Immutable
public class IdWithVersion {
    @JsonProperty("id") private final String id;
    @JsonProperty("version") private final int version;

    // tell the JSON deserializer to use this constructor (to be able to make fields final)
    @JsonCreator
    public IdWithVersion(@JsonProperty("id") String id, @JsonProperty("version") int version) {
        this.id = id;
        this.version = version;
    }

    public String id() { return id; }
    public int version() { return version; }

    @Override public String toString() {
        return String.format("[id: %s, version: %s]", id, version);
    }

    @Override public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        IdWithVersion that = (IdWithVersion)o;
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
