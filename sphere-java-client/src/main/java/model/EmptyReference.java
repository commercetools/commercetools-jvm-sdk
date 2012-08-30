package de.commercetools.sphere.client.model;

import org.codehaus.jackson.annotate.JsonValue;

/** A null object signalling that a reference field in some parent object was empty,
 * to prevent NullPointerExceptions and provide better error messages. */
public class EmptyReference<T> extends Reference<T> {

    private String fieldName;

    /** Creates a new instance of EmptyReference. */
    public EmptyReference(String fieldName) {
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

    private ReferenceException emptyReferenceException() {
        throw new ReferenceException("Reference: " + fieldName + " is empty.");
    }

    /** User code will typically check for {@link #isExpanded} before calling {@link #get}.
     * This way, the user won't get an exception even if the reference was missing in the parent object. */
    public boolean isExpanded() {
        return false;
    }
    @Override
    public T get() throws ReferenceException {
        throw emptyReferenceException();
    }
    @Override
    public String getId() throws ReferenceException {
        throw emptyReferenceException();
    }
    @Override
    public String getTypeId() throws ReferenceException {
        throw emptyReferenceException();
    }
}
