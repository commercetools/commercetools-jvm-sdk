package sphere.util;

import net.jcip.annotations.Immutable;
import org.codehaus.jackson.annotate.JsonCreator;
import org.codehaus.jackson.annotate.JsonProperty;

/** Helper object for the SDK to keep cart id and version in session. */
@Immutable
public class IdWithVersion {
    private final String id;
    private final int version;

    // tell the JSON deserializer to use this constructor (to be able to make fields final)
    @JsonCreator
    public IdWithVersion(@JsonProperty("id") String id, @JsonProperty("version") int version) {
        this.id = id;
        this.version = version;
    }

    public String getId() { return id; }
    public int getVersion() { return version; }

    @Override
    public String toString() {
        return string(id, version);
    }

    public static String string(String id, int version) {
        return String.format("[id: %s, version: %s]", id, version);
    }
}
