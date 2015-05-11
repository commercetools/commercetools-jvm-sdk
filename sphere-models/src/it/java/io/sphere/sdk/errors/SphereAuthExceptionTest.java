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

import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class SphereAuthExceptionTest extends IntegrationTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void invalidCredentialsToGetToken() throws Throwable {
        final SphereAuthConfig config = SphereAuthConfig.of(projectKey(), clientId(), "wrong-password", authUrl());
        final SphereAccessTokenSupplier supplierOfOneTimeFetchingToken = SphereAccessTokenSupplierFactory.of().createSupplierOfOneTimeFetchingToken(config);
        assertThatThrownBy(() -> supplierOfOneTimeFetchingToken.get().toCompletableFuture().join())
                .hasCauseInstanceOf(InvalidClientCredentialsException.class);
        supplierOfOneTimeFetchingToken.close();
    }

    @Test
    public void apiRequestWithWrongToken() throws Throwable {
        client();

        final SphereApiConfig config = SphereApiConfig.of(projectKey(), apiUrl());
        final SphereClient client = SphereClientFactory.of().createClient(config, SphereAccessTokenSupplier.ofConstantToken("invalid-token"));
        expectExceptionAndClose(client, InvalidTokenException.class, client.execute(CategoryQuery.of()));
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
