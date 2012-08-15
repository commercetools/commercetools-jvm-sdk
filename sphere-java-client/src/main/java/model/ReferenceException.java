package de.commercetools.sphere.client.model;

/** Exception thrown on attempt to access an object inside a {@link Reference} that has not been expanded. */
public class ReferenceException extends Exception {

    public ReferenceException(String message) {
        super(message);
    }
}