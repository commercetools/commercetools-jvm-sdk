package io.sphere.sdk.exceptions;

import io.sphere.sdk.categories.Category;
import io.sphere.sdk.categories.commands.CategoryUpdateCommand;
import io.sphere.sdk.categories.queries.CategoryQuery;
import io.sphere.sdk.client.*;
import io.sphere.sdk.commands.UpdateAction;
import io.sphere.sdk.http.HttpResponse;
import io.sphere.sdk.models.Base;
import io.sphere.sdk.models.Versioned;
import io.sphere.sdk.queries.PagedQueryResult;
import io.sphere.sdk.test.IntegrationTest;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.Collections;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.function.Function;
import java.util.function.Supplier;

import static io.sphere.sdk.http.HttpMethod.POST;
import static io.sphere.sdk.test.OptionalAssert.assertThat;
import static org.junit.Assert.fail;

public class SphereExceptionTest extends IntegrationTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void invalidJsonInHttpRequestIntent() throws Throwable {
        executing(() -> TestSphereRequest.of(HttpRequestIntent.of(POST, "/categories", "{invalidJson :)")))
                .resultsInA(InvalidJsonInputException.class);
    }

    @Test
    public void internalServerError() throws Throwable {
        aHttpResponseWithCode(500).resultsInA(InternalServerErrorException .class);
    }

    @Test
    public void badGateway() throws Throwable {
        aHttpResponseWithCode(502).resultsInA(BadGatewayException .class);
    }

    @Test
    public void serviceUnavailable() throws Throwable {
        aHttpResponseWithCode(503).resultsInA(ServiceUnavailableException .class);
    }

    @Test
    public void gatewayTimeout() throws Throwable {
        aHttpResponseWithCode(504).resultsInA(GatewayTimeoutException.class);
    }

    @Test
    public void notFoundExceptionOnUpdateWithMissingObject() throws Exception {
        final CategoryUpdateCommand updateCommand =
                CategoryUpdateCommand.of(Versioned.of("not-existing-id", 1), Collections.<UpdateAction<Category>>emptyList());
        executing(() -> updateCommand)
                .resultsInA(NotFoundException.class);

        try {
            execute(CategoryUpdateCommand.of(Versioned.of("foo", 1), Collections.<UpdateAction<Category>>emptyList()));
            fail("should throw exception");
        } catch (final SphereServiceException e) {
            assertThat(e.getProjectKey()).isPresentAs(projectKey());
        }
    }

    @Test
    public void exceptionsGatherContext() throws Exception {
        try {
            execute(CategoryUpdateCommand.of(Versioned.of("foo", 1), Collections.<UpdateAction<Category>>emptyList()));
            fail("should throw exception");
        } catch (final SphereServiceException e) {
            assertThat(e.getProjectKey()).isPresentAs(projectKey());
        }
    }

    @Test
    public void invalidCredentialsToGetToken() throws Throwable {
        final SphereAuthConfig config = SphereAuthConfig.of(projectKey(), clientId(), "wrong-password", authUrl());
        final SphereAccessTokenSupplier supplierOfOneTimeFetchingToken = SphereAccessTokenSupplierFactory.of().createSupplierOfOneTimeFetchingToken(config);
        final CompletableFuture<String> future = supplierOfOneTimeFetchingToken.get();
        expectException(InvalidClientCredentialsException.class, future);
        supplierOfOneTimeFetchingToken.close();
    }

    @Test
    public void apiRequestWithWrongToken() throws Throwable {
        client();

        final SphereApiConfig config = SphereApiConfig.of(projectKey(), apiUrl());
        final SphereClient client = SphereClientFactory.of().createClient(config, SphereAccessTokenSupplier.ofConstantToken("invalid-token"));
        expectExceptionAndClose(client, InvalidTokenException.class, client.execute(CategoryQuery.of()));
    }

    private void expectExceptionAndClose(final SphereClient client, final Class<InvalidTokenException> exceptionClass, final CompletableFuture<PagedQueryResult<Category>> future) throws Throwable {
        thrown.expect(exceptionClass);
        try {
            future.join();
        } catch (final CompletionException e) {
            client.close();
            throw e.getCause();
        }

    }

    private <T> void expectException(final Class<? extends SphereException> exceptionClass, final CompletableFuture<T> future) throws Throwable {
        thrown.expect(exceptionClass);
        try {
            future.join();
        } catch (final CompletionException e) {
            throw e.getCause();
        }
    }

    private DummyExceptionTestDsl aHttpResponseWithCode(final int responseCode) {
        return new DummyExceptionTestDsl(responseCode);
    }

    private ExceptionTestDsl executing(final Supplier<SphereRequest<? extends Object>> f) {
        return new ExceptionTestDsl(f);
    }


    private class DummyExceptionTestDsl {
        private final int responseCode;

        public DummyExceptionTestDsl(final int responseCode) {
            this.responseCode = responseCode;
        }

        public void resultsInA(final Class<? extends Throwable> type) throws Throwable {
            thrown.expect(type);
            try {
                SphereClientFactory.of()
                        .createHttpTestDouble(request -> HttpResponse.of(responseCode)).execute(CategoryQuery.of()).join();
            } catch (final CompletionException e) {
                throw e.getCause();
            }
        }
    }

    private class ExceptionTestDsl {
        private final Supplier<SphereRequest<? extends Object>> f;

        public ExceptionTestDsl(final Supplier<SphereRequest<? extends Object>> f) {
            this.f = f;
        }

        public void resultsInA(final Class<? extends Throwable> type) {
            thrown.expect(type);
            final SphereRequest<? extends Object> testSphereRequest = f.get();
            execute(testSphereRequest);
        }
    }

    private static class TestSphereRequest extends Base implements SphereRequest<String> {

        private final HttpRequestIntent requestIntent;

        private TestSphereRequest(final HttpRequestIntent requestIntent) {
            this.requestIntent = requestIntent;
        }

        public static TestSphereRequest of(final HttpRequestIntent requestIntent) {
            return new TestSphereRequest(requestIntent);
        }


        @Override
        public Function<HttpResponse, String> resultMapper() {
            return null;
        }

        @Override
        public HttpRequestIntent httpRequestIntent() {
            return requestIntent;
        }
    }
}
