package io.sphere.sdk.exceptions;

public class InternalServerErrorException extends ServerErrorException {
    static final long serialVersionUID = 0L;

    public InternalServerErrorException() {
        super(500);
    }
}
