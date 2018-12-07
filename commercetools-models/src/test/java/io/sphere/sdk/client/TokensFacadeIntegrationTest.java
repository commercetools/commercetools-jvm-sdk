package io.sphere.sdk.client;

import io.sphere.sdk.customers.Customer;
import io.sphere.sdk.customers.CustomerFixtures;
import io.sphere.sdk.customers.queries.CustomerQuery;
import io.sphere.sdk.products.ProductProjection;
import io.sphere.sdk.products.queries.ProductProjectionQuery;
import io.sphere.sdk.queries.PagedQueryResult;
import io.sphere.sdk.test.IntegrationTest;
import org.junit.Test;

import java.util.concurrent.CompletionStage;

import static io.sphere.sdk.apiclient.ApiClientFixtures.toSphereAuthConfig;
import static io.sphere.sdk.apiclient.ApiClientFixtures.withApiClient;
import static io.sphere.sdk.client.SphereClientUtils.blockingWait;
import static io.sphere.sdk.customers.CustomerFixtures.withCustomer;
import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;
import static java.util.concurrent.TimeUnit.SECONDS;
import static org.assertj.core.api.Assertions.assertThat;

public class TokensFacadeIntegrationTest extends IntegrationTest {

    @Test//workaround to not put the if condition into the demo code
    public void testFetchAccessToken() {
        if (!"false".equals(System.getenv("JVM_SDK_IT_SSL_VALIDATION"))) {
            fetchAccessToken();
        }
    }

    public void fetchAccessToken() {
        final SphereAuthConfig config = getSphereClientConfig();
        final CompletionStage<String> stringCompletionStage = TokensFacade.fetchAccessToken(config);
        final String accessToken = blockingWait(stringCompletionStage, 2, SECONDS);
        assertThat(accessToken).isNotEmpty();
    }

    @Test
    public void scopedToken() {
        if (!"false".equals(System.getenv("JVM_SDK_IT_SSL_VALIDATION"))) {
            scopedTokenBody();
        }
    }

    private void scopedTokenBody() {
        withApiClient(client(), asList(SphereProjectScope.VIEW_CUSTOMERS,SphereProjectScope.VIEW_ORDERS), apiClient -> {

            final SphereAuthConfig config = toSphereAuthConfig(getSphereClientConfig(),apiClient);
            assertThat(config.getScopes()).containsExactly("view_customers", "view_orders");
            final CompletionStage<String> stringCompletionStage = TokensFacade.fetchAccessToken(config);
            final String accessToken = blockingWait(stringCompletionStage, 2, SECONDS);
            assertThat(accessToken).isNotEmpty();
            try (final SphereClient client = SphereClientFactory.of()
                    .createClient(getSphereClientConfig(), SphereAccessTokenSupplier.ofConstantToken(accessToken))) {
                final PagedQueryResult<Customer> customerPagedQueryResult =
                        blockingWait(client.execute(CustomerQuery.of().withLimit(1)), 2, SECONDS);
                assertThat(customerPagedQueryResult).isNotNull();
            }
        });
    }

    @Test
    public void passwordFlow() {
        if (!"false".equals(System.getenv("JVM_SDK_IT_SSL_VALIDATION"))) {
            passwordFlowDemo();
        }
    }

    private void passwordFlowDemo() {
        withApiClient(client(), singletonList(SphereProjectScope.VIEW_PRODUCTS), apiClient -> {
            withCustomer(client(), (Customer customer) -> {
                final SphereAuthConfig authConfig = toSphereAuthConfig(getSphereClientConfig(),apiClient);
                final String email = customer.getEmail();
//              final String pw = customer.getPassword();//won;t work since it is obfusciated
                final String pw = CustomerFixtures.PASSWORD;
                final Tokens tokens = blockingWait(TokensFacade.
                        fetchCustomerPasswordFlowTokens(authConfig, email, pw), 2, SECONDS);
                final String accessToken = tokens.getAccessToken();
                assertThat(accessToken).isNotEmpty();
                try (final SphereClient client = SphereClientFactory.of()
                        .createClient(getSphereClientConfig(), SphereAccessTokenSupplier.ofConstantToken(accessToken))) {
                    final PagedQueryResult<ProductProjection> customerPagedQueryResult =
                            blockingWait(client.execute(ProductProjectionQuery.ofCurrent().withLimit(1)), 2, SECONDS);
                    assertThat(customerPagedQueryResult).isNotNull();
                }
            });
        });
    }
}