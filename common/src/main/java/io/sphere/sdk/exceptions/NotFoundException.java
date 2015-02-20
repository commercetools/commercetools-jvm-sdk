package io.sphere.sdk.exceptions;

/**
 *
 * Known causes:
 *
 * <ul>
 *     <li>Updating a object which does not exist (anymore)</li>
 * </ul>
 *
 *
 */
public class NotFoundException extends ClientErrorException {
    private static final long serialVersionUID = 0L;

    public NotFoundException() {
        super(404);
    }
}
