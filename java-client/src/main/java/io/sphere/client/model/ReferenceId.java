package io.sphere.client.model;

import javax.annotation.Nonnull;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@JsonIgnoreProperties({"expanded"})
public class ReferenceId<T> {
    @Nonnull private String id;
    @Nonnull private String typeId;

    // for JSON deserializer
    protected ReferenceId() { }

    private ReferenceId(@Nonnull String typeId, @Nonnull String id) {
        this.id = id;
        this.typeId = typeId;
    }

    public static <T> ReferenceId<T> create(String typeId, String id) {
        return new ReferenceId<T>(typeId, id);
    }

    /** Id of the object this reference represents. */
    @Nonnull public String getId() { return id; }

    /** Type id of the object this reference represents, e.g. "customer". */
    @Nonnull public String getTypeId() { return typeId; }

    @Override public String toString() {
        return "[Reference " + getTypeId() + " " + getId() + "]";
    }
}
