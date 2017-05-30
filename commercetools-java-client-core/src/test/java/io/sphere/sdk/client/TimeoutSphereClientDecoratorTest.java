package io.sphere.sdk.client;

import io.sphere.sdk.utils.CompletableFutureUtils;
import org.junit.Test;

import java.util.concurrent.CompletionStage;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class TimeoutSphereClientDecoratorTest {

    private static final int DELAY = 50;

    @Test
    public void throwsTimeoutExceptionOnTimeout() throws Exception {
        final SphereClient sphereClient = new NotAnsweringSphereClient();

        final SphereClient decoratedClient = TimeoutSphereClientDecorator.of(sphereClient, DELAY, TimeUnit.MILLISECONDS);
        assertThatThrownBy(() -> decoratedClient.execute(DummySphereRequest.of()).toCompletableFuture().join())
                .hasCauseInstanceOf(SphereTimeoutException.class);
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

            @Override
            public SphereApiConfig getConfig() {
                return null;
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

            @Override
            public SphereApiConfig getConfig() {
                return null;
            }
        };
        final SphereClient decoratedClient = TimeoutSphereClientDecorator.of(sphereClient, 50, TimeUnit.MILLISECONDS);
        assertThatThrownBy(() -> decoratedClient.execute(DummySphereRequest.of()).toCompletableFuture().join())
                .hasCause(t);
    }
}