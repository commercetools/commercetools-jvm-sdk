package sphere.util;

import com.google.common.base.Function;
import com.google.common.util.concurrent.AbstractFuture;
import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;

import javax.annotation.Nonnull;

/** Future that wraps another future and maps exceptions to legal return values. */
public class RecoverFuture<V> extends AbstractFuture<V> implements FutureCallback<V> {
    private final @Nonnull Function<Throwable, V> catcher;
    private RecoverFuture(@Nonnull Function<Throwable, V> catcher) {
        if (catcher == null) throw new NullPointerException("catcher");
        this.catcher = catcher;
    }

    public static <V> ListenableFuture<V> recover(
            @Nonnull ListenableFuture<V> future,
            @Nonnull Function<Throwable, V> catcher) {
        if (future == null) throw new NullPointerException("future");
        if (catcher == null) throw new NullPointerException("catcher");
        RecoverFuture<V> recoverFuture = new RecoverFuture<V>(catcher);
        Futures.addCallback(future, recoverFuture);
        return recoverFuture;
    }

    @Override public void onSuccess(V result) {
        set(result);
    }

    @Override public void onFailure(Throwable t) {
        V result = null;
        boolean success = false;
        try {
            result = catcher.apply(t);
            success = true;
        } catch (Throwable e) {
            setException(e);
        }
        if (success) {
            set(result);
        }
    }
}
