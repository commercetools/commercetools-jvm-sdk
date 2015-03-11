package io.sphere.sdk.errors;

import io.sphere.sdk.categories.Category;
import io.sphere.sdk.categories.CategoryDraft;
import io.sphere.sdk.categories.CategoryDraftBuilder;
import io.sphere.sdk.categories.CategoryFixtures;
import io.sphere.sdk.categories.commands.CategoryCreateCommand;
import io.sphere.sdk.categories.commands.CategoryDeleteCommand;
import io.sphere.sdk.categories.commands.CategoryUpdateCommand;
import io.sphere.sdk.categories.commands.updateactions.ChangeName;
import io.sphere.sdk.categories.queries.CategoryQuery;
import io.sphere.sdk.client.*;
import io.sphere.sdk.commands.UpdateAction;
import io.sphere.sdk.http.HttpResponse;
import io.sphere.sdk.http.HttpStatusCode;
import io.sphere.sdk.meta.BuildInfo;
import io.sphere.sdk.models.*;
import io.sphere.sdk.queries.PagedQueryResult;
import io.sphere.sdk.test.IntegrationTest;
import org.hamcrest.CustomTypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.Collections;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.function.Function;
import java.util.function.Supplier;

import static io.sphere.sdk.categories.CategoryFixtures.withCategory;
import static io.sphere.sdk.http.HttpMethod.POST;
import static org.fest.assertions.Assertions.assertThat;
import static io.sphere.sdk.test.OptionalAssert.assertThat;
import static io.sphere.sdk.test.SphereTestUtils.*;
import static org.junit.Assert.fail;

public class SphereExceptionTest extends IntegrationTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void invalidJsonInHttpRequestIntent() throws Throwable {
        executing(() -> TestSphereRequest.of(HttpRequestIntent.of(POST, "/categories", "{invalidJson :)")))
                .resultsInA(ErrorResponseException.class, InvalidJsonInputError.class);
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
        CategoryFixtures.withCategory(client(), category -> {
            final long wrongVersion = category.getVersion() - 1;
            final CategoryUpdateCommand command = CategoryUpdateCommand.of(Versioned.of(category.getId(), wrongVersion), Collections.<UpdateAction<Category>>emptyList());
            try {
                execute(command);
                fail("should throw exception");
            } catch (final SphereServiceException e) {
                assertThat(e.getProjectKey()).isPresentAs(projectKey());
                assertThat(e.getMessage()).contains(BuildInfo.version()).contains(command.toString());
                assertThat(e.getJsonBody().get().get("statusCode").asInt())
                        .overridingErrorMessage("exception contains json body of error response")
                        .isEqualTo(HttpStatusCode.CONFLICT_409);
            }
        });
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

    @Test
    public void referenceExists() throws Exception {
        final CategoryDraft cat1draft = categoryDraftOf(randomSlug()).build();
        final Category cat1 = execute(CategoryCreateCommand.of(cat1draft));
        final CategoryDraft cat2draft = categoryDraftOf(randomSlug()).parent(cat1).build();
        final Category cat2 = execute(CategoryCreateCommand.of(cat2draft));
        execute(CategoryDeleteCommand.of(cat2));

    }

    @Test(expected = ConcurrentModificationException.class)
    public void concurrentModification() throws Exception {
        withCategory(client(), cat -> {
            final CategoryUpdateCommand cmd = CategoryUpdateCommand.of(cat, asList(ChangeName.of(LocalizedStrings.ofEnglishLocale("new name"))));
            execute(cmd);
            execute(cmd);
        });
    }

    private CategoryDraftBuilder categoryDraftOf(final LocalizedStrings slug) {
        return CategoryDraftBuilder.of(LocalizedStrings.ofEnglishLocale("name"), slug);
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

        public void resultsInA(final Class<? extends ErrorResponseException> type, final Class<? extends SphereError> error) {
            thrown.expect(type);
            thrown.expect(ExceptionCodeMatches.of(error));
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

    private static class ExceptionCodeMatches<T extends SphereError> extends CustomTypeSafeMatcher<ErrorResponseException> {
        private final Class<T> error;

        private ExceptionCodeMatches(final Class<T> error) {
            super("expects sphere error");
            this.error = error;
        }

        @Override
        protected boolean matchesSafely(final ErrorResponseException e) {
            boolean matches = false;
            if (!e.getErrors().isEmpty()) {
                final SphereError firstError = e.getErrors().get(0);
                final Optional<T> concreteErrorOption = firstError.as(error);
                matches = concreteErrorOption.isPresent();
            }
            return matches;
        }

        public static <T extends SphereError> ExceptionCodeMatches<T> of(final Class<T> error) {
            return new ExceptionCodeMatches<>(error);
        }
    }
}
