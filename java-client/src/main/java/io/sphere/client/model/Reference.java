package io.sphere.client.model;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;
import io.sphere.client.ReferenceException;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.Callable;

/** Link to an object using its id and type id.
 *  If the reference has been expanded, the linked object can be obtained using the {@link #get} method. */
@JsonIgnoreProperties({"expanded"})
public class Reference<T> {
    @Nonnull private String id;
    @Nonnull private String typeId;
    @JsonProperty("obj")
    private T obj;

    // for JSON deserializer
    protected Reference() { }

    /** Returns the object represented by this reference.
     *
     *  If the reference has not been expanded, throws a {@link ReferenceException}. Never returns null.
     *  @throws ReferenceException If this reference has not been expanded. */
    public T get() throws ReferenceException {
        if (obj == null)
            throw new ReferenceException(
                    "Reference has not been expanded: [" + typeId + "]" + id + ". " +
                    "Consider requesting reference expansion (using expand()) when fetching data from the Sphere backend.");
        return obj;
    }

    /** True if this reference has been expanded.
     *
     * If {@link #isExpanded} returns true, {@link #get} is guaranteed to return a non-null object.
     * User code should always check for {@link #isExpanded} before calling {@link #get}. */
    public boolean isExpanded() { return obj != null; }

    /** True if this reference has no value in the parent object.
     *
     * If {@link #isEmpty} returns true, there's not much to do with this reference - {@link #isExpanded}
     * will return false and all other methods will throw a {@link ReferenceException}. */
    public boolean isEmpty() { return false; }

    /** Id of the object this reference represents. */
    @Nonnull public String getId() { return id; }

    /** Type id of the object this reference represents, e.g. "customer". */
    @Nonnull public String getTypeId() { return typeId; }
}
