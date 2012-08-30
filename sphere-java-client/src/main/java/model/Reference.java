package de.commercetools.sphere.client.model;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;
import de.commercetools.sphere.client.model.ReferenceException;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.Callable;

/** Link to an object using its type identifier and id.
 *  If the reference has been expanded, the linked object can be obtained using the {@link #get} method. */
@JsonIgnoreProperties({"expanded"})
public class Reference<T> {
    private String id;
    private String typeId;
    @JsonProperty("obj")
    private T obj;

    // for JSON deserializer
    protected Reference() { }

    /** Creates a dummy empty reference that serves as a null object to prevent NullPointerExceptions.
     * @param fieldName Name of the attribute in the parent object where the reference is used, for better error messages. */
    public static <T> Reference<T> empty(String fieldName) {
        return new EmptyReference<T>(fieldName);
    }
    
    /** Returns the object represented by this reference.
     *  If the reference has not been expanded, throws a {@link ReferenceException}. Never returns null.
     *  @throws ReferenceException If this reference has not been expanded. */
    public T get() throws ReferenceException {
        if (obj == null)
            throw new ReferenceException(
                    "Reference has not been expanded: [" + typeId + "]" + id + ". " +
                    "Consider requesting reference expansion (using expand()) when fetching the data from the backend.");
        return obj;
    }

    /** True if this reference has been expanded ({@link #get} returns an non-null object.) */
    public boolean isExpanded() {
        return obj != null;
    }
    /** Id of the object represented by this reference. */
    public String getId() {
        return id;
    }
    /** Type id of the object represented by this reference, e.g. 'vendor'. */
    public String getTypeId() {
        return typeId;
    }
}
