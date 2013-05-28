package io.sphere.client.exceptions;

/** Exception thrown when signing up. */
public class EmailAlreadyInUseException extends SphereException {
    public EmailAlreadyInUseException(String offendingEmail) {
        super("Email already in use: " + offendingEmail);
    }
}
