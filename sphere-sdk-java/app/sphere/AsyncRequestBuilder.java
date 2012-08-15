package sphere;

import play.libs.F;
import com.google.common.base.Function;

/** Represents an asynchronous request to the Sphere backend.
 *  Use {@link #get()} to obtain a promise with the result. */
public interface AsyncRequestBuilder<T> extends RequestBuilder<F.Promise<T>> {
    // inherits F.Promise<T> get()
}
