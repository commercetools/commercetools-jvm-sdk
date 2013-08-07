package sphere.util;

import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import io.sphere.client.SphereResult;
import io.sphere.internal.util.Util;
import play.api.libs.concurrent.Promise;
import play.libs.Akka;
import play.libs.F;
import play.mvc.Result;
import play.mvc.Results;
import scala.concurrent.Future;
import sphere.DeleteRequest;
import sphere.internal.DeleteRequestAdapter;

/** Static helpers for asynchronous programming. */
public final class Async {
    private Async() {}

    /** Creates Play's AsyncResult based on Guava's ListenableFuture. */
    public static Results.AsyncResult asyncResult(ListenableFuture<Result> resultFuture) {
        return Results.async(asPlayPromise(resultFuture));
    }

    /** Converts Guava's ListenableFuture to play.libs.F.Promise. */
    public static <T> F.Promise<T> asPlayPromise(ListenableFuture<T> future) {
        return Akka.asPromise(asScalaFuture(future));
    }

    /** Converts Guava's ListenableFuture to scala.concurrent.Future. */
    private static <T> Future<T> asScalaFuture(ListenableFuture<T> future) {
        final scala.concurrent.Promise<T> promise = Promise.apply();
        Futures.addCallback(future, new FutureCallback<T>() {
            @Override public void onSuccess(T result) {
                promise.success(result);
            }
            @Override public void onFailure(Throwable t) {
                promise.failure(t);
            }
        });
        return promise.future();
    }

    /** Blocks on a promise. */
    public static <T> T await(F.Promise<T> promise) {
        try {
            return promise.get(30L, java.util.concurrent.TimeUnit.SECONDS);
        } catch (Exception e) {
            throw Util.toSphereException(e);
        }
    }

    /** Blocks, extracts success value, converts Sphere errors to exceptions. */
    public static <T> T awaitResult(F.Promise<SphereResult<T>> promise) {
        SphereResult<T> result = await(promise); // If the promise itself failed, wrap the Exception as SphereException
        return Util.getValueOrThrow(result);
    }

    /** Executes a CommandRequest. */
    public static <T> F.Promise<SphereResult<T>> execute(io.sphere.client.CommandRequest<T> req) {
        return asPlayPromise(req.executeAsync());
    }

    // ------------------------------------------------------------
    // Adapters from Java client's Guava Futures to Play Promises
    // ------------------------------------------------------------

    public static <T> sphere.FetchRequest<T> adapt(io.sphere.client.FetchRequest<T> req) {
        return new sphere.internal.FetchRequestAdapter<T>(req);
    }

    public static <T> DeleteRequest<T> adapt(io.sphere.client.DeleteRequest<T> req) {
        return new DeleteRequestAdapter<T>(req);
    }

    public static <T> sphere.QueryRequest<T> adapt(io.sphere.client.QueryRequest<T> req) {
        return new sphere.internal.QueryRequestAdapter<T>(req);
    }

    public static <T> sphere.SearchRequest<T> adapt(io.sphere.client.SearchRequest<T> req) {
        return new sphere.internal.SearchRequestAdapter<T>(req);
    }
}