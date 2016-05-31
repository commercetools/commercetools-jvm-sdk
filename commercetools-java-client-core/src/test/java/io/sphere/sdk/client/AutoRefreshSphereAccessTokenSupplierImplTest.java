package io.sphere.sdk.client;

import io.sphere.sdk.http.HttpClient;
import io.sphere.sdk.http.HttpRequest;
import io.sphere.sdk.http.HttpResponse;
import io.sphere.sdk.models.Base;
import org.junit.Test;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.atomic.AtomicInteger;

import static org.assertj.core.api.Assertions.assertThat;

public class AutoRefreshSphereAccessTokenSupplierImplTest {
    @Test
    public void refreshesAfterTimeoutOfToken() throws Exception {
        final TestDoubleHttpClient httpClient = getHttpClient();
        try(final SphereAccessTokenSupplier supplier =
                AutoRefreshSphereAccessTokenSupplierImpl.createAndBeginRefreshInBackground(SphereAuthConfig.of("project-key", "client-id", "clientSecret"), httpClient, true)) {
            Thread.sleep(3000);
            assertThat(httpClient.getTimesCalledCount()).isGreaterThan(2).isLessThan(5);
        }
    }

    private TestDoubleHttpClient getHttpClient() {
        return new TestDoubleHttpClient() {
            @Override
            protected HttpResponse executeSync(final HttpRequest httpRequest, final int requestId) {
                return HttpResponse.of(200, "{\"access_token\": \"vkFuQ6oTwj8_Ye4eiRSsqMeqLYNeQRJi\", \"expires_in\": 1}");
            }
        };
    }

    public static abstract class TestDoubleHttpClient extends Base implements HttpClient {
        private final AtomicInteger timesCalled = new AtomicInteger(0);

        @Override
        public final CompletionStage<HttpResponse> execute(final HttpRequest httpRequest) {
            return CompletableFuture.completedFuture(executeSync(httpRequest, timesCalled.incrementAndGet()));
        }

        protected abstract HttpResponse executeSync(final HttpRequest httpRequest, final int requestId);

        @Override
        public void close() {

        }

        public int getTimesCalledCount() {
            return timesCalled.get();
        }
    }

}