package io.sphere.sdk.errors;

import io.sphere.sdk.carts.Cart;
import io.sphere.sdk.carts.commands.CartUpdateCommand;
import io.sphere.sdk.carts.commands.updateactions.AddLineItem;
import io.sphere.sdk.carts.queries.CartByIdGet;
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
import io.sphere.sdk.commands.UpdateActionImpl;
import io.sphere.sdk.customers.queries.CustomerQuery;
import io.sphere.sdk.http.HttpResponse;
import io.sphere.sdk.http.HttpStatusCode;
import io.sphere.sdk.meta.BuildInfo;
import io.sphere.sdk.models.Base;
import io.sphere.sdk.models.LocalizedString;
import io.sphere.sdk.models.SphereException;
import io.sphere.sdk.models.Versioned;
import io.sphere.sdk.models.errors.InvalidJsonInputError;
import io.sphere.sdk.models.errors.SphereError;
import io.sphere.sdk.queries.PagedQueryResult;
import io.sphere.sdk.test.IntegrationTest;
import org.hamcrest.CustomTypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletionException;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

import static io.sphere.sdk.carts.CartFixtures.withCart;
import static io.sphere.sdk.categories.CategoryFixtures.withCategory;
import static io.sphere.sdk.http.HttpMethod.POST;
import static io.sphere.sdk.products.ProductFixtures.withTaxedProduct;
import static io.sphere.sdk.test.SphereTestUtils.randomSlug;
import static java.util.Collections.singletonList;
import static java.util.concurrent.TimeUnit.SECONDS;
import static org.assertj.core.api.Assertions.*;
import static org.junit.Assert.fail;

public class SphereExceptionIntegrationTest extends IntegrationTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void demoExceptionForCode() {
        final int statusCode = 500;
        final SphereClient httpTestDouble = TestDoubleSphereClientFactory.createHttpTestDouble(intent -> HttpResponse.of(statusCode));

    }

    @Test
    public void invalidJsonInHttpRequestIntent() throws Throwable {
        executing(() -> TestSphereRequest.of(HttpRequestIntent.of(POST, "/categories", "{invalidJson :)")))
                .resultsInA(ErrorResponseException.class, InvalidJsonInputError.class);
    }

    @Test
    public void internalServerError() throws Throwable {
        aHttpResponseWithCode(500).resultsInA(InternalServerErrorException.class);
    }

    @Test
    public void badGateway() throws Throwable {
        aHttpResponseWithCode(502).resultsInA(BadGatewayException.class);
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
    public void requestEntityTooLarge() throws Throwable {
        aHttpResponseWithCode(413).resultsInA(RequestEntityTooLargeException.class);
    }

    @Test
    public void notFoundExceptionOnUpdateWithMissingObject() throws Exception {
        final CategoryUpdateCommand updateCommand =
                CategoryUpdateCommand.of(Versioned.of("not-existing-id", 1L), Collections.<UpdateActionImpl<Category>>emptyList());
        executing(() -> updateCommand)
                .resultsInA(NotFoundException.class);

        try {
            client().executeBlocking(CategoryUpdateCommand.of(Versioned.of("foo", 1L), Collections.<UpdateActionImpl<Category>>emptyList()));
            fail("should throw exception");
        } catch (final SphereServiceException e) {
            assertThat(e.getProjectKey()).contains(getSphereClientConfig().getProjectKey());
        }
    }

    @Test
    public void exceptionsGatherContext() throws Exception {
        CategoryFixtures.withCategory(client(), category -> {
            final Long wrongVersion = category.getVersion() - 1;
            final CategoryUpdateCommand command = CategoryUpdateCommand.of(Versioned.of(category.getId(), wrongVersion), Collections.<UpdateActionImpl<Category>>emptyList());
            try {
                client().executeBlocking(command);
                fail("should throw exception");
            } catch (final SphereServiceException e) {
                assertThat(e.getProjectKey()).contains(getSphereClientConfig().getProjectKey());
                assertThat(e.getMessage()).contains(BuildInfo.version()).contains(command.toString());
                assertThat(e.getJsonBody().get("statusCode").asInt())
                        .overridingErrorMessage("exception contains json body of error response")
                        .isEqualTo(HttpStatusCode.CONFLICT_409);
            }
        });
    }

    @Test
    public void invalidCredentialsToGetToken() throws Throwable {
        final SphereAuthConfig config = SphereAuthConfig.of(getSphereClientConfig().getProjectKey(), getSphereClientConfig().getClientId(), "wrong-password", getSphereClientConfig().getAuthUrl());
        final SphereAccessTokenSupplier supplierOfOneTimeFetchingToken = SphereAccessTokenSupplier.ofOneTimeFetchingToken(config, newHttpClient(), true);
        final CompletionStage<String> future = supplierOfOneTimeFetchingToken.get();
        expectException(InvalidClientCredentialsException.class, future);
        supplierOfOneTimeFetchingToken.close();
    }

    @Test
    public void apiRequestWithWrongToken() throws Throwable {
        client();

        final SphereApiConfig config = SphereApiConfig.of(getSphereClientConfig().getProjectKey(), getSphereClientConfig().getApiUrl());
        final SphereClient client = SphereClient.of(config, newHttpClient(),SphereAccessTokenSupplier.ofConstantToken("invalid-token"));
        expectExceptionAndClose(client, InvalidTokenException.class, client.execute(CategoryQuery.of()));
    }

    @Test
    public void referenceExists() throws Exception {
        final CategoryDraft cat1draft = categoryDraftOf(randomSlug()).build();
        final Category cat1 = client().executeBlocking(CategoryCreateCommand.of(cat1draft));
        final CategoryDraft cat2draft = categoryDraftOf(randomSlug()).parent(cat1).build();
        final Category cat2 = client().executeBlocking(CategoryCreateCommand.of(cat2draft));
        client().executeBlocking(CategoryDeleteCommand.of(cat2));

    }

    @Test
    public void concurrentModification() throws Exception {
        withCategory(client(), cat -> {
            final CategoryUpdateCommand cmd = CategoryUpdateCommand.of(cat, ChangeName.of(LocalizedString.ofEnglish("new")));
            final Category successfulUpdatedCategory = client().executeBlocking(cmd);
            final Throwable throwable = catchThrowable(() -> client().executeBlocking(cmd));//same command twice
            assertThat(throwable).isInstanceOf(ConcurrentModificationException.class);
            final ConcurrentModificationException exception = (ConcurrentModificationException) throwable;
            assertThat(exception.getCurrentVersion())
                    .isGreaterThanOrEqualTo(successfulUpdatedCategory.getVersion());
        });
    }

    @Test
    public void demoForFailing409IsGood() {
        //Given we have a product
        withTaxedProduct(client(), product -> {
            //And given we have an empty cart
            withCart(client(), cart -> {
                //when a customer clicks enter to cart
                final int variantId = 1;
                final int quantity = 2;
                final CartUpdateCommand cartUpdateCommand =  CartUpdateCommand.of(cart, AddLineItem.of(product, variantId, quantity));
                final Cart successfulUpdatedCart = client().executeBlocking(cartUpdateCommand);
                assertThat(successfulUpdatedCart.getVersion()).isGreaterThan(cart.getVersion());

                //and by accident again
                final Throwable throwable = catchThrowable(() -> client().executeBlocking(cartUpdateCommand));

                assertThat(throwable).isInstanceOf(ConcurrentModificationException.class);

                return successfulUpdatedCart;
            });
        });
    }

    @Test
    public void demoForBruteForceSolveTheVersionConflict() {
        withTaxedProduct(client(), product -> {
            withCart(client(), cart -> {
                //when a customer clicks enter to cart
                final int variantId = 1;
                final int quantity = 2;
                final CartUpdateCommand cartUpdateCommand =  CartUpdateCommand.of(cart, AddLineItem.of(product, variantId, quantity));
                client().executeBlocking(cartUpdateCommand);//successful attempt in another thread/machine/container/tab
                //the operation will increase the version number of the cart

                //warning: ignoring version conflicts can be very poor behaviour on some use cases
                //we show this here if you want to execute the command anyway
                Cart updated2;
                try {
                    updated2 = client().executeBlocking(cartUpdateCommand);
                } catch (final ConcurrentModificationException e) {
                    final Cart cartWithCurrentVersion = client().executeBlocking(CartByIdGet.of(cart));
                    final CartUpdateCommand commandWithCurrentCartVersion = cartUpdateCommand.withVersion(cartWithCurrentVersion);
                    updated2 = client().executeBlocking(commandWithCurrentCartVersion);
                }
                return updated2;
            });
        });
    }

    @Test
    public void demoForBruteForceSolveTheVersionConflictWithExceptionCurrentVersion() {
        withTaxedProduct(client(), product -> {
            withCart(client(), cart -> {
                //when a customer clicks enter to cart
                final int variantId = 1;
                final int quantity = 2;
                final CartUpdateCommand cartUpdateCommand =  CartUpdateCommand.of(cart, AddLineItem.of(product, variantId, quantity));
                client().executeBlocking(cartUpdateCommand);//successful attempt in another thread/machine/container/tab
                //the operation will increase the version number of the cart

                //warning: ignoring version conflicts can be very poor behaviour on some use cases
                //we show this here if you want to execute the command anyway
                Cart updated2;
                try {
                    updated2 = client().executeBlocking(cartUpdateCommand);
                } catch (final ConcurrentModificationException e) {
                    final CartUpdateCommand commandWithCurrentCartVersion = cartUpdateCommand.withVersion(e.getCurrentVersion());
                    updated2 = client().executeBlocking(commandWithCurrentCartVersion);
                }
                return updated2;
            });
        });
    }

    private CategoryDraftBuilder categoryDraftOf(final LocalizedString slug) {
        return CategoryDraftBuilder.of(LocalizedString.ofEnglish("name"), slug);
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

    private <T> void expectException(final Class<? extends SphereException> exceptionClass, final CompletionStage<T> stage) throws Throwable {
        thrown.expect(exceptionClass);
        try {
            stage.toCompletableFuture().join();
        } catch (final CompletionException e) {
            throw e.getCause();
        }
    }

    private DummyExceptionTestDsl aHttpResponseWithCode(final int responseCode) {
        return new DummyExceptionTestDsl(responseCode);
    }

    private ExceptionTestDsl executing(final Supplier<SphereRequest<?>> f) {
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
                TestDoubleSphereClientFactory.createHttpTestDouble(
                        request -> HttpResponse.of(responseCode)).execute(CategoryQuery.of()
                ).toCompletableFuture().join();
            } catch (final CompletionException e) {
                throw e.getCause();
            }
        }
    }

    private class ExceptionTestDsl {
        private final Supplier<SphereRequest<?>> f;

        public ExceptionTestDsl(final Supplier<SphereRequest<?>> f) {
            this.f = f;
        }

        public void resultsInA(final Class<? extends Throwable> type) {
            thrown.expect(type);
            final SphereRequest<?> testSphereRequest = f.get();
            client().executeBlocking(testSphereRequest);
        }

        public void resultsInA(final Class<? extends ErrorResponseException> type, final Class<? extends SphereError> error) {
            thrown.expect(type);
            thrown.expect(ExceptionCodeMatches.of(error));
            final SphereRequest<?> testSphereRequest = f.get();
            client().executeBlocking(testSphereRequest);
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
        public String deserialize(final HttpResponse httpResponse) {
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
                try {
                    firstError.as(error);
                    matches = true;
                } catch (final IllegalArgumentException e1) {
                    matches = false;
                }
            }
            return matches;
        }

        public static <T extends SphereError> ExceptionCodeMatches<T> of(final Class<T> error) {
            return new ExceptionCodeMatches<>(error);
        }
    }

    @Test
    public void enrichmentForSearchMappingIssues() throws Exception {
        final String body = "{\"statusCode\":400,\"message\":\"SearchPhaseExecutionException[Failed to execute phase [query_fetch], all shards failed; shardFailures {[13K3EBPTQoWoMNpMexz6tQ][products-0][3]: RemoteTransportException[[search3.sphere.prod.commercetools.de][inet[/192.168.7.35:9300]][search/phase/query+fetch]]; nested: SearchParseException[[products-0][3]: query[ConstantScore(*:*)],from[-1],size[-1]: Parse Failure [Failed to parse source [{\\\"query\\\":{\\\"match_all\\\":{}},\\\"facets\\\":{\\\"variants.attributes.SizeProductProjectio\\\":{\\\"range\\\":{\\\"field\\\":\\\"variants.attributes.SizeProductProjectio\\\",\\\"ranges\\\":[{\\\"from\\\":\\\"0\\\"}]},\\\"nested\\\":\\\"variants\\\"}},\\\"from\\\":0,\\\"size\\\":20}]]]; nested: ClassCastException[java.lang.String cannot be cast to java.lang.Number]; }]\",\"errors\":[{\"code\":\"InvalidInput\",\"message\":\"SearchPhaseExecutionException[Failed to execute phase [query_fetch], all shards failed; shardFailures {[13K3EBPTQoWoMNpMexz6tQ][products-0][3]: RemoteTransportException[[search3.sphere.prod.commercetools.de][inet[/192.168.7.35:9300]][search/phase/query+fetch]]; nested: SearchParseException[[products-0][3]: query[ConstantScore(*:*)],from[-1],size[-1]: Parse Failure [Failed to parse source [{\\\"query\\\":{\\\"match_all\\\":{}},\\\"facets\\\":{\\\"variants.attributes.SizeProductProjectio\\\":{\\\"range\\\":{\\\"field\\\":\\\"variants.attributes.SizeProductProjectio\\\",\\\"ranges\\\":[{\\\"from\\\":\\\"0\\\"}]},\\\"nested\\\":\\\"variants\\\"}},\\\"from\\\":0,\\\"size\\\":20}]]]; nested: ClassCastException[java.lang.String cannot be cast to java.lang.Number]; }]\"}]}";
        final SphereClient client = TestDoubleSphereClientFactory.createHttpTestDouble(intent -> HttpResponse.of(400, body));
        assertThatThrownBy(() -> client.execute(CategoryQuery.of()).toCompletableFuture().join())
                .hasCauseInstanceOf(ErrorResponseException.class)
                .matches(e -> ((ErrorResponseException) e.getCause()).getAdditionalNotes().stream()
                        .anyMatch(note -> note.contains("Maybe it helps to reindex the products https://admin.sphere.io/fake-project-key-for-testing/developers/danger but this may take a while.")));
    }

    @Test
    public void noSearchHintNoteOnNormalException() throws Exception {
        final SphereClient client = TestDoubleSphereClientFactory.createHttpTestDouble(intent -> HttpResponse.of(500));
        assertThatThrownBy(() -> client.execute(CategoryQuery.of()).toCompletableFuture().join())
                .hasCauseInstanceOf(SphereException.class)
                .matches(e -> ((SphereException) e.getCause()).getAdditionalNotes().stream().allMatch(s -> !s.contains("reindex")));
    }

    @Test
    public void permissionsExceeded() {
        final List<SphereScope> scopes = singletonList(SphereProjectScope.VIEW_PRODUCTS);
        try(final SphereClient client = createClientWithScopes(scopes)) {
            assertThatThrownBy(() -> {
                final CustomerQuery request = CustomerQuery.of();
                SphereClientUtils.blockingWait(client.execute(request), 5, SECONDS);
            })
            .as("since the allowed scope is only to view products, customer data should not be loadable")
            .isInstanceOf(ForbiddenException.class);
        }
    }

    private SphereClient createClientWithScopes(final List<SphereScope> scopes) {
        final SphereClientConfig config = SphereClientConfigBuilder.ofClientConfig(getSphereClientConfig())
                .scopes(scopes)
                .build();
        return SphereClientFactory.of(IntegrationTest::newHttpClient)
                .createClient(config);
    }
}
