package io.sphere.sdk.client;

import io.sphere.sdk.utils.CompletableFutureUtils;
import org.junit.Test;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import static org.fest.assertions.Assertions.assertThat;

import static org.assertj.core.api.Assertions.*;

public class TimeoutSphereClientDecoratorTest {

    private static final int DELAY = 50;

    @Test
    public void throwsTimeoutExceptionOnTimeout() throws Exception {
        final SphereClient sphereClient = new SphereClient() {
            @Override
            public <T> CompletionStage<T> execute(final SphereRequest<T> sphereRequest) {
                return new CompletableFuture<>();
            }

            @Override
            public void close() {

            }
        };

        final SphereClient decoratedClient = TimeoutSphereClientDecorator.of(sphereClient, DELAY, TimeUnit.MILLISECONDS);
        assertThatThrownBy(() -> decoratedClient.execute(DummySphereRequest.of()).toCompletableFuture().join())
                .hasCauseInstanceOf(TimeoutException.class);
    }

    @Test
    public void throwsNoTimeOutOnResult() throws Exception {
        final SphereClient sphereClient = new SphereClient() {
            @Override
            @SuppressWarnings("unchecked")
            public <T> CompletionStage<T> execute(final SphereRequest<T> sphereRequest) {
                return CompletableFutureUtils.successful((T) "hello");
            }

            @Override
            public void close() {

            }
        };
        final SphereClient decoratedClient = TimeoutSphereClientDecorator.of(sphereClient, 50, TimeUnit.MILLISECONDS);
        final String s = decoratedClient.execute(DummySphereRequest.of()).toCompletableFuture().join();
        assertThat(s).isEqualTo("hello");
    }

    @Test
    public void throwsNoTimeOutOnOtherException() throws Exception {
        final Throwable t = new RuntimeException("x");
        final SphereClient sphereClient = new SphereClient() {
            @Override
            public <T> CompletionStage<T> execute(final SphereRequest<T> sphereRequest) {
                return CompletableFutureUtils.failed(t);
            }

            @Override
            public void close() {

            }
        };
        final SphereClient decoratedClient = TimeoutSphereClientDecorator.of(sphereClient, 50, TimeUnit.MILLISECONDS);
        assertThatThrownBy(() -> decoratedClient.execute(DummySphereRequest.of()).toCompletableFuture().join())
                .hasCause(t);
    }
}