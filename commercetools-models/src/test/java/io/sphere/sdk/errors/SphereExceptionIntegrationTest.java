package io.sphere.sdk.errors;

import io.sphere.sdk.apiclient.ApiClientFixtures;
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
import io.sphere.sdk.categories.queries.CategoryByIdGet;
import io.sphere.sdk.categories.queries.CategoryQuery;
import io.sphere.sdk.channels.queries.ChannelByIdGet;
import io.sphere.sdk.client.*;
import io.sphere.sdk.commands.UpdateActionImpl;
import io.sphere.sdk.customers.queries.CustomerQuery;
import io.sphere.sdk.http.HttpHeaders;
import io.sphere.sdk.http.HttpResponse;
import io.sphere.sdk.http.HttpStatusCode;
import io.sphere.sdk.meta.BuildInfo;
import io.sphere.sdk.models.LocalizedString;
import io.sphere.sdk.models.SphereException;
import io.sphere.sdk.models.Versioned;
import io.sphere.sdk.models.errors.InvalidJsonInputError;
import io.sphere.sdk.queries.PagedQueryResult;
import io.sphere.sdk.test.IntegrationTest;
import io.sphere.sdk.test.utils.VrapHeaders;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletionException;
import java.util.concurrent.CompletionStage;
import java.util.function.Supplier;

import static io.sphere.sdk.apiclient.ApiClientFixtures.toSphereClientConfig;
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

    private static final Logger logger = LoggerFactory.getLogger(SphereExceptionIntegrationTest.class);

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void demoExceptionForCode() {
        final int statusCode = 500;
        final SphereClient httpTestDouble = TestDoubleSphereClientFactory.createHttpTestDouble(intent -> HttpResponse.of(statusCode));

    }

    @Test
    public void invalidJsonInHttpRequestIntent() throws Throwable {
        final HttpHeaders headers = VrapHeaders.disableValidation("request", "response");
        executing(() -> TestSphereRequest.of(HttpRequestIntent.of(POST, "/categories", "{invalidJson :)").withHeaders(headers)))
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

    @Ignore("Disabled because there are problems with the external OAuth")
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

    /*
     Sometimes while development a computer is hibernated and the token expires and then needs to be refreshed.
     */
    @Test
    public void retryOnInvalidToken() throws Exception {
        final RetryInvalidTokenHttpClient httpClient = new RetryInvalidTokenHttpClient(getSphereClientConfig());
        final SphereAccessTokenSupplier tokenSupplier =
                SphereAccessTokenSupplier.ofAutoRefresh(getSphereClientConfig(), httpClient, false);
        try(final SphereClient client = SphereClient.of(getSphereClientConfig(), httpClient, tokenSupplier)) {
            assertThat(httpClient.isTokenValid()).isTrue();
            assertThat(client.execute(CategoryByIdGet.of("cat-id")).toCompletableFuture().join()).isNull();
            //here is the point where the token expires
            assertThat(httpClient.isTokenValid()).isFalse();
            assertThat(client.execute(ChannelByIdGet.of("channel-id")).toCompletableFuture().join()).isNull();
            assertThat(httpClient.isTokenValid()).isTrue();
        }
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
        return new DummyExceptionTestDsl(responseCode,thrown);
    }

    private ExceptionTestDsl executing(final Supplier<SphereRequest<?>> f) {
        return  ExceptionTestDsl.of(f,thrown,client());
    }

    @Test
    public void noSearchHintNoteOnNormalException() throws Exception {
        final SphereClient client = TestDoubleSphereClientFactory.createHttpTestDouble(intent -> HttpResponse.of(500));
        assertThatThrownBy(() -> client.execute(CategoryQuery.of()).toCompletableFuture().join())
                .hasCauseInstanceOf(SphereException.class)
                .matches(e -> ((SphereException) e.getCause()).getAdditionalNotes().stream().allMatch(s -> !s.contains("reindex")));
    }

    @Test
    public void permissionsExceededImpl() {
        if (!"false".equals(System.getenv("JVM_SDK_IT_SSL_VALIDATION"))) {
            permissionsExceeded();
        }
    }

    private void permissionsExceeded() {
        final List<SphereScope> scopes = singletonList(SphereProjectScope.VIEW_PRODUCTS);

        ApiClientFixtures.withApiClient(client(), scopes, apiClient -> {
            final SphereClientConfig clientConfig = toSphereClientConfig(getSphereClientConfig(), apiClient);
            try (final SphereClient client = SphereClientFactory.of(IntegrationTest::newHttpClient)
                    .createClient(clientConfig)) {
                assertThatThrownBy(() -> {
                    final CustomerQuery request = CustomerQuery.of();
                    SphereClientUtils.blockingWait(client.execute(request), 5, SECONDS);
                })
                        .as("since the allowed scope is only to view products, customer data should not be loadable")
                        .isInstanceOf(ForbiddenException.class);
            }
        });
    }
}
