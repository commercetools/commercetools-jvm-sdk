package io.sphere.sdk.client;

import io.sphere.sdk.models.Base;
import io.sphere.sdk.utils.CompletableFutureUtils;
import org.junit.Test;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

import static io.sphere.sdk.client.ClientTestWrapper.execute;
import static org.assertj.core.api.Assertions.assertThat;

public class QueueSphereClientDecoratorTest {

     static final String DUMMY_RESULT = "hello";
     static final SphereClient FAKE_SPHERE_CLIENT = new FakeSphereClient();
     static final int MAX_PARALLEL_REQUESTS = 100;
     static final DummySphereRequest SPHERE_REQUEST_SUCCESS = DummySphereRequest.of("success");
     static final DummySphereRequest SPHERE_REQUEST_FINISH_ON_COMMAND = DummySphereRequest.of("wait");

    @Test
    public void worksAtAll() throws Exception {
        final SphereClient client = QueueSphereClientDecorator.of(FAKE_SPHERE_CLIENT, 100);
        final String s = execute(client, SPHERE_REQUEST_SUCCESS);
        assertThat(s).isEqualTo(DUMMY_RESULT);
    }

    @Test
    public void underTheLimitNewRequestsAreProcessed() throws Exception {
        final SphereClient client = QueueSphereClientDecorator.of(FAKE_SPHERE_CLIENT, MAX_PARALLEL_REQUESTS);
        for (int i = 0; i < MAX_PARALLEL_REQUESTS - 1; i++) {
            client.execute(SPHERE_REQUEST_FINISH_ON_COMMAND);
        }
        final String s = execute(client, SPHERE_REQUEST_SUCCESS);
        assertThat(s).isEqualTo(DUMMY_RESULT);
    }

    @Test
    public void requestsOverMaxParallelAreNotProcessed() throws Exception {
        final FakeSphereClient delegate = new FakeSphereClient();
        final SphereClient client = QueueSphereClientDecorator.of(delegate, MAX_PARALLEL_REQUESTS);
        final int numberOfRequests = MAX_PARALLEL_REQUESTS + 20;
        for (int i = 0; i < numberOfRequests; i++) {
            client.execute(SPHERE_REQUEST_FINISH_ON_COMMAND);
        }
        delegate.waitForRequestCount(MAX_PARALLEL_REQUESTS);
        Thread.sleep(100);//test if in the meantime no request is processed
        assertThat(delegate.getUsageCounter()).isEqualTo(MAX_PARALLEL_REQUESTS);
        delegate.release();
        delegate.waitForRequestCount(numberOfRequests);



        Thread.sleep(100);//test if in the meantime no request is processed

        assertThat(delegate.getUsageCounter()).isEqualTo(numberOfRequests);
    }

    private static class FakeSphereClient extends Base implements SphereClient {
        private int usageCounter = 0;

        private final List<Runnable> releaseOnCommand = Collections.synchronizedList(new LinkedList<>());
        private final List<CompletionStage<String>> responseFutures = Collections.synchronizedList(new LinkedList<>());

        @Override
        public <T> CompletionStage<T> execute(final SphereRequest<T> sphereRequest) {
            usageCounter++;
            final CompletionStage<T> result = produceResult(sphereRequest);
            addResult(result);
            return result;
        }

        private <T> CompletionStage<T> produceResult(final SphereRequest<T> sphereRequest) {
            final CompletionStage<T> result;
            if (SPHERE_REQUEST_SUCCESS.equals(sphereRequest)) {
                result = successful();
            } else if (SPHERE_REQUEST_FINISH_ON_COMMAND.equals(sphereRequest)) {
                final CompletableFuture<T> future = new CompletableFuture<>();
                final Runnable releaseCommand = getRunnable(future, usageCounter);
                releaseOnCommand.add(releaseCommand);
                result = future;
            } else {
                throw new RuntimeException();
            }
            return result;
        }

        @SuppressWarnings("unchecked")
        private <T> void addResult(final CompletionStage<T> result) {
            responseFutures.add((CompletionStage<String>) result);
        }

        @SuppressWarnings("unchecked")
        private <T> Runnable getRunnable(final CompletableFuture<T> future, final int currentUsageCounter) {
            return () -> future.complete((T) "done");
        }

        @Override
        public void close() {

        }

        public int getUsageCounter() {
            return usageCounter;
        }

        public void release() {
            releaseOnCommand.forEach(runnable -> runnable.run());
            releaseOnCommand.clear();
        }

        public List<CompletionStage<String>> getResponseFutures() {
            return responseFutures;
        }

        public void waitForRequestCount(final int count) {
            final int waitMillis = 50;
            int ttl = 1000;
            while (ttl > 0 && responseFutures.size() < count) {
                try {
                    Thread.sleep(waitMillis);
                    ttl -= waitMillis;
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            if (responseFutures.size() < count) {
                throw new RuntimeException("timeout for request count " + count + ", was only " + responseFutures.size());
            }
        }

        @Override
        public SphereApiConfig getConfig() {
            return null;
        }
    }

    @SuppressWarnings("unchecked")
    private static  <T> CompletionStage<T> successful() {
        return (CompletionStage<T>) CompletableFutureUtils.successful("hello");
    }
}