package io.sphere.sdk.client;

import io.sphere.sdk.utils.CompletableFutureUtils;
import org.junit.Test;

import java.util.concurrent.CompletionStage;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

public class BlockingSphereClientTest {

    @Test
    public void alsoServesTheBaseInterface() {
        final BlockingSphereClient blockingSphereClient = blockingClientOf(new SphereClient() {
            @SuppressWarnings("unchecked")
            @Override
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
        });
        assertThat(blockingSphereClient)
                .isInstanceOf(BlockingSphereClient.class)
                .as("also usable as classic sphere client")
                .isInstanceOf(SphereClient.class);
        assertThat(blockingSphereClient.execute(DummySphereRequest.of()).toCompletableFuture().join()).isEqualTo("hello");
    }

    @Test
    public void globalTimeout() {
        final BlockingSphereClient blockingSphereClient = createBlockingSphereClient();//reusable instance among threads

        final Throwable throwable = catchThrowable(() -> blockingSphereClient.executeBlocking(DummySphereRequest.of()));

        assertThat(throwable).isInstanceOf(SphereTimeoutException.class);
    }

    @Test
    public void timeoutPerCall() {
        final BlockingSphereClient blockingSphereClient = createBlockingSphereClient();

        final Throwable throwable = catchThrowable(() -> blockingSphereClient.executeBlocking(DummySphereRequest.of(), 1, TimeUnit.NANOSECONDS));

        assertThat(throwable).isInstanceOf(SphereTimeoutException.class);
        assertThat(throwable).hasMessageContaining("sphere request: DummySphereRequest");
    }

    private BlockingSphereClient createBlockingSphereClient() {
        final SphereClient sphereClient = new NotAnsweringSphereClient();
        return blockingClientOf(sphereClient);
    }

    private BlockingSphereClient blockingClientOf(final SphereClient sphereClient) {
        return BlockingSphereClient.of(sphereClient, 500, TimeUnit.MILLISECONDS);
    }
}