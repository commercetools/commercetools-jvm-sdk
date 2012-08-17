package sphere;

/** Exception thrown when the Sphere backend responds with other status code than HTTP 200 OK. */
public class BackendException extends RuntimeException {

    public BackendException(String message) {
        super(message);
    }

    public BackendException(String message, Throwable cause) {
        super(message, cause);
    }
    
    public BackendException(Throwable cause) {
        super(cause);
    }
}
