package io.sphere.sdk.client;

/**
 *
 * Known causes:
 *
 * <ul>
 *     <li>Updating an object which does not exist (anymore)</li>
 * </ul>
 *
 *
 */
public class NotFoundException extends ClientErrorException {
    private static final long serialVersionUID = 0L;

    public NotFoundException() {
        super(404);
    }

    public NotFoundException(final String message) {
        super(message, 404);
    }
}
