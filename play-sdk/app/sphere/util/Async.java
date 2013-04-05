package sphere.util;

import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import play.api.libs.concurrent.Promise;
import play.libs.Akka;
import play.mvc.Result;
import play.mvc.Results;
import scala.concurrent.Future;

/** Static helpers for asynchronous programming. */
public final class Async {
    private Async() {}

    /** Creates Play's AsyncResult based on Guava's ListenableFuture. */
    public static Results.AsyncResult asyncResult(ListenableFuture<Result> resultFuture) {
        return Results.async(Akka.asPromise(asScalaFuture(resultFuture)));
    }

    /** Converts Guava's ListenableFuture to scala.concurrent.Future. */
    private static <V> Future<V> asScalaFuture(ListenableFuture<V> future) {
        final scala.concurrent.Promise<V> promise = Promise.apply();
        Futures.addCallback(future, new FutureCallback<V>() {
            @Override public void onSuccess(V result) {
                promise.success(result);
            }
            @Override public void onFailure(Throwable t) {
                promise.failure(t);
            }
        });
        return promise.future();
    }
}
