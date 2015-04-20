package io.sphere.assertasync;

import org.fest.assertions.Assertions;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.ExecutionException;
import java.util.function.Consumer;

public final class AsyncAssertions<T> {
    private final CompletionStage<T> stage;


    private AsyncAssertions(final CompletionStage<T> stage) {
        this.stage = stage;
    }

    public static <T> AsyncAssertions<T> performing(final CompletionStage<T> stage) {
        return new AsyncAssertions<>(stage);
    }

    @SuppressWarnings("unchecked")
    public <E extends Throwable> void completesExceptionally(final Class<E> errorClass, final Consumer<E> handler) throws Exception {
        try {
            stage.toCompletableFuture().get();
            Assertions.assertThat(true).isFalse();
        } catch (final InterruptedException e) {
            throw e;
        } catch (final ExecutionException e) {
            final Throwable cause = e.getCause();
            Assertions.assertThat(cause).overridingErrorMessage(String.format("Stage %s did not error with %s but with %s.", stage, errorClass, cause)).isInstanceOf(errorClass);
            handler.accept((E) cause);
        }
    }
}
