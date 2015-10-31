package io.sphere.sdk.queries;

import io.sphere.sdk.models.Base;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

class CollectAllSubscriber<T> extends Base implements Subscriber<T> {
    protected Subscription subscription;
    private final List<T> elements = new LinkedList<>();
    private final CompletableFuture<List<T>> future = new CompletableFuture<>();

    @Override
    public void onComplete() {
        future.complete(elements);
    }

    @Override
    public void onError(final Throwable throwable) {
        future.completeExceptionally(throwable);
    }

    @Override
    public void onNext(final T element) {
        elements.add(element);
        subscription.request(1);
    }

    @Override
    public void onSubscribe(final Subscription subscription) {
        this.subscription = subscription;
        subscription.request(1);
    }

    public CompletionStage<List<T>> getCompletionStage() {
        return future;
    }
}
