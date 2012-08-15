package de.commercetools.sphere.client.model;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

/** Link to an object using its id and type id.
 *  If the reference has been expanded, the linked object can be obtained using the {@link #get} method. */
@JsonIgnoreProperties({"expanded"})
public class Reference<T> {
    private String id;
    private String typeId;
    @JsonProperty("obj")
    private T obj;

    // for JSON deserializer
    private Reference() { }
    
    /** Returns the object represented by this reference.
     *  @throws ReferenceException If this reference has not been expanded. */
    public T get() throws de.commercetools.sphere.client.model.ReferenceException {
        if (obj == null)
            throw new ReferenceException("Reference has not been expanded: [" + typeId + "]" + id);
        return obj;
    }
    
    /** True if this reference has been expanded ({@link #get} returns an object.) */
    public boolean isExpanded() {
        return obj != null;
    }

    /** Id of the object represented by this reference. */
    public String getId() {
        return id;
    }

    /** Type id of the object represented by this reference. */
    public String getTypeId() {
        return typeId;
    }
}
