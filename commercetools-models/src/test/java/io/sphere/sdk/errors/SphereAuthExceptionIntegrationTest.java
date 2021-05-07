package io.sphere.sdk.errors;

import io.sphere.sdk.client.*;
import io.sphere.sdk.http.HttpClient;
import io.sphere.sdk.http.HttpException;
import io.sphere.sdk.http.HttpRequest;
import io.sphere.sdk.http.HttpResponse;
import io.sphere.sdk.json.JsonException;
import io.sphere.sdk.models.SphereException;
import io.sphere.sdk.projects.Project;
import io.sphere.sdk.projects.queries.ProjectGet;
import io.sphere.sdk.test.IntegrationTest;
import org.assertj.core.api.Condition;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.net.UnknownHostException;
import java.time.Duration;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;

import static io.sphere.sdk.client.SphereClientUtils.blockingWait;
import static io.sphere.sdk.test.SphereTestUtils.assertEventually;
import static org.assertj.core.api.Assertions.*;

public class SphereAuthExceptionIntegrationTest extends IntegrationTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void serverProblemsRecover() throws Exception {
        final int failCount = 5;
        final String token = "foo";
        try(final HttpClient httpClient = newFailingThenSuccessfullHttpClient(failCount, token)) {
            try (final SphereAccessTokenSupplier supplier = SphereAccessTokenSupplier.ofAutoRefresh(getSphereClientConfig(), httpClient, false)) {
                final Throwable t = catchThrowable(() -> blockingWait(supplier.get(), 2000, TimeUnit.MILLISECONDS));
                assertThat(t).isInstanceOf(SphereException.class).hasCauseInstanceOf(HttpException.class);
                assertEventually(() -> {
                    final CompletionStage<String> tokenStage = supplier.get();
                    assertThat(tokenStage).is(successfullyCompletedWithin(2000, TimeUnit.MILLISECONDS));
                    assertThat(tokenStage.toCompletableFuture().join()).isEqualTo(token);
                });
            }
        }
    }

    private Condition<CompletionStage<String>> successfullyCompletedWithin(final long time, final TimeUnit timeUnit) {
        return new Condition<CompletionStage<String>>() {

            @Override
            public boolean matches(final CompletionStage<String> value) {
                boolean successful = true;
                try {
                    value.toCompletableFuture().get(time, timeUnit);
                } catch (final Exception e) {
                    successful = false;
                }

                return successful;
            }
        };
    }

    @Test
    public void serverProblemsFail() throws Exception {
        final int failCount = 9000;
        final String token = "foo";
        try(final HttpClient httpClient = newFailingThenSuccessfullHttpClient(failCount, token)) {
            try (final SphereAccessTokenSupplier supplier = SphereAccessTokenSupplier.ofAutoRefresh(getSphereClientConfig(), httpClient, false)) {
                final Throwable t = catchThrowable(() -> blockingWait(supplier.get(), 2000, TimeUnit.MILLISECONDS));
                assertThat(t).isInstanceOf(SphereException.class).hasCauseInstanceOf(HttpException.class);
            }
        }
    }

    private HttpClient newFailingThenSuccessfullHttpClient(final int failCount, final String token) {
        return new HttpClient() {
            private final AtomicInteger counter = new AtomicInteger(0);

            @Override
            public CompletionStage<HttpResponse> execute(final HttpRequest httpRequest) {
                return counter.incrementAndGet() > failCount ? successResponse() : failingResponse();
            }

            private CompletionStage<HttpResponse> failingResponse() {
                return CompletableFuture.completedFuture(HttpResponse.of(502, "{}"));
            }

            private CompletionStage<HttpResponse> successResponse() {
                return CompletableFuture.completedFuture(HttpResponse.of(200, "{\"access_token\": \"foo\"}"));
            }

            @Override
            public void close() {

            }
        };
    }

    @Test
    public void misconfigurationDomainInAuth() throws Exception {
        if (!"false".equals(System.getenv("JVM_SDK_IT_SSL_VALIDATION"))) {
            try(final HttpClient httpClient = newHttpClient()) {
                final String authUrl = "http://sdfjifdsjifdsjfdsjdfsjidsfjidfs.de";
                final String apiUrl = getSphereClientConfig().getApiUrl();
                final SphereClientConfig config = SphereClientConfig.of("projectKey", "clientId", "clientSecret", authUrl, apiUrl);
                try (final SphereAccessTokenSupplier supplier = SphereAccessTokenSupplier.ofAutoRefresh(config, httpClient, false)) {
                    supplier.close();
                    assertThatThrownBy(() -> supplier.get()).isInstanceOf(IllegalStateException.class);
                }
            }
        }
    }

    @Test
    public void clientAlreadyClosed() throws Exception {
        if (!"false".equals(System.getenv("JVM_SDK_IT_SSL_VALIDATION"))) {
            try (final SphereClient client = SphereClientFactory.of().createClient(getSphereClientConfig())) {
                client.close();
                assertThatThrownBy(() -> client.execute(ProjectGet.of()))
                        .isInstanceOf(IllegalStateException.class);
            }
        }
    }

    @Test
    public void misconfiguredAuthUrl() throws Exception {
        if (!"false".equals(System.getenv("JVM_SDK_IT_SSL_VALIDATION"))) {
            final String invalidAuthUrl = "http://sdfjifdsjifdsjfdsjdfsjidsfjidfs.de";
            final SphereClientConfig config = getSphereClientConfig().withAuthUrl(invalidAuthUrl);
            try (final SphereClient client = SphereClientFactory.of().createClient(config)) {
                final Throwable throwable =
                        catchThrowable(() -> blockingWait(client.execute(ProjectGet.of()), Duration.ofMillis(1000)));
                assertThat(throwable).isInstanceOf(HttpException.class).hasCauseInstanceOf(UnknownHostException.class);
                assertThat(client).isNot(closed());
            }
        }
    }

    @Test
    public void misconfiguredApiUrl() throws Exception {
        if (!"false".equals(System.getenv("JVM_SDK_IT_SSL_VALIDATION"))) {
            final String invalidApiUrl = "http://sdfjifdsjifdsjfdsjdfsjidsfjidfs.de";
            final SphereClientConfig config = getSphereClientConfig().withApiUrl(invalidApiUrl);
            try (final SphereClient client = SphereClientFactory.of().createClient(config)) {
                final Throwable throwable =
                        catchThrowable(() -> blockingWait(client.execute(ProjectGet.of()), Duration.ofMillis(1000)));
                assertThat(throwable).isInstanceOf(HttpException.class).hasCauseInstanceOf(UnknownHostException.class);
                assertThat(client).isNot(closed());
            }
        }
    }

    @Test
    public void invalidCredentialsClient() throws Exception {
        if (!"false".equals(System.getenv("JVM_SDK_IT_SSL_VALIDATION"))) {
            final SphereClientConfig config = SphereClientConfigBuilder.ofClientConfig(getSphereClientConfig())
                    .clientSecret("wrong")
                    .build();
            try (final SphereClient client = SphereClientFactory.of().createClient(config)) {
                final CompletionStage<Project> completionStage = client.execute(ProjectGet.of());
                final Throwable throwable = catchThrowable(() -> blockingWait(completionStage, Duration.ofMillis(5000)));
                assertThat(throwable).isInstanceOf(InvalidClientCredentialsException.class);
                assertEventually(() -> {
                    assertThat(client).is(closed());
                });
            }
        }
    }

    private static Condition<SphereClient> closed() {
        return new Condition<SphereClient>() {
            @Override
            public boolean matches(final SphereClient client) {
                final Throwable throwable = catchThrowable(() -> blockingWait(client.execute(ProjectGet.of()), Duration.ofMillis(100)));
                return throwable != null && throwable instanceof IllegalStateException;
            }
        }.describedAs("closed");
    }

    @Test
    public void invalidCredentialsToGetToken() throws Throwable {
        checkInvalidCredentialForAuthTokenSupplier(config -> SphereAccessTokenSupplier.ofOneTimeFetchingToken(config, newHttpClient(), true));
    }

    @Test
    public void invalidCredentialsToGetTokenForAutoRefresh() throws Throwable {
        checkInvalidCredentialForAuthTokenSupplier(config -> SphereAccessTokenSupplier.ofAutoRefresh(config, newHttpClient(), true));
    }

    @Test
    public void invalidJson() throws Throwable {
        final String response = "{invalid}";
        final SphereAuthConfig config = SphereClientConfig.of(getSphereClientConfig().getProjectKey(), "foo", "bar");
        final SphereAccessTokenSupplier tokenSupplier = SphereAccessTokenSupplier.ofOneTimeFetchingToken(config, new HttpClient() {
            @Override
            public CompletionStage<HttpResponse> execute(final HttpRequest httpRequest) {
                return CompletableFuture.completedFuture(HttpResponse.of(200, response));
            }

            @Override
            public void close() {

            }
        }, true);
        assertThat(tokenSupplier.get().toCompletableFuture())
                .hasFailed()
                .hasFailedWithThrowableThat()
                .isInstanceOf(JsonException.class)
                .hasMessageContaining("{invalid}")
                .hasMessageContaining("http request: HttpRequestImpl[")
                .hasMessageContaining("httpMethod=POST,url=https://auth.europe-west1.gcp.commercetools.com/oauth/token");
    }

    private void checkInvalidCredentialForAuthTokenSupplier(final Function<SphereAuthConfig, SphereAccessTokenSupplier> authTokenSupplierSupplier) {
        final SphereAuthConfig config = SphereAuthConfig.of(getSphereClientConfig().getProjectKey(), getSphereClientConfig().getClientId(), "wrong-password", getSphereClientConfig().getAuthUrl());
        final SphereAccessTokenSupplier authTokenSupplier = authTokenSupplierSupplier.apply(config);
        assertThatThrownBy(() -> authTokenSupplier.get().toCompletableFuture().join())
                .hasCauseInstanceOf(InvalidClientCredentialsException.class);
        final Throwable throwable = catchThrowable(() -> authTokenSupplier.get().toCompletableFuture().get(20, TimeUnit.MILLISECONDS));
        assertThat(throwable)
                .as("further requests get also an exception and do not hang")
                .isInstanceOfAny(IllegalStateException.class, ExecutionException.class);
        authTokenSupplier.close();
    }
}
