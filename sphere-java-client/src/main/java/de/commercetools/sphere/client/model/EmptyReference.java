package de.commercetools.sphere.client.model;

import de.commercetools.sphere.client.ReferenceException;
import org.codehaus.jackson.annotate.JsonValue;

/** A null object signalling that a reference field in some parent object was empty,
 * to prevent NullPointerExceptions and provide better error messages. */
public class EmptyReference<T> extends Reference<T> {

    private String fieldName;

    private EmptyReference(String fieldName) {
        this.fieldName = fieldName;
    }

    /** A field name hinting where this reference was used. */
    public String getFieldName() {
        return fieldName;
    }

    @JsonValue
    /** Tells JSON serializer to serialize this class as null. */
    protected Object toJson() {
        return null;
    }

    /** Creates a dummy empty reference that serves as a null object to prevent NullPointerExceptions.
     * @param fieldName Name of the attribute in the parent object where the reference is used, for better error messages. */
    public static <T> Reference<T> create(String fieldName) {
        return new EmptyReference<T>(fieldName);
    }

    private ReferenceException emptyReferenceException() {
        throw new ReferenceException("This object has no: " + fieldName + ".");
    }

    /** Checks whether a reference has been expanded.
     *
     * User code should always check for {@link #isExpanded} before calling {@link #get}.
     * This way, the user won't get an exception even if the reference was missing in the parent object. */
    @Override public boolean isExpanded() {
        return false;
    }
    @Override public boolean isEmpty() {
        return true;
    }
    @Override public T get() throws ReferenceException {
        throw emptyReferenceException();
    }
    @Override public String getId() throws ReferenceException {
        throw emptyReferenceException();
    }
    @Override public String getTypeId() throws ReferenceException {
        throw emptyReferenceException();
    }
}
