package io.sphere.sdk.errors;

public class InternalServerErrorException extends ServerErrorException {
    static final long serialVersionUID = 0L;

    public InternalServerErrorException() {
        super(500);
    }
}
