package io.sphere.sdk.apiclient;

import io.sphere.sdk.apiclient.commands.ApiClientCreateCommand;
import io.sphere.sdk.apiclient.commands.ApiClientDeleteCommand;
import io.sphere.sdk.apiclient.queries.ApiClientQuery;
import io.sphere.sdk.client.*;
import io.sphere.sdk.orders.Order;
import io.sphere.sdk.orders.OrderFixtures;
import io.sphere.sdk.orders.queries.OrderQuery;
import io.sphere.sdk.queries.PagedQueryResult;
import io.sphere.sdk.stores.StoreFixtures;
import io.sphere.sdk.test.IntegrationTest;
import org.assertj.core.api.Assertions;
import org.junit.Ignore;
import org.junit.Test;

import java.util.Arrays;
import java.util.concurrent.CompletionStage;
import java.util.stream.Collectors;

import static io.sphere.sdk.apiclient.ApiClientFixtures.GENERATED_CLIENT_NAME;
import static io.sphere.sdk.client.SphereClientUtils.blockingWait;
import static io.sphere.sdk.client.SphereProjectScope.MANAGE_API_CLIENTS;
import static io.sphere.sdk.client.SphereProjectScope.MANAGE_MY_ORDERS;
import static io.sphere.sdk.test.SphereTestUtils.assertEventually;
import static java.util.Arrays.asList;
import static java.util.concurrent.TimeUnit.SECONDS;
import static org.assertj.core.api.Assertions.assertThat;

public class ApiClientCommandIntegrationTest extends IntegrationTest {


    @Test
    public void createApiClient(){
        final String projectKey = getSphereClientConfig().getProjectKey();
        final ApiClientCreateCommand createCommand  =ApiClientCreateCommand.of(ApiClientDraftBuilder.of(GENERATED_CLIENT_NAME, projectKey, MANAGE_MY_ORDERS, MANAGE_API_CLIENTS).deleteDaysAfterCreation(1).build());
        final ApiClient res = client().executeBlocking(createCommand);
        assertThat(res).isNotNull();
        final String expectedScope = asList(MANAGE_MY_ORDERS, MANAGE_API_CLIENTS).stream().map(SphereScope::toScopeString).map(s -> s+":"+projectKey).collect(Collectors.joining(" "));
        assertThat(res.getScope()).isEqualTo(expectedScope);
        assertThat(res.getDeleteAt()).isNotNull();
        final ApiClient deletedRes =client().executeBlocking(ApiClientDeleteCommand.of(res));
        assertThat(deletedRes).isEqualToIgnoringGivenFields(res,"secret");
    }

    @Test
    public void testQueryModel(){
        final String projectKey = getSphereClientConfig().getProjectKey();
        final ApiClientCreateCommand createCommand  =ApiClientCreateCommand.of(ApiClientDraftBuilder.of(GENERATED_CLIENT_NAME, projectKey, MANAGE_MY_ORDERS, MANAGE_API_CLIENTS).deleteDaysAfterCreation(1).build());
        final ApiClient res = client().executeBlocking(createCommand);assertThat(res).isNotNull();
        final PagedQueryResult<ApiClient> result = client().executeBlocking(ApiClientQuery.of()
                .plusPredicates(m -> m.id().is(res.getId()))
        );

        assertEventually(() ->{
            assertThat(result.getResults()).hasSize(1);
        });

        assertThat(result.getResults().get(0)).isEqualToIgnoringGivenFields(res,"secret");
        final ApiClient deletedRes =client().executeBlocking(ApiClientDeleteCommand.of(res));
        assertThat(deletedRes).isEqualToIgnoringGivenFields(res,"secret");
    }

    @Ignore("Backend does not support the scope at the time of writing the test")
    @Test
    public void testCustomScope() {

        StoreFixtures.withUpdateableStore(client(), firstStore -> {
            StoreFixtures.withStore(client(), secondStore -> {
                final String projectKey = getSphereClientConfig().getProjectKey();
                final String viewOrdersScopeString = "view_orders:" + projectKey + ":" + firstStore.getKey();
                //final String viewOrdersScopeString = "view_orders:" + projectKey;
                final SphereScope sphereScope = SphereProjectScope.of(viewOrdersScopeString);

                final ApiClientCreateCommand createCommand = ApiClientCreateCommand.of(ApiClientDraftBuilder.of("store-scope-test-client", viewOrdersScopeString).deleteDaysAfterCreation(1).build());
                final ApiClient apiClient = client().executeBlocking(createCommand);
                Assertions.assertThat(apiClient).isNotNull();

                final SphereAuthConfig config = MySphereAuthConfigBuilder.ofKeyIdSecret(apiClient.getProjectKey(), apiClient.getId(), apiClient.getSecret())
                        .scopes(Arrays.asList(sphereScope))
                        .authUrl(getSphereClientConfig().getAuthUrl())
                        .build();
                assertThat(config.getScopes()).containsExactly(viewOrdersScopeString);

                final CompletionStage<String> stringCompletionStage = TokensFacade.fetchAccessToken(config);
                final String accessToken = blockingWait(stringCompletionStage, 2, SECONDS);
                assertThat(accessToken).isNotEmpty();

                final SphereClient client = SphereClientFactory.of()
                        .createClient(getSphereClientConfig(), SphereAccessTokenSupplier.ofConstantToken(accessToken));

                    OrderFixtures.withOrderInStore(client(), firstStore, order -> {
                        final PagedQueryResult<Order> orderPagedQueryResult =
                                blockingWait(client.execute(OrderQuery.of().withLimit(1).withPredicates(m -> m.store().key().is(firstStore.getKey()))), 2, SECONDS);
                        assertThat(orderPagedQueryResult).isNotNull();
                        return order;
                    });

//                    OrderFixtures.withOrderInStore(client(), secondStore, order -> {
//                        final PagedQueryResult<Order> orderPagedQueryResult =
//                                blockingWait(client.execute(OrderQuery.of().withLimit(1)), 2, SECONDS);
//                        assertThat(orderPagedQueryResult).isNull();
//                    });
                client.close();

                final ApiClient deletedRes = client().executeBlocking(ApiClientDeleteCommand.of(apiClient));
                Assertions.assertThat(deletedRes).isEqualToIgnoringGivenFields(apiClient,"secret", "lastUsedAt");
            });
            return firstStore;
        });
    }
}
