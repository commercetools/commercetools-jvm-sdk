package io.sphere.sdk.client;

import io.sphere.sdk.http.HttpClient;
import io.sphere.sdk.http.HttpRequest;
import io.sphere.sdk.http.HttpResponse;
import io.sphere.sdk.models.Base;
import org.junit.Test;
import org.slf4j.LoggerFactory;

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
            Thread.sleep(4000);
            assertThat(httpClient.getTimesCalledCount()).isGreaterThan(2).isLessThan(6);
        }
    }

    @Test
    public void selectNextRetryTime() {
        final long aDay = 60 * 60 * 24L;
        assertThat(AuthActor.selectNextRetryTime(20 * aDay)).as("max one day").isEqualTo(aDay);
        assertThat(AuthActor.selectNextRetryTime((long)(0.5 * aDay))).as("half the time of the expire time").isEqualTo(aDay / 4);
        assertThat(AuthActor.selectNextRetryTime(-100L)).as("at least a second").isEqualTo(1L);
    }

    @Test
    public void testSuspendedClient() throws Exception {
        final SuspendableDoubleHttpClient httpClient = getSuspendableHttpClient();

        try(final SphereAccessTokenSupplier supplier =
                AutoRefreshSphereAccessTokenSupplierImpl.createAndBeginRefreshInBackground(SphereAuthConfig.of("project-key", "client-id", "clientSecret"), httpClient, true)) {
            Thread.sleep(2000);
            httpClient.suspended = true;
            Thread.sleep(120000);
            LoggerFactory.getLogger(AutoRefreshSphereAccessTokenSupplierImplTest.class).debug("Project unsuspended");
            httpClient.suspended = false;
            Thread.sleep(10000);
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

    private SuspendableDoubleHttpClient getSuspendableHttpClient() {
        return new SuspendableDoubleHttpClient() {
            @Override
            protected HttpResponse executeSync(final HttpRequest httpRequest, final int requestId) {
                if (suspended) {
                    return HttpResponse.of(400, "{\n" +
                            "      \"statusCode\" : 400,\n" +
                            "      \"message\" : \"Project 'test-project-suspension-1' is suspended\",\n" +
                            "      \"errors\" : [ {\n" +
                            "        \"code\" : \"invalid_scope\",\n" +
                            "        \"message\" : \"Project 'test-project-suspension-1' is suspended\"\n" +
                            "      } ],\n" +
                            "      \"error\" : \"invalid_scope\",\n" +
                            "      \"error_description\" : \"Project 'test-project-suspension-1' is suspended\"\n" +
                            "    }");
                }
                return HttpResponse.of(200, "{\"access_token\": \"vkFuQ6oTwj8_Ye4eiRSsqMeqLYNeQRJi\", \"expires_in\": 1}");
            }
        };
    }

    public static abstract class SuspendableDoubleHttpClient extends TestDoubleHttpClient {
        public boolean suspended = false;
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
