package io.sphere.sdk.client;

import io.sphere.sdk.customers.Customer;
import io.sphere.sdk.customers.queries.CustomerQuery;
import io.sphere.sdk.queries.PagedQueryResult;
import io.sphere.sdk.test.IntegrationTest;
import org.junit.Test;

import java.util.List;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.TimeUnit;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;

public class TokensFacadeIntegrationTest extends IntegrationTest {

    @Test//workaround to not put the into the demo code
    public void testFetchAccessToken() {
        if (!"false".equals(System.getenv("JVM_SDK_IT_SSL_VALIDATION"))) {
            fetchAccessToken();
        }
    }

    public void fetchAccessToken() {
        final SphereAuthConfig config = getSphereClientConfig();
        final CompletionStage<String> stringCompletionStage = TokensFacade.fetchAccessToken(config);
        final String accessToken = SphereClientUtils.blockingWait(stringCompletionStage, 2, TimeUnit.SECONDS);
        assertThat(accessToken).isNotEmpty();
    }

    @Test
    public void scopedToken() {
        if (!"false".equals(System.getenv("JVM_SDK_IT_SSL_VALIDATION"))) {
            scopedTokenBody();
        }
    }

    private void scopedTokenBody() {
        final List<SphereScope> scopes = asList(SphereProjectScope.VIEW_CUSTOMERS, SphereProjectScope.VIEW_ORDERS);
        final SphereAuthConfig config = SphereAuthConfigBuilder.ofAuthConfig(getSphereClientConfig())
                .scopes(scopes)
                .build();
        assertThat(config.getScopes()).containsExactly("view_customers", "view_orders");
        final CompletionStage<String> stringCompletionStage = TokensFacade.fetchAccessToken(config);
        final String accessToken = SphereClientUtils.blockingWait(stringCompletionStage, 2, TimeUnit.SECONDS);
        assertThat(accessToken).isNotEmpty();
        try(final SphereClient client = SphereClientFactory.of()
                .createClient(getSphereClientConfig(), SphereAccessTokenSupplier.ofConstantToken(accessToken))) {
            final PagedQueryResult<Customer> customerPagedQueryResult =
                    SphereClientUtils.blockingWait(client.execute(CustomerQuery.of().withLimit(1)), 2, TimeUnit.SECONDS);
            assertThat(customerPagedQueryResult).isNull();
        }
    }
}