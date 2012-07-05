package sphere;

import sphere.util.ServiceError;

/** Simple validation-like helper used internally by the SDK. Contains either a value or an error. */
class Validation<T> {
    private ServiceError error = null;
    private T value = null;

    public static <T> Validation<T> success(T value) {
        return new Validation<T>(value, null);
    }

    public static <T> Validation<T> failure(ServiceError error) {
        return new Validation<T>(null, error);
    }

    private Validation(T value, ServiceError error) {
        this.value = value;
        this.error = error;
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
