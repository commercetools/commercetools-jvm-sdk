package sphere;

import com.google.common.base.Optional;
import net.jcip.annotations.Immutable;
import play.libs.F.Promise;

@Immutable
public interface DeleteRequest<T>{

    /** Executes the request and returns the deleted object. */
    Optional<T> execute();

    /** Executes the request asynchronously and returns a future of the deleted object. */
    Promise<Optional<T>> executeAsync();

}

