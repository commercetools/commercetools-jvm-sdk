package io.sphere.sdk.meta;

import io.sphere.sdk.categories.Category;
import io.sphere.sdk.categories.commands.CategoryDeleteCommand;
import io.sphere.sdk.categories.queries.CategoryQuery;
import io.sphere.sdk.queries.ExperimentalForAll;
import io.sphere.sdk.test.IntegrationTest;
import org.junit.BeforeClass;
import org.junit.Test;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.util.concurrent.CompletableFuture;

public class CategoryDocumentationTest extends IntegrationTest {
    @BeforeClass
    public static void deleteAllCategories() {
        final Publisher<Category> publisher = ExperimentalForAll.of(CategoryQuery.of()).publisher(client().getUnderlying());
        final CategoryDeleteSubscriber subscriber = new CategoryDeleteSubscriber();
        publisher.subscribe(subscriber);
        subscriber.getFuture().join();
    }

    @Test
    public void foo() throws Exception {


    }

    private static class CategoryDeleteSubscriber implements Subscriber<Category> {
        private final CompletableFuture<Void> future = new CompletableFuture<>();
        private Subscription subscription;

        @Override
        public void onSubscribe(final Subscription subscription) {
            this.subscription = subscription;
            requestElements();
        }

        private void requestElements() {
            subscription.request(1);
        }

        @Override
        public void onNext(final Category category) {
            client().getUnderlying().execute(CategoryDeleteCommand.of(category)).whenComplete((c, t) -> {
                requestElements();
            });
        }

        @Override
        public void onError(final Throwable throwable) {
            future.completeExceptionally(throwable);
        }

        @Override
        public void onComplete() {
            future.complete(null);
        }

        public CompletableFuture<Void> getFuture() {
            return future;
        }
    }
}