package sphere.util;

/** Simple validation-like helper used internally by the SDK. Contains either a value or an error. */
class Validation<T> {
    private ServiceError error = null;
    private T value = null;

    Validation(ServiceError error) {
        this.error = error;
    }

    Validation(T value) {
        this.value = value;
    }

    public ServiceError getError() {
        return error;
    }
    public boolean isError() {
        return error != null;
    }

    public T getValue() {
        return value;
    }
}
