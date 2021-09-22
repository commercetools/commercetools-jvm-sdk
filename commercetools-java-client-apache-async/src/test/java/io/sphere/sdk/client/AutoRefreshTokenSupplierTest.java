package io.sphere.sdk.client;

import io.sphere.sdk.http.HttpClient;
import io.sphere.sdk.http.HttpRequest;
import io.sphere.sdk.http.HttpResponse;
import io.sphere.sdk.models.Base;
import org.junit.Test;

import java.util.concurrent.CompletionStage;
import java.util.concurrent.atomic.AtomicInteger;

import static org.assertj.core.api.Assertions.assertThat;

public class AutoRefreshTokenSupplierTest {

    public static class TestDoubleClosedHttpClient extends Base implements HttpClient {
        private final AtomicInteger timesCalled = new AtomicInteger(0);
        private final HttpClient client;

        public TestDoubleClosedHttpClient() {
            this.client = SphereClientFactory.of().createHttpClient();
            client.close();
        }

        @Override
        public final CompletionStage<HttpResponse> execute(final HttpRequest httpRequest) {
            timesCalled.incrementAndGet();
            return client.execute(httpRequest);
        }


        @Override
        public void close() {

        }

        public int getTimesCalledCount() {
            return timesCalled.get();
        }
    }

    @Test
    public void closedClient() throws InterruptedException {
        final TestDoubleClosedHttpClient httpClient = new TestDoubleClosedHttpClient();
        try(final SphereAccessTokenSupplier supplier =
                    AutoRefreshSphereAccessTokenSupplierImpl.createAndBeginRefreshInBackground(SphereAuthConfig.of("project-key", "client-id", "clientSecret"), httpClient, true)) {
            Thread.sleep(1000);
            assertThat(httpClient.getTimesCalledCount()).isEqualTo(2);
        }
    }
}
