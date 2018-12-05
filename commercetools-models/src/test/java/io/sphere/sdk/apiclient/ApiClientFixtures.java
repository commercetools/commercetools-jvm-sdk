package io.sphere.sdk.apiclient;

import io.sphere.sdk.apiclient.commands.ApiClientCreateCommand;
import io.sphere.sdk.apiclient.commands.ApiClientDeleteCommand;
import io.sphere.sdk.client.BlockingSphereClient;
import io.sphere.sdk.client.SphereApiConfig;
import io.sphere.sdk.client.SphereScope;

import java.util.List;
import java.util.function.Consumer;

public final class ApiClientFixtures {

    public static void withApiClient(final BlockingSphereClient client,final ApiClientDraft apiClientDraft,final Consumer<ApiClient> consumer){
        ApiClient apiClient = client.executeBlocking(ApiClientCreateCommand.of(apiClientDraft));
        consumer.accept(apiClient);
        client.executeBlocking(ApiClientDeleteCommand.of(apiClient));
    }

    public static void withApiClient(final BlockingSphereClient client, final List<SphereScope> scopes, final Consumer<ApiClient> consumer){
        final SphereApiConfig config = client.getConfig();
        final ApiClientDraft apiClientDraft = ApiClientDraftBuilder.of("api_generated_client", config.getProjectKey(), scopes).build();
        withApiClient(client,apiClientDraft,consumer);
    }


}
