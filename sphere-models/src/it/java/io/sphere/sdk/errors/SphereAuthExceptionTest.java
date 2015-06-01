package io.sphere.sdk.errors;

import io.sphere.sdk.categories.Category;
import io.sphere.sdk.categories.queries.CategoryQuery;
import io.sphere.sdk.client.*;
import io.sphere.sdk.queries.PagedQueryResult;
import io.sphere.sdk.test.IntegrationTest;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.concurrent.CompletionException;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class SphereAuthExceptionTest extends IntegrationTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void invalidCredentialsToGetToken() throws Throwable {
        checkInvalidCredentialForAuthTokenSupplier(config -> SphereAccessTokenSupplierFactory.of().createSupplierOfOneTimeFetchingToken(config));
    }

    @Test
    public void invalidCredentialsToGetTokenForAutoRefresh() throws Throwable {
        checkInvalidCredentialForAuthTokenSupplier(config -> SphereAccessTokenSupplierFactory.of().createSupplierOfAutoRefresh(config));
    }

    @Test
    public void apiRequestWithWrongToken() throws Throwable {
        client();

        final SphereApiConfig config = SphereApiConfig.of(getSphereClientConfig().getProjectKey(), getSphereClientConfig().getApiUrl());
        final SphereClient client = SphereClientFactory.of().createClient(config, SphereAccessTokenSupplier.ofConstantToken("invalid-token"));
        expectExceptionAndClose(client, InvalidTokenException.class, client.execute(CategoryQuery.of()));
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
