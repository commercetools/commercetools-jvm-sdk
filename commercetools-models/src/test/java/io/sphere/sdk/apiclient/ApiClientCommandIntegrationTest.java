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

import java.time.Duration;
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

        assertEventually(Duration.ofSeconds(60), Duration.ofSeconds(1), () ->{
            final PagedQueryResult<ApiClient> result = client().executeBlocking(ApiClientQuery.of()
                .plusPredicates(m -> m.id().is(res.getId()))
            );
            assertThat(result.getResults()).hasSize(1);
            assertThat(result.getResults().get(0)).isEqualToIgnoringGivenFields(res,"secret");
        });

        final ApiClient deletedRes =client().executeBlocking(ApiClientDeleteCommand.of(res));
        assertThat(deletedRes).isEqualToIgnoringGivenFields(res,"secret");
    }

}
