package io.sphere.sdk.errors;

import io.sphere.sdk.categories.Category;
import io.sphere.sdk.client.*;
import io.sphere.sdk.http.HttpClient;
import io.sphere.sdk.http.HttpRequest;
import io.sphere.sdk.http.HttpResponse;
import io.sphere.sdk.json.JsonException;
import io.sphere.sdk.projects.Project;
import io.sphere.sdk.projects.queries.ProjectGet;
import io.sphere.sdk.queries.PagedQueryResult;
import io.sphere.sdk.test.IntegrationTest;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.time.Duration;
import java.util.concurrent.*;
import java.util.function.Function;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class SphereAuthExceptionIntegrationTest extends IntegrationTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void misconfigurationDomainInAuth() throws Exception {
        try(final HttpClient httpClient = newHttpClient()) {
            final String authUrl = "http://sdfjifdsjifdsjfdsjdfsjidsfjidfs.de";
            final String apiUrl = getSphereClientConfig().getApiUrl();
            final SphereClientConfig config = SphereClientConfig.of("projectKey", "clientId", "clientSecret", authUrl, apiUrl);
            try (final SphereAccessTokenSupplier supplier = SphereAccessTokenSupplier.ofAutoRefresh(config,httpClient,false)) {
                supplier.close();
                supplier.get().toCompletableFuture().get(200, TimeUnit.MILLISECONDS);
            }

        }
    }

    @Test
    public void clientAlreadyClosed() throws Exception {
        try(final SphereClient client = SphereClientFactory.of().createClient(getSphereClientConfig())) {
            client.close();
            assertThatThrownBy(() -> client.execute(ProjectGet.of()))
                    .isInstanceOf(IllegalStateException.class);
        }
    }

    @Test
    public void misconfigurationDomain() throws InterruptedException {
        try(final HttpClient httpClient = newHttpClient()) {
            final String authUrl = "http://sdfjifdsjifdsjfdsjdfsjidsfjidfs.de";
            final String apiUrl = getSphereClientConfig().getApiUrl();
            final SphereClientConfig config = SphereClientConfig.of("projectKey", "clientId", "clientSecret", authUrl, apiUrl);
            try (final SphereClient client = SphereClientFactory.of().createClient(config)) {
                System.err.println(client);
                try {
                    final Project project = SphereClientUtils.blockingWait(client.execute(ProjectGet.of()), Duration.ofMinutes(1000));

                    System.err.println(project);
                }catch (final Exception e) {
                    System.err.println("e");
                    System.err.println(e.getClass());
                    System.err.println(e);
                    e.printStackTrace();

                    Thread.sleep(30000);
                }
            }
        }
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
                return CompletableFuture.completedFuture(HttpResponse.of(400, response));
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
                .hasMessageContaining("http request: HttpRequestImpl[httpMethod=POST,url=https://auth.sphere.io/oauth/token");
    }

    private void checkInvalidCredentialForAuthTokenSupplier(final Function<SphereAuthConfig, SphereAccessTokenSupplier> authTokenSupplierSupplier) {
        final SphereAuthConfig config = SphereAuthConfig.of(getSphereClientConfig().getProjectKey(), getSphereClientConfig().getClientId(), "wrong-password", getSphereClientConfig().getAuthUrl());
        final SphereAccessTokenSupplier authTokenSupplier = authTokenSupplierSupplier.apply(config);
        assertThatThrownBy(() -> authTokenSupplier.get().toCompletableFuture().join())
                .hasCauseInstanceOf(InvalidClientCredentialsException.class);
        assertThatThrownBy(() -> authTokenSupplier.get().toCompletableFuture().get(20, TimeUnit.MILLISECONDS))
                .overridingErrorMessage("further requests get also an exception and do not hang")
                .hasCauseInstanceOf(InvalidClientCredentialsException.class);
        authTokenSupplier.close();
    }

    private void expectExceptionAndClose(final SphereClient client, final Class<InvalidTokenException> exceptionClass, final CompletionStage<PagedQueryResult<Category>> stage) throws Throwable {
        thrown.expect(exceptionClass);
        try {
            stage.toCompletableFuture().join();
        } catch (final CompletionException e) {
            client.close();
            throw e.getCause();
        }

    }

}
