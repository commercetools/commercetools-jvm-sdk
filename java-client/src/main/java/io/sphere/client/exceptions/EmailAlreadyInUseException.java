package io.sphere.client.exceptions;

/** Thrown when signing up. */
public class EmailAlreadyInUseException extends SphereException {
    public EmailAlreadyInUseException(String offendingEmail) {
        super("Email already in use: " + offendingEmail);
    }
}
